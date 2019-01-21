package com.haolang.admin.service;

import com.haolang.admin.entity.Tag;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * @author :  TobaisTao
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ITagServiceTest {

    @Autowired
    ITagService tagService;
    @Test
    public void saveTag(){
        Tag tag = new Tag();
        tag.setGmtCreate(LocalDateTime.now());
        tag.setGmtModified(LocalDateTime.now());
        tag.setIconUrl(" ");
        tag.setName("C");

        tagService.save(tag);
    }
}