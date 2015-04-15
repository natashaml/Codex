package com.codex.restCrud.controllers;

import com.codex.restCrud.service.ReadProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
/**
 * @author Natasha Levkovich
 * @since 11.04.15
 */
@Controller
@RequestMapping("/read")
public class ReadController {
    @Autowired
    ReadProcessor readProcessor;

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public String readBook(@PathVariable("projectId") Integer bookId,
                           ModelMap modelMap, Principal principal) {
        return readProcessor.readBookPage(principal, modelMap, bookId);
    }

}
