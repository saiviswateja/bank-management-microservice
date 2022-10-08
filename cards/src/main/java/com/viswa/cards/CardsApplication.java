package com.viswa.cards;

import com.viswa.cards.model.Cards;
import com.viswa.cards.repoitory.CardsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication
@RefreshScope
public class CardsApplication implements CommandLineRunner {

	@Autowired
	private CardsRepository cardsRepository;

	public static void main(String[] args) {
		SpringApplication.run(CardsApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		Cards cards = new Cards();
		cards.setCardId(Integer.parseInt("4555555"));
		cards.setCardNumber(String.valueOf(1));
		cards.setAvailableAmount(1000);
		cards.setTotalLimit(245454);
		cards.setCustomerId(1);

		cardsRepository.save(cards);

		cards.setCardId(Integer.parseInt("4555555"));
		cards.setCardNumber(String.valueOf(1));
		cards.setAvailableAmount(1000);
		cards.setTotalLimit(790);
		cards.setCustomerId(1);
		cardsRepository.save(cards);

		cards.setCardId(Integer.parseInt("4555555"));
		cards.setCardNumber(String.valueOf(1));
		cards.setAvailableAmount(1000);
		cards.setTotalLimit(900);
		cards.setCustomerId(1);
		cardsRepository.save(cards);
	}
}
