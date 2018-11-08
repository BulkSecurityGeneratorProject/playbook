package com.playbook.game.v1;

import com.playbook.game.BoardGameDTO;
import com.playbook.game.BoardGameResourceAssembler;
import com.playbook.game.BoardGameServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Slf4j
@RestApiControllerV1
public class BoardGameResourceV1 {

    private BoardGameServiceImpl boardGameService;

    public BoardGameResourceV1(BoardGameServiceImpl boardGameService) {
        this.boardGameService = boardGameService;
    }

    @GetMapping("/boardgames")
    ResponseEntity<List<BoardGameDTO>> getAllBoardGameV1() {
        log.debug("Request to get all boardgames");
        List<BoardGameDTO> games = boardGameService.findAll();
        return ResponseEntity.ok(games);
    }

    @PostMapping("/boardgames")
    ResponseEntity<BoardGameDTO> newBoardGameV1(@Valid @RequestBody BoardGameDTO boardGameDTO) throws URISyntaxException {
        log.debug("Create new boardgame {}", boardGameDTO.toString());
        BoardGameDTO game = boardGameService.save(boardGameDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BoardGameResourceV1.class).getBoardGameV1(game.getGameId())).toUri())
                .body(game);
    }

    @GetMapping("/boardgames/{id}")
    ResponseEntity<BoardGameDTO> getBoardGameV1(@PathVariable Long id) {
        log.debug("Request to get boardgame with id {}", id);
        BoardGameDTO game = boardGameService.findById(id);
        return ResponseEntity.ok(game);
    }

    @PutMapping("/boardgames/{id}")
    ResponseEntity<BoardGameDTO> modifyBoardGameV1(@Valid @RequestBody BoardGameDTO boardGameDTO, @PathVariable Long id) throws URISyntaxException {
        log.debug("Request to update boadgame {} with id {}", boardGameDTO, id);
        BoardGameDTO game = boardGameService.update(id, boardGameDTO);
        return ResponseEntity
                .created(linkTo(methodOn(BoardGameResourceV1.class).getBoardGameV1(game.getGameId())).toUri())
                .body(game);
    }

    @DeleteMapping("/boardgames/{id}")
    ResponseEntity<?> deleteBoardGameV1(@PathVariable Long id) {
        log.debug("Request to delete boardgame with id {}", id);
        boardGameService.deleteById(id);
        // Con noContent devolvemos un 204
        return ResponseEntity.noContent().build();
    }
}
