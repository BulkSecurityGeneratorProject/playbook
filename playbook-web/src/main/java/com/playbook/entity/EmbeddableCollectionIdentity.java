package com.playbook.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class EmbeddableCollectionIdentity implements Serializable {

    @Column(name="user_id")
    private Long userId;

    @Column(name="boardgame_id")
    private Long boardgameId;
}
