package com.acme.test01.levanjeriashvili.storages;

import com.acme.test01.levanjeriashvili.exceptions.WithdrawalAmountTooLargeException;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CurrentAccount {
    private Long accountId;
    private int balance;
    private int overdraftLimit;

    public void deposit(int amountToDeposit) {
        balance += amountToDeposit;
    }

    public void withdraw(int amountToWithdraw) throws WithdrawalAmountTooLargeException {
        if (amountToWithdraw > (balance + overdraftLimit)) {
            throw new WithdrawalAmountTooLargeException("Withdrawal amount exceeds the balance + overdraft limit");
        }
        balance -= amountToWithdraw;
    }
}
