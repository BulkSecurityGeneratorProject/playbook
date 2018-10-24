package com.playbook;

import com.playbook.security.CustomAccessDeniedHandler;
import com.playbook.security.CustomAuthenticationFailureHandler;
import com.playbook.security.CustomLoginSuccessHandler;
import com.playbook.security.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

// Con estas dos anotaciones desactivamos la configuracion de seguridad por defecto
@Configuration
@EnableWebSecurity
// Necesitamos esta anotacion con los parametros habilitados para @Secured y @PreAuthorized
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/css/**", "/vendor/**", "/js/**", "/img/**").permitAll()
                .antMatchers("/", "/index", "/login", "/h2-console/**").permitAll()
               // .antMatchers("/usuarios/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().successHandler(loginSuccessHandler()).loginPage("/login").permitAll()
               // .failureHandler(authenticationFailureHandler())
                .and().logout()
                .deleteCookies("JSESSIONID")
               // .logoutSuccessHandler(logoutSuccessHandler())
                .permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user")
                .password("password")
                .roles("USER")
                .and()
                .withUser("admin")
                .password("admin")
                .roles("USER", "ADMIN");
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        // Esto es necesario si se usan claves en memoria y no hay password encoder
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    AuthenticationSuccessHandler loginSuccessHandler(){
        return new CustomLoginSuccessHandler();
    }

}
