package com.Daredevil.studentmanagment.config;

import com.Daredevil.studentmanagment.model.Users;
import com.Daredevil.studentmanagment.repository.userRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataIntializer {


    @Bean
    CommandLineRunner loadSampleData(userRepository userRepo,
                                        PasswordEncoder passwordEncoder){

        return args -> {
            if (!userRepo.existsByUsername("Admin")){
                Users users = new Users();
                users.setUsername("Admin");

                users.setPassword(passwordEncoder.encode("admin@123"));
                users.setActive(true);
                userRepo.save(users);
            }
        };
    }
}
