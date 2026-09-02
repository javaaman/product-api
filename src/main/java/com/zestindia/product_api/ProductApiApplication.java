package com.zestindia.product_api;

import com.zestindia.product_api.entity.User;
import com.zestindia.product_api.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ProductApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductApiApplication.class, args);
    }

    @Bean
    CommandLineRunner createAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (userRepository
                    .findByUsername("admin")
                    .isEmpty()) {

                User admin = new User();

                admin.setUsername("admin");
                admin.setPassword(
                        passwordEncoder.encode("admin123")
                );
                admin.setRole(User.Role.ADMIN);

                userRepository.save(admin);

                System.out.println(
                        "Default admin created: admin/admin123"
                );
            }
        };
    }
}