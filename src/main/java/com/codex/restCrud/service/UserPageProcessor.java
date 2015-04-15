package com.codex.restCrud.service;

import com.codex.restCrud.dao.UserDao;
import com.codex.restCrud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Natasha Levkovich
 * @since 11.04.15
 */
@Service
public class UserPageProcessor {
    @Autowired
    UserDao userDao;

    public User getUser(String login) {
        return userDao.getLazyByName(login);
    }

    public List<User> getUsers() {
        return userDao.getLazyAllUsers();
    }
}
