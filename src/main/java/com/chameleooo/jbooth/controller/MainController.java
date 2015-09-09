package com.chameleooo.jbooth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class MainController {

    @RequestMapping(value = "")
    public String index(Map<String, Object> model) {
        //Return a template name
        return "welcome";
    }

    @RequestMapping(value = "get-ready")
    public String getReady(Map<String, Object> model) {
        //Return a template name
        return "get-ready";
    }

}
