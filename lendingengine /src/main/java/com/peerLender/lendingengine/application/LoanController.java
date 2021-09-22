package com.peerLender.lendingengine.application;

import com.peerLender.lendingengine.application.model.LoanPaymentRequest;
import com.peerLender.lendingengine.application.model.LoanRequest;
import com.peerLender.lendingengine.application.service.TokenValidateService;
import com.peerLender.lendingengine.domain.exception.UserNotFoundException;
import com.peerLender.lendingengine.domain.model.Loan;
import com.peerLender.lendingengine.domain.model.LoanApplication;
import com.peerLender.lendingengine.domain.model.Status;
import com.peerLender.lendingengine.domain.model.User;
import com.peerLender.lendingengine.domain.repository.LoanApplicationRepository;
import com.peerLender.lendingengine.domain.repository.UserRepository;
import com.peerLender.lendingengine.domain.service.LoanApplicationAdapter;
import com.peerLender.lendingengine.domain.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class LoanController {

    private final LoanApplicationRepository loanApplicationRepository;
    private final UserRepository userRepository;
    private final LoanApplicationAdapter loanApplicationAdapter;
    private final LoanService loanService;
    private final TokenValidateService tokenValidateService;

    @Autowired
    public LoanController(LoanApplicationRepository loanApplicationRepository,
                          UserRepository userRepository,
                          LoanApplicationAdapter loanApplicationAdapter,
                          LoanService loanService,
                          TokenValidateService tokenValidateService) {
        this.loanApplicationRepository = loanApplicationRepository;
        this.userRepository = userRepository;
        this.loanApplicationAdapter = loanApplicationAdapter;
        this.loanService = loanService;
        this.tokenValidateService = tokenValidateService;
    }

    @PostMapping(value = "/loan/request")
    public void requestLoan(@RequestBody final LoanRequest loanRequest,
                            HttpServletRequest request) {
        User borrower = tokenValidateService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        loanApplicationRepository.save(loanApplicationAdapter.transform(loanRequest, borrower)) ;
    }

    @PostMapping(value = "/loan/accept/{loanApplicationId}")
    public void acceptLoan(@PathVariable String loanApplicationId,
                           HttpServletRequest request) {
        User lender =
                tokenValidateService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));

        loanService.acceptLoan(Long.parseLong(loanApplicationId), lender.getUsername());
    }

    @PostMapping(value = "/loan/repay")
    public void repayLoan(@RequestBody LoanPaymentRequest request,
                          @RequestHeader String authorization) {
        User borrower = tokenValidateService.validateTokenAndGetUser(authorization);
        loanService.repayLoan(request.getAmount(), request.getLongId(), borrower);
    }

    @GetMapping(value = "/loan/requests")
    public List<LoanApplication> findLoanApplications(HttpServletRequest request) {
        tokenValidateService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return loanApplicationRepository.findAllByStatusEquals(Status.ONGOING);
    }

    @GetMapping(value = "/loans")
    public List<Loan> findLoans(HttpServletRequest request) {
        tokenValidateService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return loanService.getLoans();
    }

    @GetMapping(value = "/loan/{status}/borrowed")
    public List<Loan> findBorrowedLoans(@RequestHeader String authorization,
                                        @PathVariable Status status) {
        User borrower = tokenValidateService.validateTokenAndGetUser(authorization);
        return loanService.findAllBorrowedLoans(borrower, status);
    }

    @GetMapping(value = "/loan/{status}/lent")
    public List<Loan> findLentLoans(@RequestHeader String authorization,
                                    @PathVariable Status status) {
        User lender = tokenValidateService.validateTokenAndGetUser(authorization);
        return loanService.findAllLentLoans(lender, status);
    }



}
