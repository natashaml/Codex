package com.codex.restCrud.controllers;

import com.codex.restCrud.IdResponseUI;
import com.codex.restCrud.dao.UserDao;
import com.codex.restCrud.service.AdminProcessor;
import com.codex.restCrud.service.SecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    UserDao userDao;

    @Autowired
    AdminProcessor adminProcessor;

    @Autowired
    SecurityProcessor securityProcessor;

    @Secured("hasRole(1)")
    @RequestMapping(method = RequestMethod.GET)
    public String giveAdminPage(ModelMap modelMap, Principal principal) {
        securityProcessor.identifyViewer(principal, modelMap);
        adminProcessor.prepareUsers(modelMap);
        return "admin";
    }

    @Secured("hasRole(1)")
    @RequestMapping(value = "/addUser.htm", method = RequestMethod.POST)
    @ResponseBody
    public String addUser(@RequestParam(value = "login", required = true) String login,
                          @RequestParam(value = "password", required = true) String password,
                          ModelMap modelMap, Principal principal) {
        //IdResponseUI idResponseUI = new IdResponseUI();
        adminProcessor.saveUser(modelMap, principal, login, password);
        //idResponseUI.setId(userDao.save(user));
        return "redirect:/admin";
    }

    @Secured("hasRole(1)")
    @RequestMapping(value = "/editUser.htm", method = RequestMethod.POST)
    @ResponseBody
    public String editUser(@RequestParam(value = "id", required = true) Integer id,
                           @RequestParam(value = "login", required = true) String login,
                           @RequestParam(value = "password", required = true) String password,
                           ModelMap modelMap, Principal principal) {
        IdResponseUI idResponseUI = new IdResponseUI();
        adminProcessor.editUser(modelMap, principal, id, login, password);
        idResponseUI.setId(id);
        return "redirect:/admin";
    }

    @Secured("hasRole(1)")
    @RequestMapping(value = "/delete.htm", method = RequestMethod.POST)
    @ResponseBody
    public String goToDeleteUserPage(@RequestParam(value = "id", required = true) Integer id) {
        adminProcessor.deleteUser(id);
        return "redirect:/admin";
    }
}
