package com.unla.tp_oo2_g16.services.implementations;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unla.tp_oo2_g16.repositories.UserRepository;

import java.text.MessageFormat;

@Service("userService")
public class UserServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String emailUser) throws UsernameNotFoundException {
        return userRepository.findByEmailUser(emailUser).orElseThrow(
                () -> new UsernameNotFoundException(MessageFormat.format("User with email {0} not found", emailUser))
        );
    }

}