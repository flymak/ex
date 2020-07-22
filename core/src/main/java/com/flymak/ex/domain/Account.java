package com.flymak.ex.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "currency")
    private String currency;

    @Column(name = "account_no", unique = true)
    private String accountNo;

    @Column(name = "balance")
    private BigDecimal balance;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type")
    private AccountType type;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_at")
    private LocalDateTime lastModifiedAt;

    public Account() {}

    public Account(long userId, String currency, AccountType type) {
        this.userId = userId;
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
        this.status = AccountStatus.OK;
        this.type = type;
        this.accountNo = Account.accountNo(this);
    }

    public static String accountNo(long userId, String currency, AccountType type) {
        return String.format("%d_%s_%s", userId, currency, type.name());
    }

    public static String accountNo(Account account) {
        return Account.accountNo(account.userId, account.currency, account.type);
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public AccountType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getLastModifiedAt() {
        return lastModifiedAt;
    }

    public Account setBalance(BigDecimal newBalance) {
        this.balance = newBalance;
        return this;
    }
}
