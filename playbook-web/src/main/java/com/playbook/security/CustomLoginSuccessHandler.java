package com.playbook.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    public CustomLoginSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SessionFlashMapManager sessionFlashMapManager = new SessionFlashMapManager();
        FlashMap flashMap = new FlashMap();
        flashMap.put("success", "Bienvenido " + authentication.getName() + ". Inicio de sesion correcto");
        sessionFlashMapManager.saveOutputFlashMap(flashMap, request, response);
        if(authentication != null){
            log.info("El usuario " + authentication.getName() + " ha iniciado sesion");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
