package com.playbook.game;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class BoardGameServiceImpl implements BoardGameService{

    private BoardGameRepository boardGameRepository;

    @Autowired
    public BoardGameServiceImpl(BoardGameRepository boardGameRepository) {
        this.boardGameRepository = boardGameRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardGameDTO> findAll() {
        List<BoardGame> boardGames = boardGameRepository.findAll();
        // boardGames.stream().map(BoardGameMapper.INSTANCE::toDto).collect(Collectors.toList());
        return BoardGameMapper.INSTANCE.BoardGameToBoardGameDTO(boardGames);
    }

    @Override
    public BoardGameDTO save(BoardGameDTO boardGameDTO) {
        BoardGame boardGame = boardGameRepository.save(BoardGameMapper.INSTANCE.toEntity(boardGameDTO));
        return BoardGameMapper.INSTANCE.toDto(boardGame);
    }

    @Override
    public BoardGameDTO findById(Long id) {
        Optional<BoardGame> boardGame = boardGameRepository.findById(id);
        return BoardGameMapper.INSTANCE.toDto(boardGame.get());
    }

    @Override
    public void deleteById(Long id) {
        boardGameRepository.deleteById(id);
    }

    @Override
    public BoardGameDTO update(Long id, BoardGameDTO boardGameDTO) {
        return boardGameRepository.findById(id).map(
                boardGame -> {
                    boardGame.setGameId(id);
                    boardGame.setDescripcion(boardGameDTO.getDescripcion());
                    boardGame.setDesigner(boardGameDTO.getDesigner());
                    boardGame.setDuracion(boardGameDTO.getDuracion());
                    boardGame.setImagen(boardGameDTO.getImagen());
                    boardGame.setMinPlayers(boardGameDTO.getMinPlayers());
                    boardGame.setMaxPlayers(boardGameDTO.getMaxPlayers());
                    boardGame.setNombre(boardGameDTO.getNombre());
                    boardGame.setPublicacion(boardGameDTO.getPublicacion());
                    boardGame.setPuntuacion(boardGameDTO.getPuntuacion());
                    boardGame.setThumbnail(boardGameDTO.getThumbnail());
                    boardGame.setImagen(boardGameDTO.getImagen());
                    boardGame.setVotos(boardGameDTO.getVotos());
                    return BoardGameMapper.INSTANCE.toDto(boardGameRepository.save(boardGame));
                }
        ).orElseGet(() -> {
            boardGameDTO.setGameId(id);
            return BoardGameMapper.INSTANCE.toDto(boardGameRepository.
                    save(BoardGameMapper.INSTANCE.toEntity(boardGameDTO)));
        });
    }
}
