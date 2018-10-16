package com.playbook.game;

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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Slf4j
@RestController
public class BoardGameResource {

    private BoardGameServiceImpl boardGameService;
    private BoardGameResourceAssembler assembler;

    public BoardGameResource(BoardGameServiceImpl boardGameService,
                             BoardGameResourceAssembler boardGameResourceAssembler) {
        this.boardGameService = boardGameService;
        this.assembler = boardGameResourceAssembler;
    }

    @GetMapping("/boardgames")
    Resources<Resource<BoardGameDTO>> getAllBoardGame() {
        log.debug("Request to get all boardgames");
        // Usamos Resources para tener una raiz con sus propios enlaces, y luego enlaces para cada Resource
        // Usamos el assembler para simplificar
        List<Resource<BoardGameDTO>> employees = boardGameService.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(employees,
                linkTo(methodOn(BoardGameResource.class).getAllBoardGame()).withSelfRel());
    }

    @PostMapping("/boardgames")
    ResponseEntity<Resource<BoardGameDTO>> newBoardGame(@Valid @RequestBody BoardGameDTO boardGameDTO) throws URISyntaxException {
        log.debug("Create new boardgame {}", boardGameDTO.toString());
        Resource<BoardGameDTO> resource = assembler.toResource(boardGameService.save(boardGameDTO));
        // Usamos un ResponseEntity para devolver un codigo de estado 201
        // Usamos un Resource para devolver un enlace al elemento creado y a la lista completa
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/boardgames/{id}")
    Resource<BoardGameDTO> getBoardGame(@PathVariable Long id) {
        log.debug("Request to get boardgame with id {}", id);
        // Usamos el assembler para devolver una referencia al juego y a la lista
        return assembler.toResource ((BoardGameDTO) boardGameService.findById(id));
    }

    @PutMapping("/boardgames/{id}")
    ResponseEntity<?> modifyBoardGame(@Valid @RequestBody BoardGameDTO boardGameDTO, @PathVariable Long id) throws URISyntaxException {
        log.debug("Request to update boadgame {} with id {}", boardGameDTO, id);
        Resource<BoardGameDTO> resource = assembler.toResource(boardGameService.update(id, boardGameDTO));
        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/boardgames/{id}")
    ResponseEntity<?> deleteBoardGame(@PathVariable Long id) {
        log.debug("Request to delete boardgame with id {}", id);
        boardGameService.deleteById(id);
        // Con noContent devolvemos un 204
        return ResponseEntity.noContent().build();
    }
}
