package com.playbook.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.playbook.dto.BoardGameDTO;
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
public class BoardGameServiceImplResources {

    private final String BOARD_GAME_URL = "http://playbook-rest:8888/boardgames";
    private RestTemplate restTemplate;

    @Transactional(readOnly = true)
    public Resources<Resource<BoardGameDTO>> findAll(){
        ParameterizedTypeReference<Resources<Resource<BoardGameDTO>>> responseType = new ParameterizedTypeReference<Resources<Resource<BoardGameDTO>>>() {};
        ResponseEntity<Resources<Resource<BoardGameDTO>>> responseEntity = restTemplate.exchange(BOARD_GAME_URL, GET, null, responseType);
        return responseEntity.getBody();
    }

    // Metodo para poder generar un mapper en el restTemplate que me permita mapear una respuesta de tipo Resources<Resource<XXXXXXXX>>
    private RestTemplate generateRestTemlate(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        RestTemplate template = new RestTemplate(Collections.<HttpMessageConverter<?>> singletonList(converter));

        return template;
    }
}
