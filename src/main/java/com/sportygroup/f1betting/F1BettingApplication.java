package com.sportygroup.f1betting;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.sportygroup.f1betting.model.entity.User;
import com.sportygroup.f1betting.repo.UserRepository;

@SpringBootApplication
public class F1BettingApplication {

	public static void main(String[] args) {
		SpringApplication.run(F1BettingApplication.class, args);
	}

	@Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    CommandLineRunner init(UserRepository userRepository) {
        return args -> {
        	if (userRepository.count() == 0) {
                User user = new User();
                user.setName("Test User");
                user.setBalance(100.0);
                User savedUser = userRepository.save(user);
                System.out.println("Test user created with ID: " + savedUser.getUserId());
            }
        };
    }

}
