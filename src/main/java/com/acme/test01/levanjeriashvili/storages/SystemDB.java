package com.acme.test01.levanjeriashvili.storages;

import com.acme.test01.levanjeriashvili.exceptions.AccountNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class SystemDB {

    private static SystemDB instance;
    private Map<Long, SavingsAccount> savingsAccounts;
    private Map<Long, CurrentAccount> currentAccounts;

    private SystemDB() {
        savingsAccounts = new HashMap<>();
        currentAccounts = new HashMap<>();

        // Prepopulate the database with a few accounts
        openSavingsAccount(1L, 2000);
        openSavingsAccount(2L, 5000);
        openCurrentAccount(3L, 1000, 10000);
        openCurrentAccount(4L, -5000, 20000);
    }

    public static SystemDB getInstance() {
        if (instance == null) {
            instance = new SystemDB();
        }
        return instance;
    }

    public void openSavingsAccount(Long accountId, int initialBalance) {
        SavingsAccount account = new SavingsAccount(accountId, initialBalance);
        savingsAccounts.put(accountId, account);
    }

    public void openCurrentAccount(Long accountId, int initialBalance, int overdraftLimit) {
        CurrentAccount account = new CurrentAccount(accountId, initialBalance, overdraftLimit);
        currentAccounts.put(accountId, account);
    }

    public SavingsAccount getSavingsAccount(Long accountId) {
        return savingsAccounts.get(accountId);
    }

    public CurrentAccount getCurrentAccount(Long accountId) {
        return currentAccounts.get(accountId);
    }

}
