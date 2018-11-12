package com.playbook.service;


import com.playbook.dto.BoardGameDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface BoardGameService {

    @Transactional(readOnly = true)
    List<BoardGameDTO> findAll();

    @Transactional(readOnly = true)
    BoardGameDTO findOne(Long id);
}
