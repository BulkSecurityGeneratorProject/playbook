package com.playbook.service;

import com.playbook.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO user);

    UserDTO registerUser(UserDTO user);

    List<UserDTO> getAll();

    @Transactional(readOnly = true)
    UserDTO getById(Long id);

    @Transactional
    void deleteUser(String login);
}
