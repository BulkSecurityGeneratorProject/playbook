package com.playbook.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playbook.entity.Authority;
import lombok.Data;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {

    @NotNull
    private String dni;

    @NotNull
    @Pattern(regexp = "^[_'.@A-Za-z0-9-]*$")
    @Size(min = 1, max = 100)
    private String login;

    @NotNull
    @JsonIgnore // Indicamos que se omita esta campo en la serializacion
    @Size(min = 60, max = 60)
    private String password;

    @NotEmpty
    @Size(max = 50)
    private String nombre;

    @NotEmpty
    private String apellidos;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @NotNull
    private boolean activated = false;

    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();
}
