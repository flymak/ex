package com.flymak.ex.service;

import com.flymak.ex.domain.Account;
import com.flymak.ex.domain.AccountType;
import com.flymak.ex.exception.AccountNoSufficientBalance;
import com.flymak.ex.exception.AccountNotFound;
import com.flymak.ex.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
public class AccountService {
    private AccountRepository repository;

    @Value("{ex.systemUser}")
    private long systemUserId;


    @Autowired
    public AccountService(AccountRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void deposit(long userId, String currency, BigDecimal amount) {
        String systemAccountNo = Account.accountNo(systemUserId, currency, AccountType.AVAILABLE);
        String toAccountNo = Account.accountNo(userId, currency, AccountType.AVAILABLE);
        transfer(systemAccountNo, toAccountNo, amount, false);
    }

    @Transactional
    public void withdraw(long userId, String currency, BigDecimal amount) {
        String fromAccountNo = Account.accountNo(userId, currency, AccountType.AVAILABLE);
        String toAccountNo = Account.accountNo(userId, currency, AccountType.FROZEN);
        transfer(fromAccountNo, toAccountNo, amount, true);
    }

    @Transactional
    public void withdrawComplete(long userId, String currency, BigDecimal amount) {
        String fromAccountNo = Account.accountNo(userId, currency, AccountType.FROZEN);
        String toAccountNo = Account.accountNo(systemUserId, currency, AccountType.AVAILABLE);
        transfer(fromAccountNo, toAccountNo, amount, true);
    }

    @Transactional
    public void order(long userId, String currency, BigDecimal amount) {
        String fromAccountNo = Account.accountNo(userId, currency, AccountType.AVAILABLE);
        String toAccountNo = Account.accountNo(userId, currency, AccountType.FROZEN);
        transfer(fromAccountNo, toAccountNo, amount, true);
    }

    @Transactional
    public void transfer(String fromAccountNo, String toAccountNo, BigDecimal amount, boolean checkBalance) {
        Account from = repository.findByAccountNo(fromAccountNo).orElseThrow(() -> new AccountNotFound(fromAccountNo));
        Account to = repository.findByAccountNo(toAccountNo).orElseThrow(() -> new AccountNotFound(toAccountNo));

        if (checkBalance && from.getBalance().compareTo(amount) < 0) {
            throw new AccountNoSufficientBalance(fromAccountNo, amount);
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        repository.save(from);
        repository.save(to);
    }
}
