package com.peerLender.lendingengine.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
public final class LoanApplication {

    @Id
    @GeneratedValue
    private long id;
    private int amount;
    @ManyToOne
    private User borrower;
    private long repaymentTerm;
    private double interestRate;
    private Status status;

    public LoanApplication() {
    }

    public LoanApplication(int amount, User borrower, long repaymentTerm, double interestRate) {
        this.amount = amount;
        this.borrower = borrower;
        this.repaymentTerm = repaymentTerm;
        this.interestRate = interestRate;
        this.status = Status.ONGOING;
    }

    public Loan acceptLoanApplication(final User lender) {
        lender.withdraw(getAmount());
        borrower.topUp(getAmount());
        status = Status.COMPLETED;
        return new Loan(lender, this);
    }

    public Money getAmount() {
        return new Money(amount, Currency.USD);
    }

    public long getRepaymentTerm() {
        return repaymentTerm;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public long getId() {
        return id;
    }

    public User getBorrower() {
        return borrower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass())
            return false;
        LoanApplication that = (LoanApplication) o;
        return amount == that.amount && Double.compare(that.interestRate, interestRate) == 0 && Objects.equals(borrower, that.borrower) && Objects.equals(repaymentTerm, that.repaymentTerm);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, borrower, repaymentTerm, interestRate);
    }

    @Override
    public String toString() {
        return "LoanRequest{" +
                "amount=" + amount +
                ", user=" + borrower +
                ", repaymentTerm=" + repaymentTerm +
                ", interestRate=" + interestRate +
                '}';
    }
}
