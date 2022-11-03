package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Request;
import com.techelevator.tebucks.model.Transfer;

import java.util.List;

public interface RequestDao {
    List<Request> listRequestsByRecipientId(int recipientId);
    Request create(Request request);
    void setIsSolved(Request request);


}
