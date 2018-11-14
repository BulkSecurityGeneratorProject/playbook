package com.playbook.service;


import com.playbook.dto.UserDTO;

public interface PasswordService {

    UserDTO requestPasswordReset(String mail);

    boolean resetKeyExist(String key);

    UserDTO resetPassword(String token, String password);

    UserDTO changePassword(String login, String password, String newPassword);
}
