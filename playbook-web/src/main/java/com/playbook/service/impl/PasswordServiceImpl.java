package com.playbook.service.impl;

import com.playbook.dto.UserDTO;
import com.playbook.entity.User;
import com.playbook.error.ErrorUtil;
import com.playbook.error.exception.PasswordChangeException;
import com.playbook.error.exception.PasswordResetException;
import com.playbook.error.exception.UserNotFoundException;
import com.playbook.mapper.UserMapper;
import com.playbook.repository.UserRepository;
import com.playbook.service.MailService;
import com.playbook.service.PasswordService;
import com.playbook.service.util.RandomUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class PasswordServiceImpl implements PasswordService{

    private final UserRepository userRepository;
    private final MailService mailService;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordServiceImpl(UserRepository userRepository, MailService mailService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserDTO requestPasswordReset(String mail) {
        User userToReset = userRepository.findOneByEmailIgnoreCase(mail)
                .filter(User::isActivated)
                .map(user -> {
                    user.setResetKey(RandomUtil.generateResetKey());
                    user.setResetDate(Instant.now());
                    return user;
                }).orElseThrow(() -> new UserNotFoundException("El correo electronico no existe"));
        User user = userRepository.save(userToReset);
        mailService.sendPasswordResetMail(user);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public boolean resetKeyExist(String key){
        return userRepository.findOneByResetKey(key).isPresent();
    }

    @Override
    public UserDTO resetPassword(String token, String password){
        User user = userRepository.findOneByResetKey(token).orElseThrow(() -> new PasswordResetException("Token inválido. Vuelva a usar el enlace de su correo"));
        user.setPassword(passwordEncoder.encode(password));
        user.setActivationKey(null);
        user.setResetDate(null);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(user);
    }

    @Override
    public UserDTO changePassword(String login, String password, String newPassword){
        User user = userRepository.findOneByLogin(login).orElseThrow(() -> new UserNotFoundException("El usuario logado no existe"));
        ErrorUtil.check(!passwordEncoder.matches(password, user.getPassword()), new PasswordChangeException("Contraseña incorrecta"));
        user.setPassword(passwordEncoder.encode(newPassword));
        user = userRepository.save(user);
        return UserMapper.INSTANCE.toDto(user);
    }
}
