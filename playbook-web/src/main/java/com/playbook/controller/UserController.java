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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
        user.setActivated(true);
        user.setAuthorities(new HashSet<Authority>(Arrays.asList(new Authority(AuthoritiesConstants.USER))));
        user = userService.createUser(user);
        if(user.getId() > 0) {
            flash.addFlashAttribute("success", "Usuario registrado correctamente");
        }else{
            flash.addFlashAttribute("error", "Se produjo un error durante el proceso de registro");
        }
        return "redirect:login";
    }

}
