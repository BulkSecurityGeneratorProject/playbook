package com.playbook.service.impl;

import com.playbook.service.JuegosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JuegosServiceImpl implements JuegosService{

    private RestTemplate restTemplate;

    @Autowired
    public JuegosServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


}
