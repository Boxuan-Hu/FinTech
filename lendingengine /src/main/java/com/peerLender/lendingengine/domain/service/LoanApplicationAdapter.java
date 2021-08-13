package com.peerLender.lendingengine.domain.service;

import com.peerLender.lendingengine.application.model.LoanRequest;
import com.peerLender.lendingengine.domain.exception.UserNotFoundException;
import com.peerLender.lendingengine.domain.model.LoanApplication;
import com.peerLender.lendingengine.domain.model.User;
import com.peerLender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanApplicationAdapter {

    private final UserRepository userRepository;

    @Autowired
    public LoanApplicationAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoanApplication transform(LoanRequest req, User borrower) {
        Optional<User> userOptional = userRepository.findById(borrower.getUsername());

        if (userOptional.isPresent()) {
            return new LoanApplication(req.getAmount(), userOptional.get(),
                    req.getDaysToRepay(), req.getInterestRate());
        } else {
            throw new UserNotFoundException(borrower.getUsername());
        }
    }

}
