package com.acme.test01.levanjeriashvili.services;

import com.acme.test01.levanjeriashvili.exceptions.AccountNotFoundException;
import com.acme.test01.levanjeriashvili.exceptions.WithdrawalAmountTooLargeException;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    void openSavingsAccount(Long accountId, Long amountToDeposit);
    void openCurrentAccount(Long accountId);
    void withdraw(Long accountId, int amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException;
    void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException;

    int getCurrentAccountBalance(Long accountId) throws AccountNotFoundException;

    int getSavingsAccountBalance(Long accountId) throws AccountNotFoundException;
}
