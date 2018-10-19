package com.playbook.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Slf4j
@Controller
public class LoginController {

    @Value("${mensajes.login.error}")
    private String mensajeError;

    @RequestMapping(value = "/login")
    public String login(Model model, Principal principal, RedirectAttributes flash){
        log.debug("Login");
        // Con esto detectamos si ya se ha hecho login y evitamos el doble login
        if(principal != null){
            flash.addFlashAttribute("info", "El usuario ya ha iniciado sesion");
            return "redirect:/";
        }
        return "login";
    }
}
