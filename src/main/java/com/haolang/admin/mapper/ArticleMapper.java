package com.haolang.admin.mapper;

import com.haolang.admin.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Max;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 郝浪
 * @since 2019-01-17
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 插入 article 并返回 主键值
     */
    Long insertAndGetId(Article article);
}
