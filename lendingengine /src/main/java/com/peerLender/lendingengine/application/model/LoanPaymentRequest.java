package com.peerLender.lendingengine.application.model;


import com.peerLender.lendingengine.domain.model.Currency;
import com.peerLender.lendingengine.domain.model.Money;

import java.util.Objects;

public class LoanPaymentRequest {

    private final double amount;
    private final long longId;


    public LoanPaymentRequest(double amount, long longId) {
        this.amount = amount;
        this.longId = longId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoanPaymentRequest that = (LoanPaymentRequest) o;
        return Double.compare(that.amount, amount) == 0 && longId == that.longId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, longId);
    }

    public Money getAmount() {
        return new Money(amount, Currency.USD);
    }

    public long getLongId() {
        return longId;
    }

    @Override
    public String toString() {
        return "LoanPaymentRequest{" +
                "amount=" + amount +
                ", longId=" + longId +
                '}';
    }
}
