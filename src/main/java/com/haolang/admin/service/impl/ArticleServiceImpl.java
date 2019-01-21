package com.haolang.admin.service.impl;

import com.haolang.admin.entity.Article;
import com.haolang.admin.mapper.ArticleMapper;
import com.haolang.admin.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 郝浪
 * @since 2019-01-17
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    private final
    ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public Long saveAndGetId(Article article) {
        return articleMapper.insertAndGetId(article);
    }
}
