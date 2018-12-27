package com.playbook.controller;

import com.google.common.base.Preconditions;
import com.playbook.service.MessageService;
import com.playbook.service.UserService;
import com.playbook.vm.ChatMessage;
import com.playbook.vm.QueueMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MessageController {

    private final UserService userService;
    private final MessageService messageService;

    public MessageController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping("/messages/{id}/enviar")
    public String showUserMessages(@PathVariable("id") long id, Model model){
        Preconditions.checkNotNull(id, "No se encontro el usuario");
        List<String> usuarios = userService.findAll()
                .stream()
                .map(user -> user.getLogin())
                .sorted()
                .collect(Collectors.toList());
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("mensaje", new QueueMessage());
        return ("messages/send");
    }

    @PostMapping("/messages/send")
    public String sendMessage(@ModelAttribute("mensaje") QueueMessage mensaje){
        Preconditions.checkNotNull(mensaje, "El mensaje no se recibio correctamente");
        return null;
    }
}
