package com.playbook.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return ("index");
    }

    @GetMapping("/volver")
    public String volver(HttpServletRequest request) {
        // Vuelve a la pagina anterior, o sino vuelve a home
        return getPreviousPageByRequest(request).orElse("index");
    }

    /**
     * Returns the viewName to return for coming back to the sender url
     *
     * @param request Instance of {@link HttpServletRequest} or use an injected instance
     * @return Optional with the view name. Recomended to use an alternativa url with
     * {@link Optional#orElse(java.lang.Object)}
     */
    protected Optional<String> getPreviousPageByRequest(HttpServletRequest request)
    {
        return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
    }

    @GetMapping("/juegos")
    public String prueba(){
        return ("loginviejo");
    }
}
