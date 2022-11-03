package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.TransferStatusUpdateDto;

import java.util.List;

public interface TransferDao {

    List<Transfer> getAllTransfersByUserId(int userId);

    Transfer getTransferById(int transferId);

    Transfer createNewTransfer(NewTransferDto newTransfer);

    Transfer updateTransfer(int id, TransferStatusUpdateDto transferStatusUpdateDto);
}
