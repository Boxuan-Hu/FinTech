package com.peerLender.lendingengine.domain.repository;

import com.peerLender.lendingengine.domain.model.Loan;
import com.peerLender.lendingengine.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {



}
