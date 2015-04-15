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

    public Project addBook(NewProjectForm newProjectForm, Integer userId) {
        Project project = new Project();

        project.setName(newProjectForm.getTitle());
        //project.setCreative(newProjectForm.getCreative());
        project.setSynopsis(newProjectForm.getSynopsis());
        //project.setPicture(newProjectForm.getPicture());

		/*List<String> tagNames = searchProcessor.getKeyWords(newProjectForm
                .getComments());
		List<Comment> tags = tagDao.addComments(commentNames);
		//project.setComments(tags);  */

        project.setUser(userDao.getLazyById(userId));
        //project.setVersion(1);
        project.setLastModified(System.currentTimeMillis());

        return projectDao.addProject(project);
    }

}
