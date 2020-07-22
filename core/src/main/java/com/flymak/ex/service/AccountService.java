package com.flymak.ex.service;

import com.flymak.ex.domain.Account;
import com.flymak.ex.domain.AccountFlow;
import com.flymak.ex.exception.AccountNoSufficientBalance;
import com.flymak.ex.exception.AccountNotFound;
import com.flymak.ex.repository.AccountFlowRepository;
import com.flymak.ex.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AccountService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private AccountRepository repository;

    private AccountFlowRepository flowRepository;

    @Value("{ex.systemUser}")
    private long systemUserId;

    @Autowired
    public AccountService(AccountRepository repository, AccountFlowRepository flowRepository) {
        this.repository = repository;
        this.flowRepository = flowRepository;
    }

    @Transactional
    void transfer(AccountFlow flow, boolean checkBalance) {
        logger.info("start transfer checkBalance {}, {}...", checkBalance, flow);
        Account from = repository.findByAccountNo(flow.getFromAccountNo()).orElseThrow(() -> new AccountNotFound(flow.getFromAccountNo()));
        Account to = repository.findByAccountNo(flow.getToAccountNo()).orElseThrow(() -> new AccountNotFound(flow.getToAccountNo()));

        if (checkBalance && from.getBalance().compareTo(flow.getAmount()) < 0) {
            throw new AccountNoSufficientBalance(flow.getFromAccountNo(), flow.getAmount());
        }

        from.setBalance(from.getBalance().subtract(flow.getAmount()));
        to.setBalance(to.getBalance().add(flow.getAmount()));
        repository.save(from);
        repository.save(to);
        flowRepository.save(flow);
        logger.info("finish transfer checkBalance {}, {}", checkBalance, flow);
    }
}
