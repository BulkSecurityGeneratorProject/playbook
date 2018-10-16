package com.playbook.game;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BoardGameMapper {

    BoardGameMapper INSTANCE = Mappers.getMapper( BoardGameMapper.class );

    BoardGameDTO toDto(BoardGame BoardGame);
    BoardGame toEntity(BoardGameDTO clienteDTO);
    List<BoardGameDTO> BoardGameToBoardGameDTO(List<BoardGame> BoardGames);
}
