package com.viswa.accounts.repository;

import com.viswa.accounts.model.Accounts;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Accounts, Long> {
    Accounts findByCustomerId(int customerId);
}
