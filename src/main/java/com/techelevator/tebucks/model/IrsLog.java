package com.techelevator.tebucks.model;

import java.math.BigDecimal;

public class IrsLog {
    private String description;
    private String account_from;
    private String account_to;
    private double amount;

    public IrsLog (String description,String account_from,String account_to,double amount) {
        this.description = description;
        this.account_from=account_from;
        this.account_to=account_to;
        this.amount=amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAccount_from() {
        return account_from;
    }

    public void setAccount_from(String account_from) {
        this.account_from = account_from;
    }

    public String getAccount_to() {
        return account_to;
    }

    public void setAccount_to(String account_to) {
        this.account_to = account_to;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
