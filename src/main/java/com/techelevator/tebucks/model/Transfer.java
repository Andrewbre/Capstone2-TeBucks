package com.techelevator.tebucks.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transfer {
    private int transferId;
    private String transferType;
    private String transferStatus;
    private User userFrom;
    private User userTo;
    private BigDecimal amount;
    private LocalDate loggedtime;

    //edited transaction class as many fields in transfer covered our intended transaction fields


    public Transfer(int transferId, String transferType, String transferStatus, User userFrom, User userTo, BigDecimal amount, LocalDate loggedtime) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = amount;
        this.loggedtime = loggedtime;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getLoggedtime() {
        return loggedtime;
    }

    public void setLoggedtime(LocalDate loggedtime) {
        this.loggedtime = loggedtime;
    }

    public static final String TRANSFER_TYPE_REQUEST = "Request";
    public static final String TRANSFER_TYPE_SEND = "Send";
    public static final String TRANSFER_STATUS_PENDING = "Pending";
    public static final String TRANSFER_STATUS_APPROVED = "Approved";
    public static final String TRANSFER_STATUS_REJECTED = "Rejected";

	public int getTransferId() {
        return transferId;
    }

    public String getTransferType() {
        return transferType;
    }

    public String getTransferStatus() {
        return transferStatus;
    }
    
    public User getUserFrom() {
    	return userFrom;
    }
    
    public User getUserTo() {
    	return userTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

	public boolean isApproved() {

        return false;

	}
	
	public boolean isRejected() {
		return false; // TODO
	}
	
	public boolean isPending() {
		return false; // TODO
	}
	
	public boolean isRequestType() {
		return false; // TODO
	}
	
	public boolean isSendType() {
		return false; // TODO
	}
}
