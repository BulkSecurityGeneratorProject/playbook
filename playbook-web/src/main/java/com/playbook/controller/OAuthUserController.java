package com.playbook.controller;

import com.playbook.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthUserController {

    @RequestMapping("/user")
    public User user(@AuthenticationPrincipal User principal) {
        return principal;
    }
}
