package com.bank.payment;

import com.bank.payment.config.RsaKeyConfigProperties;

import com.bank.payment.domain.User;
import com.bank.payment.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}
//	@Bean
	public CommandLineRunner initializeUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
		System.out.println("XXX");
		return args -> {

			User user = new User();
			user.setUserName("exampleuser");
			user.setEmail("example@gmail.com");
			user.setPassword(passwordEncoder.encode("examplepassword"));

			// Save the user to the database
			userRepository.save(user);

		};
	}

}
