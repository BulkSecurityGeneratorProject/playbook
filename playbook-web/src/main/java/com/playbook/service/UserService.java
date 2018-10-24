package com.playbook.service;

import com.playbook.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO createUser(UserDTO user);

    List<UserDTO> getAll();
}
