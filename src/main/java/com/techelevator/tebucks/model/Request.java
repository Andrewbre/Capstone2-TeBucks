package com.techelevator.tebucks.model;

import java.math.BigDecimal;

public class Request {
    private int custId;
    private int transactionId;
    private BigDecimal amount;
    private boolean isSolved = false;
    private int recipientId;

    public Request (int custId, int transactionId,BigDecimal amount, int recipientId) {
        this.custId = custId;
        this.transactionId =transactionId;
        this.amount=amount;
        this.recipientId=recipientId;

    }
    public Request () {

    }
    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }
}
