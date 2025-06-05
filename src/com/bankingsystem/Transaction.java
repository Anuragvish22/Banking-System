package com.bankingsystem;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    public enum Type { DEPOSIT, WITHDRAW, TRANSFER }

    private final Type type;
    private final LocalDateTime dateTime;
    private final double amount;
    private final int accountId;

    public int getAccountId() { return accountId; }
    public Type getType() { return type; }
    public double getAmount() { return amount; }
    public LocalDateTime getDateTime() { return dateTime; }

    public Transaction(Type type, double amount, int accountId) {
        this.type = type;
        this.amount = amount;
        this.accountId = accountId;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return dateTime + " - " + type + ": " + amount + " (Account: " + accountId + ")";
    }
}
