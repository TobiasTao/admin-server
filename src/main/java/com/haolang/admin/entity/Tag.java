package com.haolang.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

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
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("gmt_create")
    private LocalDateTime gmtCreate;

    @TableField("gmt_modified")
    private LocalDateTime gmtModified;

    @TableField("name")
    private String name;

    /**
     * 标签的图标链接
     */
    @TableField("icon_url")
    private String iconUrl;

    public TagVO transVO(){
        TagVO tagVO = new TagVO();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        tagVO.setGmtCreate(df.format(this.gmtCreate));
        tagVO.setGmtModified(df.format(this.gmtModified));
        tagVO.setName(this.name);
        tagVO.setId(Integer.parseInt(String.valueOf(this.id)));
        tagVO.setIconUrl(this.iconUrl);
        return tagVO;
    }

}
