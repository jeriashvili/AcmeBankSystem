package com.acme.test01.levanjeriashvili.storages;

import com.acme.test01.levanjeriashvili.exceptions.WithdrawalAmountTooLargeException;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class SavingsAccount {
    private Long accountId;
    private int balance;

    public void deposit(int amountToDeposit) {
        balance += amountToDeposit;
    }

    public void withdraw(int amountToWithdraw) throws WithdrawalAmountTooLargeException {
        if (balance - amountToWithdraw < 1000) {
            throw new WithdrawalAmountTooLargeException("Withdrawal amount too large for a Savings Account");
        }
        balance -= amountToWithdraw;
    }
}
