package com.viswa.accounts.controller;

import com.viswa.accounts.model.Accounts;
import com.viswa.accounts.model.Customer;
import com.viswa.accounts.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/myAccount")
    public Accounts getAccountsByCustomerId(@RequestBody Customer customer) {
        Accounts accounts = accountRepository.findByCustomerId(customer.getCustomerId());
        if(accounts!=null) {
            return accounts;
        } else {
            return null;
        }
    }


}
