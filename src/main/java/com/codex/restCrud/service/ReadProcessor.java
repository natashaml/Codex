package com.codex.restCrud.service;

import com.codex.restCrud.dao.ProjectDao;
import com.codex.restCrud.dao.UserDao;
import com.codex.restCrud.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.security.Principal;

/**
 * @author Natasha Levkovich
 * @since 10.04.15
 */
@Service
public class ReadProcessor {
    @Autowired
    private ProjectDao projectDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityProcessor securityProcessor;

    public String readBookPage(Principal principal, ModelMap modelMap,
                               Integer bookId) {
        securityProcessor.identifyViewer(principal, modelMap);
        Project project = projectDao.getEagerById(bookId);
        modelMap.addAttribute("project", project);
        return "read";
    }
}
