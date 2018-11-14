package com.playbook.error;

import com.playbook.error.exception.PasswordChangeException;
import com.playbook.error.exception.PasswordResetException;
import com.playbook.error.exception.UserNotFoundException;
import com.playbook.vm.KeyAndPasswordVM;
import org.springframework.context.NoSuchMessageException;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request, HttpServletResponse response){
        ErrorVM error = new ErrorVM("Usuario no encontrado", ex.getMessage());
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
    public ModelAndView handleMailAuthenticationException(NoSuchMessageException ex, HttpServletRequest request, HttpServletResponse response){
        ErrorVM error = new ErrorVM("Error durante el envío del correo", ex.getMessage());
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("error", error);
        return modelAndView;
    }

    @ExceptionHandler(PasswordResetException.class)
    public ModelAndView handlePasswordResetException(PasswordResetException ex, HttpServletRequest request, HttpServletResponse response, RedirectAttributes flash){
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        flash.addFlashAttribute("fallo", ex.getMessage());
        return modelAndView;
    }

    @ExceptionHandler(PasswordChangeException.class)
    public ModelAndView handlePasswordChangeExceptionException(PasswordChangeException ex, HttpServletRequest request, HttpServletResponse response, RedirectAttributes flash){
        ModelAndView modelAndView = new ModelAndView("users/change");
        modelAndView.addObject("passData", new KeyAndPasswordVM());
        flash.addFlashAttribute("fallo", ex.getMessage());
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

