package com.playbook.game;

import com.playbook.game.exceptions.ApiError;
import com.playbook.game.exceptions.FieldErrorVM;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class BoardGameAdvice extends ResponseEntityExceptionHandler{

    private MessageSource messages;

    @Autowired
    public BoardGameAdvice(MessageSource messages) {
        this.messages = messages;
    }

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        ApiError error = new ApiError.ApiErrorBuilder().
                withStatus(HttpStatus.NOT_FOUND.value()).
                withMensaje(messages.getMessage("error.no.encontrado", null, request.getLocale())).
                withExcepcion(HttpStatus.NOT_FOUND.name()).
                build();

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    ResponseEntity<Object> handleAccessDenied(Exception ex, WebRequest request) {
        ApiError error = new ApiError.ApiErrorBuilder().
                withStatus(HttpStatus.FORBIDDEN.value()).
                withMensaje(messages.getMessage("error.no.autorizado", null, request.getLocale())).
                withExcepcion(HttpStatus.FORBIDDEN.name()).
                build();

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        log.error("Excepcion MethodArgumentNotValidException");
        BindingResult result = ex.getBindingResult();
        // Obtenemos la lista de errores
        List<FieldErrorVM> fieldErrors = result.getFieldErrors().stream()
                .map(f -> new FieldErrorVM(f.getField(), f.getRejectedValue(), f.getDefaultMessage()))
                .collect(Collectors.toList());
        // Montamos el error como queremos
        ApiError error = new ApiError.ApiErrorBuilder().
                withStatus(HttpStatus.BAD_REQUEST.value()).
                withMensaje(messages.getMessage("error.parametros.invalidos", null, request.getLocale())).
                withExcepcion(status.BAD_REQUEST.name()).
                withErrors(fieldErrors).
                build();

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> defaultExceptionHandler(Exception ex, WebRequest request) {
        ApiError error = new ApiError.ApiErrorBuilder().
                withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value()).
                withMensaje(messages.getMessage("error.generico", null, request.getLocale())).
                withExcepcion(ex.getMessage()).
                build();

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
