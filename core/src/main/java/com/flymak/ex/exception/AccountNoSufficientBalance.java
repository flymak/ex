package com.flymak.ex.exception;

import java.math.BigDecimal;

public class AccountNoSufficientBalance extends BaseException {
    public AccountNoSufficientBalance(String accountNo, BigDecimal amount) {
        super(-20_002, accountNo + " no sufficient balance:" + amount);
    }
}
