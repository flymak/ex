package com.flymak.ex.exception;

public class AccountNotFound extends BaseException {
    public AccountNotFound(String accountNo) {
        super(-20001, accountNo + " not found");
    }
}
