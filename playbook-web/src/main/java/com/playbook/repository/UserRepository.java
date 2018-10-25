package com.playbook.repository;

import com.playbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String>{

    User findByLogin(String login);

}
