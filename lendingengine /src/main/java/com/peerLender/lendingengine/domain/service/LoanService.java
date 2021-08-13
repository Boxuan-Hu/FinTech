package com.peerLender.lendingengine.domain.service;

import com.peerLender.lendingengine.domain.exception.UserNotFoundException;
import com.peerLender.lendingengine.domain.model.Loan;
import com.peerLender.lendingengine.domain.model.LoanApplication;
import com.peerLender.lendingengine.domain.model.User;
import com.peerLender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerLender.lendingengine.domain.repository.LoanRepository;
import com.peerLender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoanService {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanApplicationRepository loanApplicationRepository, UserRepository userRepository, LoanRepository loanRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
    }

    public void acceptLoan(final long loanApplicationId, final String lenderName) {
        User lender = userRepository.findById(lenderName).orElseThrow(() -> new UserNotFoundException(lenderName));
        LoanApplication loanApplication = loanApplicationRepository.findById(loanApplicationId).orElseThrow(() -> new UserNotFoundException(lenderName));
        loanRepository.save(new Loan(lender, loanApplication));
    }

    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

}
