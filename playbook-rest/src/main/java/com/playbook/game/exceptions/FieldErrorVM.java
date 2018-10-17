package com.playbook.game.exceptions;

import java.io.Serializable;

public class FieldErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Object valor;

    private final String campo;

    private final String message;

    public FieldErrorVM(String campo, Object valor, String message) {
        this.valor = valor;
        this.campo = campo;
        this.message = message;
    }

    public Object getValor() {
        return valor;
    }

    public String getCampo() {
        return campo;
    }

    public String getMessage() {
        return message;
    }

}
