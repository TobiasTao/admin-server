package com.haolang.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haolang.admin.entity.Article;
import com.haolang.admin.entity.ArticleTag;
import com.haolang.admin.entity.Content;
import com.haolang.admin.entity.Tag;
import com.haolang.admin.service.IArticleService;
import com.haolang.admin.service.IArticleTagService;
import com.haolang.admin.service.IContentService;
import com.haolang.admin.service.ITagService;
import com.haolang.admin.vo.ArticleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import sun.instrument.TransformerManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 郝浪
 * @since 2019-01-17
 */
@Slf4j
@RestController
@RequestMapping("/admin/articles")
public class ArticleController {


    private final
    IArticleService articleService;
    private final
    ITagService tagService;
    private final
    IArticleTagService articleTagService;
    private final
    IContentService contentService;

    @Autowired
    public ArticleController(IArticleService articleService, ITagService tagService, IArticleTagService articleTagService, IContentService contentService) {
        this.articleService = articleService;
        this.tagService = tagService;
        this.articleTagService = articleTagService;
        this.contentService = contentService;
    }


    /**
     * 通过 id 获得 article
     */
    @GetMapping("/{id}")
    public ArticleVO getArticleVO(@PathVariable Integer id) {
        Article article = articleService.getById(id);
        List<String> tagNames = articleTagService.getTagNamesByArticleId((long)id);
        return article.transVO(tagNames);
    }

    /**
     * 分页查询
     */
    @GetMapping("")
    public HashMap<String, Object> list(@RequestParam String page, @RequestParam String limit, @RequestParam String search,
                                        @RequestParam String sort) {
        log.trace("page is: " + page + " and limit is:" + limit);

        HashMap<String, Object> map = new HashMap<>(2);
        Page<Article> page1 = new Page<>(Long.parseLong(page), Long.parseLong(limit));

        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", search);
        queryWrapper.orderBy(true, !"desc".equals(sort));

        IPage<Article> articlePage = articleService.page(page1, queryWrapper);
        List<Article> articles = articlePage.getRecords();

        List<ArticleVO> articleVOList = new ArrayList<>();
        // 将 dao 转化成 vo
        for (Article article : articles) {
            List<String> tagNames  = articleTagService.getTagNamesByArticleId(article.getId());
            articleVOList.add(article.transVO(tagNames));
        }
        long count = articlePage.getTotal();

        map.put("data", articleVOList);
        map.put("count", count);

        return map;
    }

    /**
     * 添加 article
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping("")
    public String saveArticle(@ModelAttribute ArticleVO articleVO, @RequestParam MultipartFile file) {
        Article article = new Article();
        ArticleTag articleTag = new ArticleTag();

        log.trace(articleVO.getTitle());
        // 文章名不可相同
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("title", articleVO.getTitle());
        if (articleService.count(articleQueryWrapper) > 0) {
            return "fail";
        }

        // insert article and get article_id
        article.setGmtCreate(LocalDateTime.now());
        article.setGmtModified(LocalDateTime.now());
        article.setCommentState(articleVO.getCommentState());
        article.setTitle(articleVO.getTitle());
        article.setState(articleVO.getState());
        log.trace("save article: " + article);
        articleService.saveAndGetId(article);
        Long articleId = article.getId();

        // save article's tags
        List<String> tags = articleVO.getTags();
        List<ArticleTag> articleTags = new ArrayList<>();
        for (String tagName : tags){
            QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", tagName);
            Tag tag = tagService.getOne(queryWrapper);
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tag.getId());
            articleTag.setGmtCreate(LocalDateTime.now());
            articleTag.setGmtModified(LocalDateTime.now());
            //articleTagService.save(articleTag);
            articleTags.add(articleTag);
        }
        articleTagService.saveBatch(articleTags);
        log.trace("save article's tags: " + tags.toString());

        // save article content
        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            String articleContent = new String(bytes);
            Content content = new Content();
            content.setArticleId(articleId);
            content.setContent(articleContent);
            content.setGmtCreate(LocalDateTime.now());
            content.setGmtModified(LocalDateTime.now());
            contentService.save(content);
            log.trace("save content");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }

    /**
     * 更新 article
     */
    @Transactional(rollbackFor = Exception.class)
    @PutMapping("/{id}")
    public String articleEdit(@PathVariable Integer id, @ModelAttribute ArticleVO articleVO, @RequestParam MultipartFile file) throws IOException {
        log.trace(articleVO.getTitle() + "  " + articleVO.getId());

        QueryWrapper<Article> articleQueryWrapper1 = new QueryWrapper<>();
        QueryWrapper<Article> articleQueryWrapper2 = new QueryWrapper<>();

        articleQueryWrapper1.eq("title", articleVO.getTitle());

        if (articleService.count(articleQueryWrapper1) > 0) {
            return "fail";
        }

        articleQueryWrapper2.eq("id", id);
        Article article = new Article();
        article.setState(articleVO.getState());
        article.setCommentState((articleVO.getCommentState()));
        article.setTitle(articleVO.getTitle());
        articleService.update(article, articleQueryWrapper2);
        log.trace("update article");

        // Get the file and save it somewhere
        byte[] bytes = file.getBytes();
        String articleContent = new String(bytes);
        Content content = new Content();
        content.setContent(articleContent);
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", id);
        contentService.update(content, queryWrapper);
        log.trace("update content");

        List<String> newNames = articleVO.getTags();
        articleTagService.updateBatchByArticleId(newNames, (long)id);
        return "success";
    }


    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable String id) {
        log.trace("delete article id:" + id);
        if (articleService.removeById(id)) {
            return "success";
        } else {
            return "fail";
        }
    }

}

