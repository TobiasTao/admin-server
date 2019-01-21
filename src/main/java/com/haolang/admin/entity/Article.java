package com.haolang.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.haolang.admin.vo.ArticleVO;
import com.haolang.admin.vo.TagVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 郝浪
 * @since 2019-01-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @TableField("gmt_modified")
    private LocalDateTime gmtModified;

    @TableField("title")
    private String title;

    /**
     * 发布状态；1：发布；0：未发布
     */
    @TableField("state")
    private Boolean state;

    @TableField("view_count")
    private Integer viewCount;

    @TableField("comment_count")
    private Integer commentCount;

    /**
     * 1:允许评论；0：关闭评论
     */
    @TableField("comment_state")
    private Boolean commentState;

    /**
     * todo: 去除transVO, 使用 article 的构造函数代替
     */
    public ArticleVO transVO(List<String> tags){
        ArticleVO articleVO = new ArticleVO();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        articleVO.setGmtCreate(df.format(this.gmtCreate));
        articleVO.setGmtModified(df.format(this.gmtModified));
        articleVO.setTitle(this.title);
        articleVO.setId(this.id);
        articleVO.setState(this.state);
        articleVO.setCommentCount(this.commentCount);
        articleVO.setViewCount(this.viewCount);
        articleVO.setTags(tags);
        return articleVO;
    }

}
