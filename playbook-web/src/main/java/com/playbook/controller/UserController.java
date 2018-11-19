package com.playbook.controller;

import com.google.common.base.Preconditions;
import com.playbook.dto.UserDTO;
import com.playbook.entity.Authority;
import com.playbook.error.exception.UserNotFoundException;
import com.playbook.security.AuthoritiesConstants;
import com.playbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private UserService userService;
    private MessageSource messageSource;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    // Listado de usuarios
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String showAllUsers(Model model){
        model.addAttribute("users", userService.findAll());
        return ("users/list");
    }

    // Guarda o actualiza usuario
    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String saveOrUpdateUser(@ModelAttribute("userForm") @Valid UserDTO user,
                                   BindingResult result, RedirectAttributes flash) {

        if (result.hasErrors()) {
            return "users/userform";
        } else {
            // Add message to flash scope
            if(user.isNew()){
                user = userService.createUser(user);
                flash.addFlashAttribute("success", "Usuario creado correctamente!");
            }else{
                user = userService.updateUser(user).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
                flash.addFlashAttribute("success", "Usuario modificado correctamente!");
            }

            // POST/REDIRECT/GET
            return "redirect:/users/" + user.getId();
        }
    }

    // Muestra formulario modificacion usuario
    @RequestMapping(value = "/users/{id}/update", method = RequestMethod.GET)
    public ModelAndView showUpdateUserForm(@PathVariable("id") long id, ModelMap model) {
        Preconditions.checkArgument(id > 0, "Id de usuario incorrecto");
        UserDTO user = userService.findById(id);
        // Buscamos los roles para mostrarlos por pantalla
        model.addAttribute("roles", userService.findAllAuthorities());
        model.addAttribute("user", user);
        return new ModelAndView("users/userform", model);
    }

    // Muestra el formulario de modificacion de usuario obteniendo el usuario actualmente logado
    @RequestMapping(value = "/users/update", method = RequestMethod.GET)
    public ModelAndView showUpdateCurrentUserForm(Authentication authentication, ModelMap model) {
        String login = authentication.getName();
        UserDTO user = userService.findByLogin(login);
        // Buscamos los roles para mostrarlos por pantalla
        model.addAttribute("roles", userService.findAllAuthorities());
        model.addAttribute("user", user);
        return new ModelAndView("users/userform", model);
    }

    // Muestra formulario para crear un usuario
    @Secured(AuthoritiesConstants.ADMIN)
    @RequestMapping(value = "/users/add", method = RequestMethod.GET)
    public ModelAndView showAddUserForm() {
        return new ModelAndView("users/userform", "user", new UserDTO());
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/users/{id}/delete")
    public String deleteUser(@PathVariable("id") long id, RedirectAttributes flash) {
        Preconditions.checkArgument(id > 0, "Id de usuario incorrecto");
        userService.deleteUser(id);
        flash.addFlashAttribute("success", "Usuario dado de baja correctamente");
        return "redirect:/users";
    }

    // Muestra detalle de usuario
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public String showUser(@PathVariable("id") long id, Model model, RedirectAttributes flash) {
        Preconditions.checkArgument(id > 0, "Id de usuario incorrecto");
        UserDTO user = userService.findById(id);
        if (user == null) {
            flash.addFlashAttribute("error", "Usuario no encontrado");
        }
        model.addAttribute("user", user);
        String roles = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.joining(", "));
        model.addAttribute("roles", roles);
        return "users/show";
    }


}
