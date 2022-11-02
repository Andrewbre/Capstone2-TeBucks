package com.techelevator.tebucks.model;

import java.math.BigDecimal;

public class Transfer {
    private int transferId;
    private String transferType;
    private String transferStatus;
    private User userFrom;
    private User userTo;
    private BigDecimal amount;

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
        return this.transferStatus.equals("Approved");
	}
	
	public boolean isRejected() {
		return this.transferStatus.equals("Rejected");
	}
	
	public boolean isPending() {
		return this.transferStatus.equals("Pending");
	}
	
	public boolean isRequestType() {
		return this.transferType.equals("Request");
	}
	
	public boolean isSendType() {
		return this.transferType.equals("Send");
	}
}
