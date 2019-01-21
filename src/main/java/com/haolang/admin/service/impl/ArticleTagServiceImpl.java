package com.haolang.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.haolang.admin.entity.ArticleTag;
import com.haolang.admin.entity.Tag;
import com.haolang.admin.mapper.ArticleTagMapper;
import com.haolang.admin.mapper.TagMapper;
import com.haolang.admin.service.IArticleTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 郝浪
 * @since 2019-01-17
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements IArticleTagService {

    private final
    ArticleTagMapper articleTagMapper;
    private final
    TagMapper tagMapper;

    @Autowired
    IArticleTagService articleTagService;

    @Autowired
    public ArticleTagServiceImpl(ArticleTagMapper articleTagMapper, TagMapper tagMapper) {
        this.articleTagMapper = articleTagMapper;
        this.tagMapper = tagMapper;
    }

    @Override
    public List<String> getTagNamesByArticleId(Long articleId) {
        QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>();
        List<String> tagNames = new ArrayList<>();

        queryWrapper.eq("article_id", articleId);
        List<ArticleTag> articleTags = articleTagMapper.selectList(queryWrapper);
        for (ArticleTag articleTag : articleTags) {
            Long tagId = articleTag.getTagId();
            Tag tag = tagMapper.selectById(tagId);
            String tagName = tag.getName();
            tagNames.add(tagName);
        }

        return tagNames;
    }

    @Override
    public void updateBatchByArticleId(List<String> newNames, Long articleId) {
        QueryWrapper<ArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        articleTagService.remove(queryWrapper);
        List<ArticleTag> articleTags = new ArrayList<>();
        ArticleTag articleTag = new ArticleTag();
        for (String tagName : newNames){
            QueryWrapper<Tag> queryWrapper2 = new QueryWrapper<>();
            queryWrapper.eq("name", tagName);
            Tag tag = tagMapper.selectOne(queryWrapper2);
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tag.getId());
            articleTag.setGmtCreate(LocalDateTime.now());
            articleTag.setGmtModified(LocalDateTime.now());
            articleTags.add(articleTag);
        }
        articleTagService.saveBatch(articleTags);
    }
}
