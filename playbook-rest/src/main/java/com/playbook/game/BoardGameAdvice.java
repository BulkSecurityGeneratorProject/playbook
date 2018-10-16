package com.playbook.game;

import com.playbook.game.exceptions.ApiError;
import com.playbook.game.exceptions.FieldErrorVM;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class BoardGameAdvice {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ApiError> notFoundHandler(NotFoundException ex) {
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> processValidationError(MethodArgumentNotValidException ex) {
        log.error("Excepcion MethodArgumentNotValidException");
        BindingResult result = ex.getBindingResult();
        List<FieldErrorVM> fieldErrors = result.getFieldErrors().stream()
                .map(f -> new FieldErrorVM(f.getObjectName(), f.getField(), f.getCode()))
                .collect(Collectors.toList());
        log.debug("Montamos el error");
        ApiError error = new ApiError.ApiErrorBuilder().
                withStatus(HttpStatus.BAD_REQUEST.value()).
                withMensaje("Parametros invalidos").
                withExcepcion("Se produjo un error por parámetros invalidos").
                withErrors(fieldErrors).
                build();
        log.debug("Error montado: " + error.toString());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
