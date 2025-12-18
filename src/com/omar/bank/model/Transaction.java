package com.omar.bank.model;

import com.omar.bank.util.IdGenerator;

import java.time.Instant;
import java.time.LocalDateTime;

public class Transaction {
    private final String transactionId;
    private final TransactionType type;
    private final double amount;
    private final double fee;
    private final double total;
    private final Instant timestamp;
    private final double balanceAfter;



    public  Transaction( TransactionType type, double amount, double fee, double balanceAfter) {
        this.transactionId = IdGenerator.generateTransactionId();
        this.type = type;
        this.amount = amount;
        this.fee= fee;
        this.total = amount + fee;
        this.balanceAfter = balanceAfter;
        this.timestamp = Instant.now();
    }
    public String getTransactionId() {
        return transactionId;
    }
    public String getType() {
        return type.toString();
    }
    public double getAmount() {
        return amount;
    }
    public double getFee() {
        return fee;
    }
    public double getTotal() {
        return total;
    }
    public Instant getTimestamp() {
        return timestamp;
    }
    public double getBalanceAfter() {
        return balanceAfter;
    }
}
