package com.unla.tp_oo2_g16.configurations.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.unla.tp_oo2_g16.services.implementations.UserServiceImp;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    private final UserServiceImp userServiceImp;

    public SecurityConfiguration(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        var swaggerUser = org.springframework.security.core.userdetails.User
            .withUsername("g16")
            .password(passwordEncoder().encode("g16"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(swaggerUser);
    }

    @SuppressWarnings("deprecation")
    @Bean
    AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userServiceImp);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @SuppressWarnings("deprecation")
    @Bean
    AuthenticationProvider inMemoryAuthenticationProvider(InMemoryUserDetailsManager inMemoryUserDetailsManager) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(inMemoryUserDetailsManager);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager() {
        return new ProviderManager(
            List.of(
                inMemoryAuthenticationProvider(inMemoryUserDetailsManager()),
                daoAuthenticationProvider()
            )
        );
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/admin/**").hasRole("ADMIN")
                .requestMatchers(
                    "/css/**", "/imgs/**", "/js/**",
                    "/vendor/bootstrap/css/**", "/vendor/jquery/**", "/vendor/bootstrap/js/**",
                    "/api/v1/**",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).permitAll()
                .requestMatchers(
                    "/auth/login", "/auth/loginProcess", "/auth/loginSuccess", "/auth/logout",
                    "/auth/index", "/", "/auth/register", "/auth/guardar-user"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults())
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/loginProcess")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/auth/loginSuccess", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/auth/login")
                .permitAll()
            );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
