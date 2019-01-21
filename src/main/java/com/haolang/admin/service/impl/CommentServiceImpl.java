package com.haolang.admin.service.impl;

import com.haolang.admin.entity.Comment;
import com.haolang.admin.mapper.CommentMapper;
import com.haolang.admin.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 郝浪
 * @since 2019-01-17
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
