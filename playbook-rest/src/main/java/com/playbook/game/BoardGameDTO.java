package com.playbook.game;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class BoardGameDTO implements Serializable{


    private Long gameId;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String imagen;

    @NotEmpty
    private String thumbnail;

    @Min(value = 1)
    private Integer minPlayers;

    @Min(value = 1)
    private Integer maxPlayers;

    @Min(value = 5)
    private Integer duracion;

    @Min(value = 0)
    @Max(value = 10)
    private Long puntuacion;

    @NotEmpty
    private String descripcion;

    @NotEmpty
    private String publicacion;

    @NotEmpty
    private String designer;

    private Long votos;
}
