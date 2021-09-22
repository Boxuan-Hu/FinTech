package com.peerLender.lendingengine;

import com.peerLender.lendingengine.domain.model.Balance;
import com.peerLender.lendingengine.domain.model.Currency;
import com.peerLender.lendingengine.domain.model.Money;
import com.peerLender.lendingengine.domain.model.User;
import com.peerLender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class LendingengineApplication implements CommandLineRunner{

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(LendingengineApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Override
	public void run(String... args) throws Exception {
		User boxuan = new User("Hu", "Hu", "B", 27, "Engineer", new Balance());
		User be = new User("BE", "BE", "C", 21, "Doctor", new Balance());
		User df = new User("DF", "DF", "E", 19, "Unemployed", new Balance());
		boxuan.topUp(new Money(200, Currency.USD));
		df.topUp(new Money(100, Currency.USD));
		userRepository.save(boxuan);
		userRepository.save(be);
		userRepository.save(df);


	}
}


