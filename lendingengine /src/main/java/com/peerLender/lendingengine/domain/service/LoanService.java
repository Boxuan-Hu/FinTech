package com.peerLender.lendingengine.domain.service;

import com.peerLender.lendingengine.domain.exception.LoanNotFoundException;
import com.peerLender.lendingengine.domain.exception.UserNotFoundException;
import com.peerLender.lendingengine.domain.model.*;
import com.peerLender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerLender.lendingengine.domain.repository.LoanRepository;
import com.peerLender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void acceptLoan(final long loanApplicationId, final String lenderName) {
        User lender = getUser(lenderName);
        LoanApplication loanApplication = getLoanApplication(loanApplicationId, lenderName);
        loanRepository.save(loanApplication.acceptLoanApplication(lender));
    }

    // mutate application should return void
    @Transactional
    public void repayLoan(final Money amountToRepay,
                          final long loanId,
                          final User borrower) {
        Loan loan =
                loanRepository.findOneByIdAndBorrower(loanId, borrower).orElseThrow(()->new LoanNotFoundException());
        Money actualPaidAmount = amountToRepay.getAmount() > loan.getAmountOwed().getAmount() ?
                loan.getAmountOwed() : amountToRepay;

        loan.repay(actualPaidAmount);
    }


    private LoanApplication getLoanApplication(long loanApplicationId, String lenderName) {
        System.out.println("Application ID");
        System.out.println(loanApplicationId);
        return loanApplicationRepository.findById(loanApplicationId).orElseThrow(() -> new UserNotFoundException(lenderName));
    }

    private User getUser(String lenderName) {
        return userRepository.findById(lenderName).orElseThrow(() -> new UserNotFoundException(lenderName));
    }

    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> findAllBorrowedLoans(final User borrower, final Status status) {
        return loanRepository.findAllByBorrowerAndStatus(borrower, status);
    }

    public List<Loan> findAllLentLoans(final User lender, final Status status) {
        return loanRepository.findAllByLenderAndStatus(lender, status);
    }




}
