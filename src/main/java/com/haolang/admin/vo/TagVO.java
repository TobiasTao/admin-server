package com.haolang.admin.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * tag 类的显示层对象
 * @author :  TobaisTao
 */
@Data
public class TagVO {
    private Integer id;

    /**
     * 创建时间
     */
    private String gmtCreate;

    /**
     * 更新时间
     */
    private String gmtModified;

    /**
     * 标签名
     */
    private String name;

    /**
     * 图标url地址
     */
    private String iconUrl;
}
