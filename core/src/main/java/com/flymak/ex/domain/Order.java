package com.flymak.ex.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    long id;

    @Column(name = "user_id")
    long userId;

    @Column(name = "symbol")
    String symbol;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "command")
    OrderCommand command;

    @Column(name = "quantity")
    BigDecimal quantity;

    @Column(name = "price")
    BigDecimal price;

    @Column(name = "ref_id")
    long refId;

    @Enumerated(value = EnumType.STRING)
    OrderType type;
}
