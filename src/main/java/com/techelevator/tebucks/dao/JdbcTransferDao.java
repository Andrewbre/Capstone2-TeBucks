package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.TransferStatusUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    @Override
    public List<Transfer> getAllTransfersByUserId(int userId) {
        return null;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        return null;
    }

    @Override
    public Transfer createNewTransfer(@RequestBody Transfer newTransfer) {
        return null;
    }

    @Override
    public Transfer updateTransfer(TransferStatusUpdateDto transferStatusUpdateDto) {
        return null;
    }

    @Override
    public Transfer updateTransfer(int id, TransferStatusUpdateDto transferStatusUpdateDto) {
        return null;
    }

}

