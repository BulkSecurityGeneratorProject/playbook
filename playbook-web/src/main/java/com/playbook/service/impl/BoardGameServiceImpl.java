package com.playbook.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playbook.dto.BoardGameDTO;
import com.playbook.service.BoardGameService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Service
public class BoardGameServiceImpl implements BoardGameService{

    private final String BOARD_GAME_URL = "http://playbook-rest:8888/api/v1/boardgames";
    private RestTemplate restTemplate;

    public BoardGameServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardGameDTO> findAll(){
        ResponseEntity<List<BoardGameDTO>> response = restTemplate.exchange(BOARD_GAME_URL, GET,
                null, new ParameterizedTypeReference<List<BoardGameDTO>>(){});
        List<BoardGameDTO> games = response.getBody();
        return games;
    }

    @Override
    @Transactional(readOnly = true)
    public BoardGameDTO findOne(Long id) {
        BoardGameDTO gameDTO = restTemplate.getForObject(BOARD_GAME_URL + "/" + id, BoardGameDTO.class);
        return gameDTO;
    }
}
