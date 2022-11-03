package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public List<Transfer> getAllTransfersByUserId(int userId) {
        return null;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        String sql = "SELECT transfer_id, user_id, recipient_id, amount, transfer_type, is_completed, logged_time FROM transfers WHERE transfer_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()) {
            return mapRowToTransfer(result);
        }
        return null;
    }

    @Override
    public Transfer createNewTransfer(Transfer newTransfer) {

        String sql = "INSERT INTO transfers (user_id, logged_time, recipient_id, amount, " +
                "transfer_type, is_completed VALUES (?, ?, ?, ?, ?, ?);";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getUserFrom(),
                newTransfer.getLoggedTime(), newTransfer.getUserTo(), newTransfer.getAmount(),
                newTransfer.getTransferType(), newTransfer.getTransferStatus());
        try {
            newTransfer.setTransferId(newId);
            return newTransfer;
        } catch (NullPointerException e) {
            return null;
        }
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

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        //transfer.setUserFrom(rowSet.getObject("user_id"), User.class);
        return transfer;
    }

}
