package com.viswa.loans;

import com.viswa.loans.model.Loans;
import com.viswa.loans.repository.LoansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import java.sql.Date;
import java.time.LocalDate;

@SpringBootApplication
@RefreshScope
public class LoansApplication implements CommandLineRunner {

	@Autowired
	private LoansRepository loansRepository;

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Loans loans = new Loans();
		loans.setAmountPaid(100);
		loans.setLoanNumber(123);
		loans.setCreateDt(String.valueOf(LocalDate.parse("2022-01-22")));
		loans.setCustomerId(1);
		loans.setTotalLoan(123);
		loans.setOutstandingAmount(12);
		loans.setStartDt(Date.valueOf(String.valueOf(LocalDate.parse("2022-01-22"))));

		loansRepository.save(loans);

		loans.setAmountPaid(123567);
		loans.setCustomerId(1);
		loansRepository.save(loans);

		loans.setAmountPaid(345678);
		loans.setCustomerId(1);
		loansRepository.save(loans);
	}
}
