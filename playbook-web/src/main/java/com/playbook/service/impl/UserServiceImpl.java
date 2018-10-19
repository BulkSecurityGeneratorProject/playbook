package com.playbook.service.impl;

import com.playbook.dto.UserDTO;
import com.playbook.mapper.UserMapper;
import com.playbook.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        log.debug("Save user: {}", userDTO.toString());
        return (UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.toEntity(userDTO))));

    }

    @Override
    public List<UserDTO> getAll(){
        log.debug("Request to get all users");
        return(UserMapper.INSTANCE.userToUserDTO(userRepository.findAll()));
    }
}
