package com.playbook.service;


import com.playbook.vm.ChatMessage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageService {
    @Transactional(readOnly = true)
    List<ChatMessage> findUserMessages(Long id);
}
