package com.viswa.loans.repository;

import java.util.List;

import com.viswa.loans.model.Loans;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoansRepository extends CrudRepository<Loans, Long> {


    List<Loans> findByCustomerIdOrderByStartDtDesc(int customerId);

}
