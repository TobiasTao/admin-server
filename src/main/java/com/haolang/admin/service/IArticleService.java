package com.haolang.admin.service;

import com.haolang.admin.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.haolang.admin.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.ws.spi.WebServiceFeatureAnnotation;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 郝浪
 * @since 2019-01-17
 */
public interface IArticleService extends IService<Article> {

    Long saveAndGetId(Article article);

}
