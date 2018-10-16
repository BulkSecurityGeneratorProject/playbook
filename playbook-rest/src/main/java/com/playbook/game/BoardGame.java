package com.playbook.game;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name="boardgames")
public class BoardGame implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gameId;

    @Column(unique=true, nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String imagen;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private Integer minPlayers;

    @Column(nullable = false)
    private Integer maxPlayers;

    @Column(nullable = false)
    private Integer duracion;

    @Column(nullable = false)
    private Long puntuacion;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String publicacion;

    @Column(nullable = false)
    private String designer;

    @Column
    private Long votos;

}
