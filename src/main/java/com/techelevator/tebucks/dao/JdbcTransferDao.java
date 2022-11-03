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
        transfer.setUserFrom(userDao.getUserById(rowSet.getInt("user_id")));
        transfer.setUserTo(userDao.getUserById(rowSet.getInt("recipient_id")));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setTransferType(rowSet.getString("transfer_type"));
        return transfer;
    }

}
