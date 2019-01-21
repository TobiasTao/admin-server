package com.haolang.admin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.haolang.admin.entity.ArticleTag;
import com.haolang.admin.entity.Tag;
import com.haolang.admin.mapper.ArticleTagMapper;
import com.haolang.admin.mapper.TagMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author :  TobaisTao
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class IArticleTagServiceTest {

    @Autowired
    IArticleTagService articleTagService;
    @Test
    public void listTest(){
        List<String> tagNames = articleTagService.getTagNamesByArticleId((long) 2);
        log.trace("tagNames: " + tagNames);
    }


}