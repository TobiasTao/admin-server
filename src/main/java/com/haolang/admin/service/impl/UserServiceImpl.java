package com.haolang.admin.service.impl;

import com.haolang.admin.entity.User;
import com.haolang.admin.mapper.UserMapper;
import com.haolang.admin.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
