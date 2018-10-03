package com.playbook.dto;

import com.playbook.entity.Role;
import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UserDTO {

    @NotNull
    private Long dni;

    @NotNull
    private String nombre;

    @NotNull
    private String apellidos;

    @NotNull
    private Role role;
}
