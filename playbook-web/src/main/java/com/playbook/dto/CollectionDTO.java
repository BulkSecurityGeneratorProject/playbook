package com.playbook.dto;


import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class CollectionDTO implements Serializable {

    private Long id;
    private Long userId;
    private Long boardgameId;
}
