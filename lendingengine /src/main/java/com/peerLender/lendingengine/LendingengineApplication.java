package com.peerLender.lendingengine;

import com.peerLender.lendingengine.domain.model.User;
import com.peerLender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LendingengineApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(LendingengineApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

//	@Override
//	public void run(String... args) throws Exception {
//		userRepository.save(new User("Hu", "Hu", "B", 27, "Engineer"));
//		userRepository.save(new User("BE", "BE", "C", 21, "Doctor"));
//		userRepository.save(new User("DF", "DF", "E", 19, "Unemployed"));
//	}
}
