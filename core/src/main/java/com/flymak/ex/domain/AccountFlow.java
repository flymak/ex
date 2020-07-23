package com.flymak.ex.domain;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_flows")
public class AccountFlow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long Id;

    @Column(name = "from_account_no")
    String fromAccountNo;

    @Column(name = "to_account_no")
    String toAccountNo;

    @Column(name = "amount")
    BigDecimal amount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "flow_type")
    AccountFlowType type;

    @Column(name = "business_id")
    long businessId;

    @Column(name = "operator_id")
    long operatorId;

    @Column(name = "meta")
    String meta;

    @CreatedDate
    @Column(name = "created_at")
    LocalDateTime createdAt;

    public AccountFlow() {
    }

    public AccountFlow(String fromAccountNo, String toAccountNo, BigDecimal amount, AccountFlowType accountFlowType, long businessId, long operatorId, String meta) {
        this.fromAccountNo = fromAccountNo;
        this.toAccountNo = toAccountNo;
        this.amount = amount;
        this.type = accountFlowType;
        this.businessId = businessId;
        this.operatorId = operatorId;
        this.meta = meta;
    }

    public long getId() {
        return Id;
    }

    public String getFromAccountNo() {
        return fromAccountNo;
    }

    public String getToAccountNo() {
        return toAccountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public AccountFlowType getType() {
        return type;
    }

    public long getBusinessId() {
        return businessId;
    }

    public long getOperatorId() {
        return operatorId;
    }

    public String getMeta() {
        return meta;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
