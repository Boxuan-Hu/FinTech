package com.peerLender.lendingengine.application;

import com.peerLender.lendingengine.application.model.LoanRequest;
import com.peerLender.lendingengine.application.service.TokenValidateService;
import com.peerLender.lendingengine.domain.exception.UserNotFoundException;
import com.peerLender.lendingengine.domain.model.Loan;
import com.peerLender.lendingengine.domain.model.LoanApplication;
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
    public void acceptLoan(final String loanApplicationId,
                           final String lenderUsername,
                           HttpServletRequest request) {
        User lender =
                userRepository.findById(lenderUsername).orElseThrow(() -> new UserNotFoundException(lenderUsername));
        tokenValidateService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        loanService.acceptLoan(Long.parseLong(loanApplicationId), lender.getUsername());
    }

    @GetMapping(value = "/users")
    public List<User> findUsers(HttpServletRequest request) {
        tokenValidateService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return userRepository.findAll();
    }

    @GetMapping(value = "/loan/requests")
    public List<LoanApplication> findLoanApplications(HttpServletRequest request) {
        tokenValidateService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return loanApplicationRepository.findAll();
    }

    @GetMapping(value = "/loans")
    public List<Loan> findLoans(HttpServletRequest request) {
        tokenValidateService.validateTokenAndGetUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        return loanService.getLoans();
    }
}
