package com.playbook.controller;

import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import com.playbook.dto.UserDTO;
import com.playbook.error.ErrorUtil;
import com.playbook.error.exception.PasswordResetException;
import com.playbook.service.PasswordService;
import com.playbook.service.UserService;
import com.playbook.vm.KeyAndPasswordVM;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Objects;
import java.util.Optional;

@Controller
public class AccountController {

    private final UserService userService;
    private final PasswordService passwordService;

    public AccountController(UserService userService, PasswordService passwordService) {
        this.userService = userService;
        this.passwordService = passwordService;
    }

    @GetMapping("/activate")
    public String activateAccount(@RequestParam(value = "key") String key, RedirectAttributes flash) {
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "Token incorrecto");
        Optional<UserDTO> user = userService.activateRegistration(key);
        flash.addFlashAttribute("success", "Enhorabuena " + user.get().getLogin() + ". Tu cuenta ha sido activada");
        return "redirect:/";
    }

    @GetMapping("/password/reset/init")
    public String showResetMailForm(){
        return ("users/reset");
    }

    @GetMapping("/password/change/init")
    public ModelAndView showChangePasswordForm(){
        ModelAndView modelAndView = new ModelAndView("users/change");
        modelAndView.addObject("passData", new KeyAndPasswordVM());
        return (modelAndView);
    }

    @GetMapping("/password/reset/finish")
    public String showResetPasswordForm(@RequestParam(value = "key") String key,
                                              ModelMap model,
                                              RedirectAttributes flash){
        Preconditions.checkArgument(StringUtils.isNotEmpty(key), "Token incorrecto");
        // Comprobamos si el token recibido existe
        if(!passwordService.resetKeyExist(key)){
            flash.addFlashAttribute("fallo", "Token incorrecto. No se encontro el usuario");
            return "redirect:/";
        }
        KeyAndPasswordVM keyAndPasswordVM = new KeyAndPasswordVM();
        keyAndPasswordVM.setKey(key);
        model.put("keyAndPass", keyAndPasswordVM);
        return "users/password";
    }

    @PostMapping("/password/reset/init")
    public String requestResetPassword(@RequestParam(value = "email") String email, RedirectAttributes flash){
        Preconditions.checkNotNull(email, "Email incorrecto");
        passwordService.requestPasswordReset(email);
        flash.addFlashAttribute("success", "Se ha enviado un correo electrónico a su cuenta para resetear su contraseña");
        return "redirect:/";
    }

    @PostMapping("/password/reset/finish")
    public String finishResetPassword(@ModelAttribute("keyAndPass") KeyAndPasswordVM keyAndPasswordVM,
                                      RedirectAttributes flash) {
        Preconditions.checkNotNull(keyAndPasswordVM, "No se recibieron datos");
        Preconditions.checkNotNull(keyAndPasswordVM.getKey(), "Token inválido. Vuelva a acceder usando el enlace de su correo");
        Preconditions.checkNotNull(keyAndPasswordVM.getConfirmation(), "Clave inválida. Su contraseña no puede estar vacia");
        Verify.verify(keyAndPasswordVM.getPassword().equals(keyAndPasswordVM.getConfirmation()),"El password y la confirmacion no coinciden");
        passwordService.resetPassword(keyAndPasswordVM.getKey(), keyAndPasswordVM.getPassword());
        flash.addFlashAttribute("success", "Su contraseña ha sido cambiada");
        return "redirect:/";
    }

    @PostMapping("/password/change/finish")
    public String finishChangePassword(@ModelAttribute("passData") KeyAndPasswordVM keyAndPasswordVM,
                                      RedirectAttributes flash, Authentication authentication) {
        Preconditions.checkNotNull(keyAndPasswordVM, "No se recibieron datos");
        String login = authentication.getName();
        passwordService.changePassword(login, keyAndPasswordVM.getPassword(), keyAndPasswordVM.getConfirmation());
        flash.addFlashAttribute("success", "Su contraseña ha sido cambiada");
        return "redirect:/";
    }
}
