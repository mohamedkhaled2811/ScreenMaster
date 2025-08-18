package com.gr74.ScreenMaster;

import com.gr74.ScreenMaster.model.Role;
import com.gr74.ScreenMaster.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
@EnableJpaAuditing
public class ScreenMasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScreenMasterApplication.class, args);
	}
    @Bean
	public CommandLineRunner runner(RoleRepository roleRepository) {
		return args -> {
			if (roleRepository.findByName("USER").isEmpty()) {
				roleRepository.save(Role.builder().name("USER").build());
			}
		};
	}
}


