package com.semestrovaya.demo.repo;

import com.semestrovaya.demo.models.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {
}
