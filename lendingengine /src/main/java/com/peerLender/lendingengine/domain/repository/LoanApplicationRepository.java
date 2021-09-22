package com.peerLender.lendingengine.domain.repository;

import com.peerLender.lendingengine.domain.model.LoanApplication;
import com.peerLender.lendingengine.domain.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Stack;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findAllByStatusEquals(Status status);

}
