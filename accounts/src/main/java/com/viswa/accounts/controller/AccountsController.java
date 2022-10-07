package com.viswa.accounts.controller;

import com.viswa.accounts.config.AccountsServiceConfig;
import com.viswa.accounts.model.Accounts;
import com.viswa.accounts.model.Customer;
import com.viswa.accounts.model.Properties;
import com.viswa.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountsServiceConfig accountsConfig;

    @PostMapping("/myAccount")
    public Accounts getAccountsByCustomerId(@RequestBody Customer customer) {
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
}
