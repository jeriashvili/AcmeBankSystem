package com.acme.test01.levanjeriashvili.services.impl;

import com.acme.test01.levanjeriashvili.exceptions.AccountNotFoundException;
import com.acme.test01.levanjeriashvili.exceptions.WithdrawalAmountTooLargeException;
import com.acme.test01.levanjeriashvili.services.AccountService;
import com.acme.test01.levanjeriashvili.storages.CurrentAccount;
import com.acme.test01.levanjeriashvili.storages.SavingsAccount;
import com.acme.test01.levanjeriashvili.storages.SystemDB;

class BankingSystem implements AccountService {
    @Override
    public void openSavingsAccount(Long accountId, Long amountToDeposit) {
        SystemDB.getInstance().openSavingsAccount(accountId, amountToDeposit.intValue());
    }

    @Override
    public void openCurrentAccount(Long accountId) {
        SystemDB.getInstance().openCurrentAccount(accountId, 0, 100000);
    }

    @Override
    public void withdraw(Long accountId, int amountToWithdraw) throws AccountNotFoundException, WithdrawalAmountTooLargeException {
        SavingsAccount savingsAccount = SystemDB.getInstance().getSavingsAccount(accountId);
        CurrentAccount currentAccount = SystemDB.getInstance().getCurrentAccount(accountId);

        // Check if it's a savings or current account
        if (savingsAccount != null) {
            savingsAccount.withdraw(amountToWithdraw);
        } else if (currentAccount != null) {
            currentAccount.withdraw(amountToWithdraw);
        } else {
            throw new AccountNotFoundException("Account not found");
        }
    }

    @Override
    public void deposit(Long accountId, int amountToDeposit) throws AccountNotFoundException {
        SavingsAccount savingsAccount = SystemDB.getInstance().getSavingsAccount(accountId);
        CurrentAccount currentAccount = SystemDB.getInstance().getCurrentAccount(accountId);

        // Check if it's a savings or current account
        if (savingsAccount != null) {
            savingsAccount.deposit(amountToDeposit);
        } else if (currentAccount != null) {
            currentAccount.deposit(amountToDeposit);
        } else {
            throw new AccountNotFoundException("Account not found");
        }
    }

    @Override
    public int getCurrentAccountBalance(Long accountId) throws AccountNotFoundException {
        CurrentAccount currentAccount = SystemDB.getInstance().getCurrentAccount(accountId);
        if (currentAccount != null) {
            return currentAccount.getBalance();
        } else {
            throw new AccountNotFoundException("Current Account not found");
        }
    }

    @Override
    public int getSavingsAccountBalance(Long accountId) throws AccountNotFoundException {
        SavingsAccount savingsAccount = SystemDB.getInstance().getSavingsAccount(accountId);
        if (savingsAccount != null) {
            return savingsAccount.getBalance();
        } else {
            throw new AccountNotFoundException("Savings Account not found");
        }
    }

    public static void main(String[] args) {
        AccountService accountService = new BankingSystem();

        try {
            // Open Savings and Current Accounts with specific IDs
            accountService.openSavingsAccount(1L, 2000L);
            accountService.openSavingsAccount(2L, 5000L);
            accountService.openCurrentAccount(3L);
            accountService.openCurrentAccount(4L);

            // Deposit funds into Savings Account (ID 1L)
            accountService.deposit(1L, 500);
            System.out.println("Savings Account (1L) Balance after deposit: " + accountService.getSavingsAccountBalance(1L));

            // Withdraw from Savings Account (ID 1L)
            accountService.withdraw(1L, 1000);
            System.out.println("Savings Account (1L) Balance after withdrawal: " + accountService.getSavingsAccountBalance(1L));

            // Deposit funds into Current Account (ID 3L)
            accountService.deposit(3L, 2000);
            System.out.println("Current Account (3L) Balance after deposit: " + accountService.getCurrentAccountBalance(3L));

            // Withdraw from Current Account (ID 3L) with positive balance
            accountService.withdraw(3L, 1000);
            System.out.println("Current Account (3L) Balance after withdrawal (positive balance): " + accountService.getCurrentAccountBalance(3L));

            // Withdraw from Current Account (ID 4L) with negative balance within overdraft limit
            accountService.withdraw(4L, 5000);
            System.out.println("Current Account (4L) Balance after withdrawal (within overdraft limit): " + accountService.getCurrentAccountBalance(4L));

            // Attempt to withdraw from Current Account (ID 4L) exceeding overdraft limit
            accountService.withdraw(4L, 2000000000);

        } catch (AccountNotFoundException e) {
            System.err.println("Account not found: " + e.getMessage());
        } catch (WithdrawalAmountTooLargeException e) {
            System.err.println("Withdrawal amount too large: " + e.getMessage());
        }
    }
}
