package com.haolang.admin.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.haolang.admin.entity.Tag;
import com.haolang.admin.service.ITagService;
import com.haolang.admin.vo.TagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
@RequestMapping("/admin/tags")
public class TagController {


    private final
    ITagService tagService;

    @Autowired
    public TagController(ITagService tagService) {
        this.tagService = tagService;
    }


    /**
     * 通过 id 获得 tag
     */
    @GetMapping("/{id}")
    public Tag getTag(@PathVariable Integer id) {
        return tagService.getById(id);
    }

    /**
     * 分页查询与搜索
     */
    @GetMapping("")
    public HashMap<String, Object> list(@RequestParam String page, @RequestParam String limit, @RequestParam String search,
                                        @RequestParam String sort) {
        log.trace("page is: " + page + " and limit is:" + limit + "search key is: " + search);


        HashMap<String, Object> map = new HashMap<>(2);
        Page<Tag> page1 = new Page<>(Long.parseLong(page), Long.parseLong(limit));

        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", search);
        queryWrapper.orderBy(true, !"desc".equals(sort));

        IPage<Tag> tagPage = tagService.page(page1, queryWrapper);
        List<Tag> tags = tagPage.getRecords();
        List<TagVO> tagVOList = new ArrayList<>();
        // 将 dao 转化成 vo
        for (Tag tag : tags) {
            tagVOList.add(tag.transVO());
        }
        long count = tagPage.getTotal();

        map.put("data", tagVOList);
        map.put("count", count);

        return map;
    }

    /**
     * 添加 tag
     */
    @PostMapping("")
    public String saveTag(@ModelAttribute Tag tag) {
        log.trace(tag.getName());
        QueryWrapper<Tag> tagQueryWrapper = new QueryWrapper<>();
        tagQueryWrapper.eq("name", tag.getName());

        if (tagService.count(tagQueryWrapper) > 0) {
            return "fail";
        }
        tag.setGmtCreate(LocalDateTime.now());
        tag.setGmtModified(LocalDateTime.now());
        tag.setIconUrl(" ");
        tagService.save(tag);
        return "success";
    }

    @PutMapping("/{id}")
    public String tagEdit(@PathVariable Integer id, @ModelAttribute Tag tag) {
        log.trace(tag.getName() + "  " + tag.getId());

        QueryWrapper<Tag> tagQueryWrapper1 = new QueryWrapper<>();
        QueryWrapper<Tag> tagQueryWrapper2 = new QueryWrapper<>();

        tagQueryWrapper1.eq("name", tag.getName());

        if (tagService.count(tagQueryWrapper1) > 0) {
            return "fail";
        }
        tagQueryWrapper2.eq("id", id);
        tagService.update(tag, tagQueryWrapper2);
        return "success";
    }



}
