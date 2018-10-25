package com.playbook.service;

import com.playbook.entity.User;
import com.playbook.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private UserRepository respository;

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User myUser = respository.findByLogin(login);

        if(myUser == null) {
            logger.error("User does not exists ".concat(login));
            throw new UsernameNotFoundException("User does not exists ".concat(login));
        }

        List<SimpleGrantedAuthority> roles = myUser.getAuthorities().stream().map(r -> {
            return new SimpleGrantedAuthority(r.getName());
        }).collect(Collectors.toList());

        if(myUser.getAuthorities().isEmpty()) {
            logger.error("User does not have any role");
            throw new UsernameNotFoundException("User does not have any role ".concat(login));
        }

        return new org.springframework.security.core.userdetails.User(
                myUser.getLogin(),
                myUser.getPassword(),
                myUser.isActivated(),
                true,
                true,
                true,
                roles);
    }
}
