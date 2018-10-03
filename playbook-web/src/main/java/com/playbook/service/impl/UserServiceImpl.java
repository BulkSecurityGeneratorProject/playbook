package com.playbook.service.impl;

import com.playbook.dto.UserDTO;
import com.playbook.mapper.UserMapper;
import com.playbook.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDTO createUser(UserDTO userDTO) {
        log.debug("save user: {}", userDTO.toString());
        return (UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.toEntity(userDTO))));

    }
}
