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
<<<<<<< HEAD
        return false;
	}
	
	public boolean isRejected() {
        return false;
	}
	
	public boolean isPending() {
        return false;
=======
        return this.transferStatus.equals("Approved");
	}
	
	public boolean isRejected() {
		return this.transferStatus.equals("Rejected");
	}
	
	public boolean isPending() {
		return this.transferStatus.equals("Pending");
>>>>>>> dc1dbd053ae57cdd35bb6c58833e6e2f25a4e26b
	}
	
	public boolean isRequestType() {
		return this.transferType.equals("Request");
	}
	
	public boolean isSendType() {
		return this.transferType.equals("Send");
	}
}
