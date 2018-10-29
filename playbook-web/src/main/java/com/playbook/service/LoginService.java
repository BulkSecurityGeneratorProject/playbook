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
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UserDetails details = userRepository.findOneByLogin(login).map(myUser -> {

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
        }).orElseThrow(() -> new UsernameNotFoundException("User does not exists ".concat(login)));

        return details;
    }
}
