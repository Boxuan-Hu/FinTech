package com.peerLender.lendingengine.domain.service;

import com.peerLender.lendingengine.domain.exception.UserNotFoundException;
import com.peerLender.lendingengine.domain.model.Money;
import com.peerLender.lendingengine.domain.model.User;
import com.peerLender.lendingengine.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class BalanceService {

    private final UserRepository userRepository;

    @Autowired
    public BalanceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    // happen fully or not happen
    @Transactional
    public void topUpBalance(final Money money, String authToken) {
        User user = getUser(authToken);
        user.topUp(money);
    }

    @Transactional
    public void withDrawFromBalance(final Money money, String authToken) {
        User user = getUser(authToken);
        user.withdraw(money);
    }

    private User getUser(String authToken) {
        return userRepository.findById(authToken).orElseThrow(() -> new UserNotFoundException(authToken));
    }
}
