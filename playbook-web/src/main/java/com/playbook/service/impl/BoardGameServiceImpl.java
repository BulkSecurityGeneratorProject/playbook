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

import static org.springframework.http.HttpMethod.GET;

@Slf4j
@Service
public class BoardGameServiceImpl implements BoardGameService{

    private final String BOARD_GAME_URL = "http://playbook-rest:8888/boardgames";
    private RestTemplate restTemplate;

    @Override
    @Transactional(readOnly = true)
    public Resources<Resource<BoardGameDTO>> findAll(){
        ParameterizedTypeReference<Resources<Resource<BoardGameDTO>>> responseType = new ParameterizedTypeReference<Resources<Resource<BoardGameDTO>>>() {};
        ResponseEntity<Resources<Resource<BoardGameDTO>>> responseEntity = restTemplate.exchange(BOARD_GAME_URL, GET, null, responseType);
        return responseEntity.getBody();
    }

    @Override
    public Resource<BoardGameDTO> findOne(Long id) {
        return null;
    }
}
