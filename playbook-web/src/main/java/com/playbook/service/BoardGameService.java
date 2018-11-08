package com.playbook.service;


import com.playbook.dto.BoardGameDTO;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface BoardGameService {
    @Transactional(readOnly = true)
    Resources<Resource<BoardGameDTO>> findAll();

    @Transactional(readOnly = true)
    Resource<BoardGameDTO> findOne(Long id);
}
