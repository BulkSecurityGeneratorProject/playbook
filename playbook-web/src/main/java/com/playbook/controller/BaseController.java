package com.playbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Jose Gonzalez on 24/09/2018.
 */

@Controller
public class BaseController {

    private static final Logger log = LoggerFactory.getLogger(BaseController.class);

    @GetMapping("/")
    public String getInitial(){
        log.debug("Entramos en la pagina inicial");
        return ("index");
    }
}
