package com.bank.payment;

import com.bank.payment.concurrency.BalanceFactory;
import com.bank.payment.concurrency.BalanceOptions;
import com.bank.payment.config.RsaKeyConfigProperties;

import com.bank.payment.domain.BalanceTypes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfigProperties.class)
public class PaymentApplication {
	private static final Logger logger= LoggerFactory.getLogger(PaymentApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
		logger.info("Dark Money Server on the fire.....");
		BalanceOptions balanceOptions= new BalanceOptions();
		BalanceFactory balanceFactory=balanceOptions.createBalance("localCurrency",0.17, BalanceTypes.FOREIGN_CURRENCY);
		System.out.println(balanceFactory.getAvailableToUseAfterTax(BigDecimal.valueOf(5000)));
		System.out.println(balanceFactory.getAvailableToUseAfterTax(BigDecimal.valueOf(5000)));

	}

//	@Bean
//	public CommandLineRunner initializeUser(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
//		System.out.println("XXX");
//		return args -> {
//
//			User user = new User();
//			user.setUserName("exampleuser");
//			user.setEmail("example@gmail.com");
//			user.setPassword(passwordEncoder.encode("examplepassword"));
//			// Save the user to the database
//			userRepository.save(user);
//
//		};
//	}

}
