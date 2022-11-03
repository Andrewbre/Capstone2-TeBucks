package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

import static com.techelevator.tebucks.model.Transfer.TRANSFER_STATUS_APPROVED;
import static com.techelevator.tebucks.model.Transfer.TRANSFER_STATUS_REJECTED;

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
    public boolean completeTransferSend (Transfer transfer, User userFrom, User userTo) {
        if (transfer.getTransferType().equals("Send")) {
            if ( transfer.getAmount().compareTo(userFrom.getBalance()) <= 0) {
                String sql = "update user set balance = ? where user_id = ?";
                String sql2 = "update user set balance = ? where user_id = ?";
                SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,userFrom.getBalance().subtract(transfer.getAmount()),userFrom.getId());
                SqlRowSet rowSet2 = jdbcTemplate.queryForRowSet(sql,userTo.getBalance().add(transfer.getAmount()),userTo.getId());
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public boolean approveTransferRequest (Transfer transfer, User userFrom, User userTo) {
        if (transfer.getTransferType().equals("Request")) {
            if (transfer.getAmount().compareTo(userTo.getBalance()) <= 0) {
                String sql1 = "update user set balance = ? where user_id = ?";
                String sql2 = "update transfer set transfer_status = ? where transfer_id = ?";
                SqlRowSet rowSet1 = jdbcTemplate.queryForRowSet(sql1,userFrom.getBalance().add(transfer.getAmount()),userFrom.getId());
                SqlRowSet rowset2 = jdbcTemplate.queryForRowSet(sql1,userTo.getBalance().subtract(transfer.getAmount()),userTo.getId());
                SqlRowSet rowSet3 = jdbcTemplate.queryForRowSet(sql2,TRANSFER_STATUS_APPROVED,transfer.getTransferId());
                return true;
            }
        }
        return false;
    }
    public void rejectTransferRequest (Transfer transfer) {
        String sql = "update transfer set transfer_status = ? where transfer_id = ?";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,TRANSFER_STATUS_REJECTED,transfer.getTransferId());

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
