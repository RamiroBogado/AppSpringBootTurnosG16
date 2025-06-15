package com.unla.tp_oo2_g16.configurations.seeder;


import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.unla.tp_oo2_g16.models.entities.RoleEntity;
import com.unla.tp_oo2_g16.enums.RoleType;
import com.unla.tp_oo2_g16.models.entities.UserEntity;
import com.unla.tp_oo2_g16.repositories.RoleRepository;
import com.unla.tp_oo2_g16.repositories.UserRepository;

import java.util.Set;

@Component
@Order(1)
public class UsersSeeder implements CommandLineRunner {

    private static final String passwordGeneric = "foo1234";

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UsersSeeder(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadRoles();
        loadUsers();
    }

    private void loadUsers() {
        if (userRepository.count() == 0){
            loadUserAdmin();
        }
    }

    private void loadUserAdmin() {
        userRepository.save(buildUserAdmin("admin@gmail.com",passwordGeneric));
    }

    private UserEntity buildUserAdmin(String emailUser, String passwordUser) {
        return UserEntity.builder()
                .emailUser(emailUser)
                .passwordUser(encryptPassword(passwordUser))
                .active(true)
                .roles(Set.of(roleRepository.findBynombre(RoleType.ADMIN).get()))
                .build(); 
    }

    private void loadRoles() {
        if (roleRepository.count() == 0){
            roleRepository.save(buildRole(RoleType.USER));
            roleRepository.save(buildRole(RoleType.ADMIN));
        }
    }

    private RoleEntity buildRole(RoleType roleNombre) {
        return RoleEntity.builder()
                .nombre(roleNombre)
                .build();
    }

    private String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(7);
        return passwordEncoder.encode(password);
    }
}