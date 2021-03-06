package com.playbook.repository;

import com.playbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{

    Optional<User> findOneByLogin(String login);

    Optional<User> findOneByActivationKey(String key);

    Optional<User> findOneByEmailIgnoreCase(String email);

    Optional<User> findOneByResetKey(String key);
}
