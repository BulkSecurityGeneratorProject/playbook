package com.playbook.service.impl;

import com.playbook.dto.UserDTO;
import com.playbook.entity.Authority;
import com.playbook.entity.User;
import com.playbook.error.exception.UserNotFoundException;
import com.playbook.mapper.UserMapper;
import com.playbook.repository.AuthorityRepository;
import com.playbook.repository.UserRepository;
import com.playbook.security.AuthoritiesConstants;
import com.playbook.service.MailService;
import com.playbook.service.UserService;
import com.playbook.service.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.Valid;
import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private AuthorityRepository authorityRepository;
    private MailService mailService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder passwordEncoder,
                           AuthorityRepository authorityRepository,
                           MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authorityRepository = authorityRepository;
        this.mailService = mailService;
    }

    private User saveUser(User user){
        if(user.getPassword() != null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setActivated(false);
        user.setActivationKey(RandomUtil.generateActivationKey());
        return userRepository.save(user);
    }


    @Override
    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        return (UserMapper.INSTANCE.toDto(saveUser(UserMapper.INSTANCE.toEntity(userDTO))));
    }

    @Override
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) {
        Set<Authority> authorities = new HashSet<>(1);
        authorities.add(authorityRepository.findById(AuthoritiesConstants.USER).get());
        userDTO.setAuthorities(authorities);
        // Registramos el nuevo usuario
        User user = saveUser(UserMapper.INSTANCE.toEntity(userDTO));
        // Enviamos el maill de activacion
        mailService.sendActivationEmail(user);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll(){
        return(UserMapper.INSTANCE.userToUserDTO(userRepository.findAll()));
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        return(UserMapper.INSTANCE.toDto(userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"))));
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        userRepository.findById(id).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    @Override
    public Optional<UserDTO> updateUser(@Valid UserDTO userDTO) {
        User newUser = userRepository.findById(userDTO.getId()).
                map(user -> {
                    user.setLogin(userDTO.getLogin());
                    user.setNombre(userDTO.getNombre());
                    user.setApellidos(userDTO.getApellidos());
                    user.setDni(userDTO.getDni());
                    user.setEmail(userDTO.getEmail());
                    user.setActivated(userDTO.isActivated());
                    Set<Authority> managedAuthorities = user.getAuthorities();
                    managedAuthorities.clear();
                    userDTO.getAuthorities().stream()
                            .map(auth -> authorityRepository.findById(auth.getName()))
                            .forEach(auth -> managedAuthorities.add(auth.get()));
                    return user;
                }).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        return Optional.of(UserMapper.INSTANCE.toDto(userRepository.save(newUser)));
    }

    @Override
    public List<Authority> findAllAuthorities(){
        return authorityRepository.findAll();
    }

    @Override
    public UserDTO findByLogin(String login) {
        return(UserMapper.INSTANCE.toDto(userRepository.findOneByLogin(login).orElse(null)));
    }

    @Override
    public Optional<UserDTO> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        User activatedUser = userRepository.findOneByActivationKey(key)
                .map(user -> {
                    // activate given user for the registration key.
                    user.setActivated(true);
                    user.setActivationKey(null);
                    log.debug("Activated user: {}", user);
                    return user;
                }).orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
        return Optional.of(UserMapper.INSTANCE.toDto(userRepository.save(activatedUser)));
    }

}
