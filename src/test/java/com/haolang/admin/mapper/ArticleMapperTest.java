package com.haolang.admin.mapper;

import com.haolang.admin.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;


/**
 * @author :  TobaisTao
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleMapperTest {

    @Autowired
    ArticleMapper articleMapper;
    @Test
    public void insertTest(){
        Article article = new Article();

        article.setGmtCreate(LocalDateTime.now());
        article.setGmtModified(LocalDateTime.now());
        article.setTitle("test article 1");
        article.setState(true);
        article.setCommentState(true);
        articleMapper.insertAndGetId(article);
        log.trace("get id: "+ article.getId());
    }
}