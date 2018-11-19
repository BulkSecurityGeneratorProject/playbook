package com.playbook.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="collections")
public class Coleccion {

    @EmbeddedId
    private EmbeddableCollectionIdentity coleccionIdentity;
}
