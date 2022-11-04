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
    private LocalDate loggedTime;

    //edited transaction class as many fields in transfer covered our intended transaction fields


    public Transfer() {}
    public Transfer(int transferId, String transferType, String transferStatus, User userFrom, User userTo, BigDecimal amount, LocalDate loggedTime) {
        this.transferId = transferId;
        this.transferType = transferType;
        this.transferStatus = transferStatus;
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.amount = BigDecimal.valueOf(1000.00);
        this.loggedTime = loggedTime;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
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

    public void setUserFrom(User userFrom) {
        this.userFrom = userFrom;
    }

    public void setUserTo(User userTo) {
        this.userTo = userTo;
    }

    public LocalDate getLoggedTime() {
        return loggedTime;
    }

    public void setLoggedTime(LocalDate loggedTime) {
        this.loggedTime = loggedTime;
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
        return this.transferStatus.equalsIgnoreCase(TRANSFER_STATUS_APPROVED);
	}
	
	public boolean isRejected() {
		return this.transferStatus.equalsIgnoreCase(TRANSFER_STATUS_REJECTED);
	}
	
	public boolean isPending() {
		return this.transferStatus.equalsIgnoreCase(TRANSFER_STATUS_PENDING);
	}
	
	public boolean isRequestType() {
		return this.transferType.equalsIgnoreCase(TRANSFER_TYPE_REQUEST);
	}
	
	public boolean isSendType() {
		return this.transferType.equalsIgnoreCase(TRANSFER_TYPE_SEND);
	}
}
