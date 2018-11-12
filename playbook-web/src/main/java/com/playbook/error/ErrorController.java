package com.playbook.error;

import com.playbook.error.exception.UserNotFoundException;
import org.springframework.context.NoSuchMessageException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request, HttpServletResponse response){
        ErrorVM error = new ErrorVM(ex.getMessage(), "El usuario indicado no existe.");
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("error", error);
        return modelAndView;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request, HttpServletResponse response, Principal principal){
        ErrorVM error = new ErrorVM("Acceso no autorizado", "Lo siento " + principal.getName() + ". No tienes permisos para acceder a este recurso.");
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("error", error);
        return modelAndView;
    }

    @ExceptionHandler(NoSuchMessageException.class)
    public ModelAndView handleNoSuchMessageException(NoSuchMessageException ex, HttpServletRequest request, HttpServletResponse response, Principal principal){
        ErrorVM error = new ErrorVM("Mensaje no encontrado", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("error", error);
        return modelAndView;
    }

    @ExceptionHandler(MailAuthenticationException.class)
    public ModelAndView handleMailAuthenticationException(NoSuchMessageException ex, HttpServletRequest request, HttpServletResponse response, Principal principal){
        ErrorVM error = new ErrorVM("Error durante el envío del correo", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("error", error);
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex, HttpServletRequest request, HttpServletResponse response){
        ErrorVM error = new ErrorVM("Error genérico", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("error", error);
        return modelAndView;
    }
}

