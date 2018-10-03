package com.playbook.mapper;

import com.playbook.dto.UserDTO;
import com.playbook.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    UserDTO toDto(User user);
    User toEntity(UserDTO clienteDTO);
    List<UserDTO> userToUserDTO(List<User> users);
}
