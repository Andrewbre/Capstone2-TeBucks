package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Transfer;
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
        String sql = "SELECT * FROM transferId WHERE transaction_id = ?";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()) {
            return mapRowToTransfer(result);
        }
        return null;
    }

    @Override
    public Transfer createNewTransfer(Transfer newTransfer) {

        String sql = "INSERT INTO transactions (user_id, logged_time, recipient_id, amount, " +
                "transaction_type, is_completed VALUES (?, ?, ?, ?, ?, ?);";
        Integer newId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getUserFrom(),
                newTransfer.getLoggedtime(), newTransfer.getUserTo(), newTransfer.getAmount(),
                newTransfer.getTransferType(), newTransfer.getTransferStatus());
        newTransfer.setTransferId(newId);
        return newTransfer;
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
        return transfer;
    }

}
