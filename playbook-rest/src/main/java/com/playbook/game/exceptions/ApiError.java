package com.playbook.game.exceptions;

import java.io.Serializable;
import java.util.List;

public class ApiError implements Serializable {

    private final Integer status;
    private final String mensaje;
    private final String excepcion;
    private final String error;
    private final List<FieldErrorVM> fieldErrors;

    private ApiError(ApiErrorBuilder builder) {
        this.status = builder.status;
        this.mensaje = builder.mensaje;
        this.excepcion = builder.excepcion;
        this.fieldErrors = builder.fieldErrors;
        this.error = builder.error;
    }

    // Builder para el error
    public static class ApiErrorBuilder {

        private Integer status;
        private String mensaje;
        private String excepcion;
        private List<FieldErrorVM> fieldErrors;
        private String error;

        public ApiErrorBuilder withStatus(Integer status){
            this.status = status;
            return this;
        }

        public ApiErrorBuilder withMensaje(String mensaje){
            this.mensaje = mensaje;
            return this;
        }

        public ApiErrorBuilder withExcepcion(String excepcion){
            this.excepcion = excepcion;
            return this;
        }

        public ApiErrorBuilder withErrors(List<FieldErrorVM> errors){
            this.fieldErrors = errors;
            return this;
        }

        public ApiErrorBuilder withError(String error){
            this.error = error;
            return this;
        }

        public ApiError build(){
            return new ApiError(this);
        }

    }

}
