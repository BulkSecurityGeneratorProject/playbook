package com.playbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playbook.entity.Authority;
import lombok.Data;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {

    private Long id;

    @NotNull
    private String dni;

    @Pattern(regexp = "^[_'.@A-Za-z0-9-]*$")
    @Size(min = 1, max = 100)
    private String login;

    @JsonIgnore // Indicamos que se omita esta campo en la serializacion
    @Size(min = 6, max = 60)
    private String password;

    @NotEmpty
    @Size(max = 50)
    private String nombre;

    @NotEmpty
    private String apellidos;

    @Email
    @Size(max = 100)
    private String email;

    @NotNull
    private boolean activated = false;

    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    public boolean isNew() {
        return (this.id == null);
    }
}
