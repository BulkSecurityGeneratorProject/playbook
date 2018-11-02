package com.playbook.service;

import com.playbook.dto.UserDTO;
import com.playbook.entity.Authority;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDTO createUser(UserDTO user);

    UserDTO registerUser(UserDTO user);

    List<UserDTO> findAll();

    @Transactional(readOnly = true)
    UserDTO findById(Long id);

    @Transactional
    void deleteUser(long id);

    @Transactional
    Optional<UserDTO> updateUser(@Valid UserDTO user);

    List<Authority> findAllAuthorities();

    UserDTO findByLogin(String login);
}
