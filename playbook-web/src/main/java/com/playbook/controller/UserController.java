package com.playbook.controller;

import com.playbook.dto.UserDTO;
import com.playbook.entity.Authority;
import com.playbook.security.AuthoritiesConstants;
import com.playbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@Controller
public class UserController {

    private UserService userService;
    private MessageSource messageSource;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/usuarios")
    public String getUsers(Model model){
        List<UserDTO> users = userService.getAll();
        model.addAttribute("users", users);
        return ("users");
    }

    @GetMapping("/registro")
    public String addUser(ModelMap map) {
        map.addAttribute("user", new UserDTO());
        return "registro";
    }

    @RequestMapping(value = "/usuarios/registro", method = RequestMethod.POST)
    public String registerUser(@Valid UserDTO user, BindingResult result, Model model, Locale locale, RedirectAttributes flash) {

        if (result.hasErrors()) {
            flash.addFlashAttribute("error", "Se produjo un error durante el proceso de registro");
            return "redirect:/";
        }
        user = userService.registerUser(user);
        if(user.getId() > 0) {
            flash.addFlashAttribute("success", "Usuario registrado correctamente");
        }else{
            flash.addFlashAttribute("error", "Se produjo un error durante el proceso de registro");
        }
        return "redirect:/";
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/eliminar/{login}")
    public String eliminar(@PathVariable(value = "login") String login, RedirectAttributes flash, Locale locale) {
        userService.deleteUser(login);
        flash.addFlashAttribute("success", "Usuario dado de baja correctamente");
        return "redirect:/usuarios";
    }

}
