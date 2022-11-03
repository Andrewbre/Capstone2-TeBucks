package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.techelevator.tebucks.model.Transfer.TRANSFER_STATUS_APPROVED;
import static com.techelevator.tebucks.model.Transfer.TRANSFER_STATUS_REJECTED;

@Component
public class JdbcTransferDao implements TransferDao {

    private final JdbcTemplate jdbcTemplate;
    private final JdbcUserDao userDao;

    public JdbcTransferDao(DataSource dataSource, JdbcUserDao userDao) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.userDao = userDao;
    }


    @Override
    public List<Transfer> getAllTransfersByUserId(int userId) {
        List<Transfer> allTransfers = new ArrayList<>();

        String sql = "SELECT transfer_id, user_id, recipient_id, amount, transfer_type FROM transfers WHERE user_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        while(results.next()) {
            allTransfers.add(mapRowToTransfer(results));
        }
        if (allTransfers.isEmpty()) {
            return null;
        }
        return allTransfers;
    }

    @Override
    public Transfer getTransferById(int transferId) {
        String sql = "SELECT transfer_id, user_id, recipient_id, amount, transfer_type FROM transfers WHERE transfer_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()) {
            return mapRowToTransfer(result);
        }
        return null;
    }

    @Override
    public Transfer createNewTransfer(NewTransferDto newTransfer) {

        String sql = "INSERT INTO transfers (user_id, recipient_id, amount, " +
                "transfer_type) VALUES (?, ?, ?, ?) RETURNING user_id, recipient_id, amount, transfer_type;";
        return jdbcTemplate.queryForObject(sql, Transfer.class, newTransfer.getUserFrom(),
                newTransfer.getUserTo(), newTransfer.getAmount(), newTransfer.getTransferType());
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
    public Transfer updateTransfer(int id, TransferStatusUpdateDto transferStatusUpdateDto) {
        return null;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setUserFrom(userDao.getUserById(rowSet.getInt("user_id")));
        transfer.setUserTo(userDao.getUserById(rowSet.getInt("recipient_id")));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setTransferType(rowSet.getString("transfer_type"));
        return transfer;
    }

}

