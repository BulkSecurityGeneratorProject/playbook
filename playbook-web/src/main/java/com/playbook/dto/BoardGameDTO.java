package com.playbook.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Component
public class BoardGameDTO implements Serializable{


    private Long gameId;

    private String nombre;

    private String imagen;

    private String thumbnail;

    private Integer minPlayers;

    private Integer maxPlayers;

    private Integer duracion;

    private Long puntuacion;

    private String descripcion;

    private String publicacion;

    private String designer;

    private Long votos;
}
