package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Transfer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao{


    @Override
    public List<Transfer> getAllTransfersByUserId(int userId) {
        return null;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        return null;
    }

    @Override
    public Transfer createNewTransfer( Transfer newTransfer) {

    }
    public void completeTransfer (Transfer transfer, BigDecimal userBal) {
        if (transfer.getTransferType()=="Send") {
            BigDecimal transferAmount = transfer.getAmount();
            if ( transferAmount.compareTo(userBal) <= 0) {
                String sql = "update transfer set balance = ? where ";
            }

        }
    }

    @Override
    public Transfer updateTransfer() {
        return null;
    }
}
