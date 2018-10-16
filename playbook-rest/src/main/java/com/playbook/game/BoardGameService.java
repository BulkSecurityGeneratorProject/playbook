package com.playbook.game;


import java.util.List;

public interface BoardGameService {

    List<BoardGameDTO> findAll();

    BoardGameDTO save(BoardGameDTO boardGameDTO);

    BoardGameDTO findById(Long id);

    void deleteById(Long id);

    BoardGameDTO update(Long id, BoardGameDTO boardGameDTO);
}
