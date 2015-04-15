package com.codex.restCrud.controllers;

import com.codex.restCrud.service.DeleteProcessor;
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
@RequestMapping("/delete")
public class DeleteController {
    @Autowired
    DeleteProcessor deleteProcessor;

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public String deleteProject(@PathVariable("projectId") Integer projectId,
                                ModelMap modelMap, Principal principal) {
        return deleteProcessor.deleteProject(principal, modelMap, projectId);
    }

}
