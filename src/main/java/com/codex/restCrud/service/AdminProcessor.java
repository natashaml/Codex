package com.codex.restCrud.service;

import com.codex.restCrud.dao.TaskDao;
import com.codex.restCrud.dao.UserDao;
import com.codex.restCrud.model.Task;
import com.codex.restCrud.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.security.Principal;
import java.util.List;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@Service
public class AdminProcessor {
    @Autowired
    UserDao userDao;


    @Autowired
    TaskDao taskDao;

    public void prepareUsers(ModelMap modelMap) {
        List<User> users = userDao.getLazyAllUsers();
        modelMap.addAttribute("users", users);
    }

    public void deleteUser(Integer id) {
        User user = userDao.getLazyById(id);
        userDao.deleteUser(user);
    }

    public void editUser(ModelMap modelMap, Principal principal, Integer id, String login, String password) {
        User user = new User();

        user.setId(id);
        user.setName(login);
        user.setPassword(password);
        userDao.editUser(user);
    }

    public void setAdmin(Integer id) {
        userDao.setAsAdmin(id);
    }

    public void unsetAdmin(Integer id) {
        userDao.setAsUser(id);
    }

    public void saveUser(ModelMap modelMap, Principal principal, String login, String password) {
        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        User user = new User();

        user.setName(login);
        user.setPassword(encoder.encodePassword(password, null));
        userDao.save(user);

    }

    public void saveUserTask(ModelMap modelMap, Principal principal, Integer taskId) {
        User user = new User();

        Task tasks = taskDao.findById(taskId);
        userDao.save(user);
    }
}
