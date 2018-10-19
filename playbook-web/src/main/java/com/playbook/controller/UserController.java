package com.playbook.controller;

import com.playbook.dto.UserDTO;
import com.playbook.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/usuarios")
    public String getUsers(Model model){
        List<UserDTO> users = userService.getAll();
        model.addAttribute("users", users);
        return ("users");
    }


}
