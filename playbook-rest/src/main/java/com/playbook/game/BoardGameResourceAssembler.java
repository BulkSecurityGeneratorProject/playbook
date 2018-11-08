package com.playbook.game;

import com.playbook.game.v2.BoardGameResourceV2;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class BoardGameResourceAssembler implements ResourceAssembler <BoardGameDTO, Resource<BoardGameDTO>>{

    // Esta clase nos permite obtener un Resource<BoardGameDTO> de un BoarGameDTO

    @Override
    public Resource<BoardGameDTO> toResource(BoardGameDTO boardGameDTO) {
        return new Resource<>(boardGameDTO,
                linkTo(methodOn(BoardGameResourceV2.class).getBoardGame(boardGameDTO.getGameId())).withSelfRel(),
                linkTo(methodOn(BoardGameResourceV2.class).getAllBoardGame()).withRel("boardgames"));

    }

}
