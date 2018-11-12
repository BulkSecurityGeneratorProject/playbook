package com.playbook.controller;

import com.playbook.dto.UserDTO;
import com.playbook.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class AccountController {

    private final UserService userService;

    public AccountController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/activate")
    public String activateAccount(@RequestParam(value = "key") String key, RedirectAttributes flash, Principal principal) {
        Optional<UserDTO> user = userService.activateRegistration(key);
        flash.addFlashAttribute("success", "Enhorabuena " + user.get().getLogin() + ". Tu cuenta ha sido activada");
        return "redirect:/";
    }

}
