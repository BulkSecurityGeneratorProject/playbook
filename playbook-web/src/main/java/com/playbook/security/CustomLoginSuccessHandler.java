package com.playbook.security;

import com.playbook.dto.UserDTO;
import com.playbook.entity.Authority;
import com.playbook.entity.User;
import com.playbook.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

@Slf4j
public class CustomLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

    @Autowired
    UserService userService;

    public CustomLoginSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Si el usuario se ha authenticado mediante OAuth2, comprobamos si esta en la base de datos, y si no
        // le damos de alta de forma local para poder gestionarlo
        /*
        if(authentication instanceof OAuth2Authentication) {
            LinkedHashMap<String, Object> details = (LinkedHashMap<String, Object>) ((OAuth2Authentication) authentication).getUserAuthentication().getDetails();
            String id = String.valueOf(details.get("id"));
            UserDTO user = userService.getById(Long.valueOf(id));
            User user1 = new User();
            if (user == null) {
                user = new UserDTO();
                user.setId(Long.valueOf(id));
                user.setActivated(true);
                String nombreCompleto = String.valueOf(details.get("name"));
                if(nombreCompleto != null) {
                    String[] nombre = nombreCompleto.split(" ");
                    user.setNombre(nombre[0]);
                    user.setApellidos(nombre[1].concat(" ").concat(nombre.length == 3 ? nombre[2] : ""));
                }
                user.setAuthorities(new HashSet<Authority>(Arrays.asList(new Authority(AuthoritiesConstants.USER))));
            }
            userService.createUser(user);
        }
*/
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
