package com.playbook.service.impl;

import com.playbook.dto.UserDTO;
import com.playbook.mapper.UserMapper;
import com.playbook.repository.UserRepository;
import com.playbook.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        return (UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.toEntity(userDTO))));

    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAll(){
        return(UserMapper.INSTANCE.userToUserDTO(userRepository.findAll()));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getById(Long id){
        return(UserMapper.INSTANCE.toDto(userRepository.findById(id).orElse(null)));
    }

}
