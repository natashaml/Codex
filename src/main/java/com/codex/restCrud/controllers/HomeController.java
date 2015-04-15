package com.codex.restCrud.controllers;

import com.codex.restCrud.service.HomeProcessor;
import com.codex.restCrud.service.SecurityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
/**
 * @author Natasha Levkovich
 * @since 09.04.15
 */
@Controller
public class HomeController {
    @Autowired
    HomeProcessor homeProcessor;

    @Autowired
    SecurityProcessor securityProcessor;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String giveHomePage(ModelMap modelMap, Principal principal) {
        /*String tags = homeProcessor.getCloudTags();
		modelMap.addAttribute("tags", tags); */
        securityProcessor.identifyViewer(principal, modelMap);
        return "home";
    }


}
