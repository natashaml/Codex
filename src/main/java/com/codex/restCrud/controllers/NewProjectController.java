package com.codex.restCrud.controllers;

import com.codex.restCrud.model.Project;
import com.codex.restCrud.service.NewProjectForm;
import com.codex.restCrud.service.NewProjectProcessor;
import com.codex.restCrud.service.SecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
/**
 * @author Natasha Levkovich
 * @since 10.04.15
 */
@Controller
public class NewProjectController {
    @Autowired
    NewProjectProcessor npProcessor;

    @Autowired
    SecurityProcessor securityProcessor;

    @RequestMapping(value = "users/{userId}/newProject", method = RequestMethod.POST)
    public String processForm(@PathVariable("userId") Integer userId,
                              NewProjectForm addProjectForm, Principal principal, ModelMap modelMap) {
        securityProcessor.identifyViewer(principal, modelMap);
        Project newProject = npProcessor.addBook(addProjectForm, userId);
        if (newProject == null)
            return "newProject";
        else
            return "redirect:/users/" + userId + "/" + newProject.getId();
    }
}
