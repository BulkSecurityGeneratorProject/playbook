package com.playbook.controller;

import com.playbook.dto.UserDTO;
import com.playbook.service.MailService;
import com.playbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Locale;

@Controller
public class LoginController {

    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public LoginController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @RequestMapping(value = "/login")
    public String login(Model model, Principal principal, RedirectAttributes flash,
                        @RequestParam(value = "error", required = false)String error,
                        @RequestParam(name = "logout", required = false) String logout){
        // Con esto detectamos si ya se ha hecho login y evitamos el doble login
        if(principal != null){
            flash.addFlashAttribute("info", "El usuario ya ha iniciado sesion");
            return "redirect:/";
        }
        if(error != null){
            model.addAttribute("fallo", "Nombre de usuario o contraseña incorrectos");
        }

        if(logout != null) {
            model.addAttribute("success", "Sesión cerrada correctamente!");
        }
        return "login";
    }

    @GetMapping("/registro")
    public String showSignInForm(ModelMap map) {
        map.addAttribute("user", new UserDTO());
        return "registro";
    }

    @RequestMapping(value = "/registro", method = RequestMethod.POST)
    public String registerUser(@Valid UserDTO user, BindingResult result, Model model, Locale locale, RedirectAttributes flash) {

        if (result.hasErrors()) {
            flash.addFlashAttribute("fallo", "Se produjo un error durante el proceso de registro");
            return "redirect:/";
        }
        user = userService.registerUser(user);

        if(user.getId() > 0) {
            flash.addFlashAttribute("success", "Usuario registrado correctamente! Recibirá un correo para activar su cuenta");
        }else{
            flash.addFlashAttribute("fallo", "Se produjo un error durante el proceso de registro");
        }
        return "redirect:/";
    }
}
