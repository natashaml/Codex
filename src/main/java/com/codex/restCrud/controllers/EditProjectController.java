package com.codex.restCrud.controllers;

import com.codex.restCrud.service.EditProjectProcessor;
import com.codex.restCrud.service.EditTaskForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

/**
 * @author Natasha Levkovich
 * @since 10.04.15
 */
@Controller
public class EditProjectController {
    @Autowired
    EditProjectProcessor ebProcessor;

    @RequestMapping(value = "/users/{userId}/{projectId}")
    public String getEditorPage(@PathVariable("userId") Integer userId,
                                @PathVariable("projectId") Integer projectId, ModelMap modelMap,
                                @RequestParam(value = "action", required = false) String action,
                                Principal principal) {
        return ebProcessor.getEditProjectPage(principal, userId, projectId, modelMap,
                action);
    }

    @RequestMapping(value = "/users/{userId}/{projectId}/{taskId}", method = RequestMethod.POST)
    public String saveTask(@PathVariable("userId") Integer userId,
                           @PathVariable("projectId") Integer projectId,
                           @PathVariable("taskId") Integer taskId, ModelMap modelMap,
                           Principal principal, EditTaskForm saveTaskForm) {
        return ebProcessor.saveTask(saveTaskForm, principal, userId,
                projectId, taskId, modelMap);
    }


    @RequestMapping(value = "/users/{userId}/{projectId}/{taskId}", method = RequestMethod.GET)
    public String editTask(@PathVariable("userId") Integer userId,
                           @PathVariable("projectId") Integer projectId,
                           @PathVariable("taskId") Integer taskId,
                           @RequestParam(value = "action", required = false) String action,
                           ModelMap modelMap, Principal principal) {
        return ebProcessor.editTask(modelMap, principal, userId, projectId,
                taskId, action);
    }

}
