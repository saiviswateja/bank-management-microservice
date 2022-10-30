package com.viswa.accounts.controller;

import com.viswa.accounts.client.CardsFeignClient;
import com.viswa.accounts.client.LoansFeignClient;
import com.viswa.accounts.config.AccountsServiceConfig;
import com.viswa.accounts.model.*;
import com.viswa.accounts.repository.AccountRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.micrometer.core.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AccountsController {

    private static final Logger logger  = LoggerFactory.getLogger(AccountsController.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountsServiceConfig accountsConfig;

    @Autowired
    private LoansFeignClient loansFeignClient;

    @Autowired
    private CardsFeignClient cardsFeignClient;

    @PostMapping("/myAccount")
    @Timed(value = "getAccountDetails.time", description = "time taken to return account details")
    public Accounts getAccountDetails(@RequestBody Customer customer) {
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId());
        if(accounts!=null) {
            return accounts;
        } else {
            return null;
        }
    }

    @GetMapping("/account/properties")
    public String getPropertyDetails() throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        Properties properties = new Properties(accountsConfig.getMsg(), accountsConfig.getBuildVersion(),
                accountsConfig.getMailDetails(), accountsConfig.getActiveBranches());
        String jsonStr = ow.writeValueAsString(properties);
        return jsonStr;
    }

    /**
     * @CircuitBreaker
     * CircuitBreaker will not retry but directly use tje fall back method
     *
     * @Retry
     * retry will first retry for the count given in application properties and then it will make use of fall back method
     *
     *
     * @param customer
     * @return
     */
    @PostMapping("/myCustomerDetails")
    @CircuitBreaker(name = "detailsForCustomerSupportApp", fallbackMethod = "myCustmerDetailsFallback")
//    @Retry(name = "detailsForCustomerSupportApp", fallbackMethod = "myCustmerDetailsFallback")
    public CustomerDetails myCustomerDetails(@RequestBody Customer customer) {
        logger.info("myCustomer details method started");
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loansList = loansFeignClient.getLoanDetails(customer);
        List<Cards> cardsList = cardsFeignClient.getCardDetails(customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
        customerDetails.setCards(cardsList);
        customerDetails.setLoans(loansList);
        logger.info("myCustomer details method ended");
        return customerDetails;
    }

    @GetMapping("/sayHello")
    @RateLimiter(name = "sayHello", fallbackMethod = "sayHelloFallback")
    public String sayHello() {
        return "Hello, Welcome to Eazy bank";
    }

    public String sayHelloFallback(Throwable t) {
        return "Hello, welcome to eazy bank from fall back method";
    }

    private CustomerDetails myCustmerDetailsFallback(Customer customer, Throwable t) {
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId());
        List<Loans> loansList = loansFeignClient.getLoanDetails(customer);
//        List<Cards> cardsList = cardsFeignClient.getCardDetails(customer);

        CustomerDetails customerDetails = new CustomerDetails();
        customerDetails.setAccounts(accounts);
//        customerDetails.setCards(cardsList);
        customerDetails.setLoans(loansList);

        return customerDetails;
    }
}
