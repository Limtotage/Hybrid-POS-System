package com.example.hybridpos.jwtsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.hybridpos.entity.MyUser;
import com.example.hybridpos.enums.Role;
import com.example.hybridpos.repository.UserRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository.findByUsername("admin").isEmpty()) {

                MyUser admin = new MyUser();

                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);

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
