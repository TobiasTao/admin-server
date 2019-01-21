package com.haolang.admin.vo;

import lombok.Data;

import java.util.List;

/**
 * @author :  TobaisTao
 */
@Data
public class ArticleVO {
    private Long id;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 更新时间
     */
    private String gmtModified;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 评论数目
     */
    private Integer commentCount;

    /**
     * 浏览数目
     */
    private Integer viewCount;


    /**
     * 是否开启评论
     */
    private Boolean commentState;

    /**
     * 文章状态 0：未发布，1：发布
     */
    private Boolean state;

    private List<String> tags;
}
