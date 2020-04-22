package com.memoforward.blog.service;

import com.memoforward.blog.model.User;
import org.springframework.stereotype.Service;

public interface IUserService {
    User checkUser(String username, String password);

    User findUserByUsername(String username);

    void saveUser(User user);

    long countUsers();
}
