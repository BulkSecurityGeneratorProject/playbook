package com.playbook.vm;

import lombok.Data;

import java.io.Serializable;

@Data
public class QueueMessage implements Serializable{

    private String remitente;
    private String destinatario;
    private String texto;
}
