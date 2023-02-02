package com.tass.payment.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "user_balance")
public class UserBalance {

    @Id
    @Column(name = "user_id")
    private long userId;

    @Column(name = "amount")
    private double balance;
}
