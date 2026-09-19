package com.hybridPOS.auth_service.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hybridPOS.auth_service.entity.MyUser;
import com.hybridPOS.auth_service.enums.Role;
import com.hybridPOS.auth_service.repository.UserRepository;

@Configuration
public class DataInitializer {

@Bean
CommandLineRunner createAdmin(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder) {

    return args -> {

        if (userRepository.findFirstByRole(Role.ADMIN).isEmpty()) {

            MyUser admin = new MyUser();

            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);

            userRepository.save(admin);

            System.out.println("=================================");
            System.out.println("DEFAULT ADMIN CREATED");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
            System.out.println("=================================");
        }
    };
}
}
