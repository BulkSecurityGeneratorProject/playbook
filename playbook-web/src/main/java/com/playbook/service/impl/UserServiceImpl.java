package com.playbook.service.impl;

import com.playbook.dto.UserDTO;
import com.playbook.entity.Authority;
import com.playbook.mapper.UserMapper;
import com.playbook.repository.AuthorityRepository;
import com.playbook.repository.UserRepository;
import com.playbook.security.AuthoritiesConstants;
import com.playbook.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if(userDTO.getPassword() != null){
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        return (UserMapper.INSTANCE.toDto(userRepository.save(UserMapper.INSTANCE.toEntity(userDTO))));

    }

    @Override
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        userDTO.setActivated(true);
        Set<Authority> authorities = new HashSet<>(1);
        authorities.add(authorityRepository.findById(AuthoritiesConstants.USER).get());
        userDTO.setAuthorities(authorities);
        return (createUser(userDTO));
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

    @Override
    @Transactional
    public void deleteUser(String login) {
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

}
