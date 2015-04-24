package com.codex.restCrud.service;

import com.codex.restCrud.dao.ProjectDao;
import com.codex.restCrud.dao.CommentDao;
import com.codex.restCrud.dao.UserDao;
import com.codex.restCrud.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Natasha Levkovich
 * @since 10.04.15
 */
@Service
public class NewProjectProcessor {
    @Autowired
    ProjectDao projectDao;

    @Autowired
    UserDao userDao;

    @Autowired
    CommentDao commentDao;

    public Project addProject(NewProjectForm newProjectForm, Integer userId) {
        Project project = new Project();

        project.setName(newProjectForm.getTitle());
        project.setSynopsis(newProjectForm.getSynopsis());
        project.setUser(userDao.getLazyById(userId));
        project.setLastModified(System.currentTimeMillis());

        return projectDao.addProject(project);
    }

}
