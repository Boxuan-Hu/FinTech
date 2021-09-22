package com.peerLender.lendingengine.application;


import com.peerLender.lendingengine.domain.model.Money;
import com.peerLender.lendingengine.domain.service.BalanceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/balance")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/topup")
    public void topUp(final @RequestBody Money money, @RequestHeader String authorization) {
        balanceService.topUpBalance(money, authorization);
    }

    @PostMapping("/withdraw")
    public void withdraw(final @RequestBody Money money, @RequestHeader String authorization) {
        balanceService.withDrawFromBalance(money, authorization);
    }



}
