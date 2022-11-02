package com.techelevator.tebucks.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private int transactionId;
    private int user_id;
    private LocalDate loggedTime;
    private BigDecimal amount;
    private boolean IRSEligible;
    private String transactionType;
    private boolean isComplete;

    public Transaction(int transactionId, int user_id, LocalDate loggedTime, BigDecimal amount, String transactionType, boolean isComplete) {
        this.transactionId = transactionId;
        this.user_id = user_id;
        this.loggedTime = loggedTime;
        this.amount = amount;
        this.transactionType = transactionType;
        this.isComplete = isComplete;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDate getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(LocalDate loggedTime) {
        this.loggedTime = loggedTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isIRSEligible() {
        return IRSEligible;
    }

    public void setIRSEligible(boolean IRSEligible) {
        this.IRSEligible = IRSEligible;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }
}
