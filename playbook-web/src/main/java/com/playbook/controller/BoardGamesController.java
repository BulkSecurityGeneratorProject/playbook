package com.playbook.controller;

import com.playbook.dto.BoardGameDTO;
import com.playbook.service.BoardGameService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardGamesController {

    private BoardGameService boardGameService;

    public BoardGamesController(BoardGameService boardGameService) {
        this.boardGameService = boardGameService;
    }

    @GetMapping("/juegos")
    public String showAllGames(Model model){
        Resources<Resource<BoardGameDTO>> games = boardGameService.findAll();
        model.addAttribute("games", games);
        return ("games/list");
    }

    @GetMapping("/juegos/{id}")
    public String showAllGames(@PathVariable Long id, Model model){
        Resource<BoardGameDTO> games = boardGameService.findOne(id);
        model.addAttribute("games", games);
        return ("games/list");
    }
}
