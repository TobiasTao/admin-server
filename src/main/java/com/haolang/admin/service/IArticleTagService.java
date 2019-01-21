package com.haolang.admin.service;

import com.haolang.admin.entity.ArticleTag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郝浪
 * @since 2019-01-17
 */
public interface IArticleTagService extends IService<ArticleTag> {

    /**
     * 得到文章的标签
     * @param articleId article_id
     * @return tagNames
     */
    List<String> getTagNamesByArticleId(Long articleId);

    void updateBatchByArticleId(List<String> newNames, Long id);
}
