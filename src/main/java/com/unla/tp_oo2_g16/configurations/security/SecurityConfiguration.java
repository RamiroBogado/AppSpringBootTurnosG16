package com.unla.tp_oo2_g16.configurations.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.unla.tp_oo2_g16.services.implementations.UserServiceImp; // <- Corrección importante

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserServiceImp userServiceImp;

    public SecurityConfiguration(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                	auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    auth.requestMatchers(
                            "/css/**", "/imgs/**", "/js/**",
                            "/vendor/bootstrap/css/**", "/vendor/jquery/**", "/vendor/bootstrap/js/**",
                            "/api/v1/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/swagger-ui.html"
                    ).permitAll();
                    auth.requestMatchers(
                            "/auth/login", "/auth/loginProcess", "/auth/loginSuccess", "/auth/logout","/auth/index","/", "/auth/register","/auth/guardar-user"
                    ).permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/auth/login");
                    login.loginProcessingUrl("/auth/loginProcess");
                    login.usernameParameter("username");
                    login.passwordParameter("password");
                    login.defaultSuccessUrl("/auth/loginSuccess", true);
                    login.permitAll();
                })
                .logout(logout -> {
                    logout.logoutUrl("/auth/logout");
                    logout.logoutSuccessUrl("/auth/login");
                    logout.permitAll();
                })
                .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @SuppressWarnings("deprecation")
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userServiceImp);
        return provider;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}