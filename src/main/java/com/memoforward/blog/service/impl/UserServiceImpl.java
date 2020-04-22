package com.memoforward.blog.service.impl;

import com.memoforward.blog.enums.BlogError;
import com.memoforward.blog.exception.CustomizedBlogException;
import com.memoforward.blog.mapper.UserMapper;
import com.memoforward.blog.model.User;
import com.memoforward.blog.model.UserExample;
import com.memoforward.blog.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0)  throw new CustomizedBlogException(BlogError.USER_NOT_FOUND);
        if(users.size() > 1) throw new CustomizedBlogException(BlogError.TOO_MANY_USERS);
        User user = users.get(0);
        if(!user.getPassword().equals(password)) throw new CustomizedBlogException(BlogError.PASSWORD_NOT_CORRECT);
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0) return null;
        else return users.get(0);
    }

    @Override
    public void saveUser(User user) {
        //验证用户名是否重复
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(user.getUsername());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() != 0) throw new CustomizedBlogException(BlogError.USERNAME_CANNOT_BE_USED);
        userMapper.insert(user);
    }

    @Override
    public long countUsers() {
         return userMapper.countByExample(new UserExample());
    }
}
