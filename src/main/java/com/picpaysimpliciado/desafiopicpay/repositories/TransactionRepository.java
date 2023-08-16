package com.picpaysimpliciado.desafiopicpay.repositories;

import com.picpaysimpliciado.desafiopicpay.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
