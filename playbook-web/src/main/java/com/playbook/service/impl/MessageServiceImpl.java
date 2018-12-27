package com.playbook.service.impl;

import com.playbook.service.MessageService;
import com.playbook.vm.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService{

    private RestTemplate restTemplate;

    public MessageServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessage> findUserMessages(Long id){
        return null;
    }
}
