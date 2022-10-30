package com.viswa.accounts;

import com.viswa.accounts.model.Accounts;
import com.viswa.accounts.model.Customer;
import com.viswa.accounts.repository.AccountRepository;
import com.viswa.accounts.repository.CustomerRepository;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
@RefreshScope
@EnableFeignClients
public class MicroserviceForAccountsApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceForAccountsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Customer customer = new Customer();
		customer.setCustomerId(124);
		customer.setCustomerName("Teja");
		customer.setEmail("sati@gmail.com");
		customer.setCreatedAt(LocalDate.parse("2021-03-22"));
		customer.setMobileNumber("949085792");

		Accounts accounts = new Accounts();
		accounts.setAccountNumber(Long.parseLong("1234678"));
		accounts.setCustomerId(124);
		accounts.setCreateDt(LocalDate.parse("2021-03-22"));
		accounts.setAccountType("Savings");

		customerRepository.save(customer);
		accountRepository.save(accounts);
	}

	@Bean
	public TimedAspect timedAspect(MeterRegistry meterRegistry) {
		return new TimedAspect(meterRegistry);
	}
}
