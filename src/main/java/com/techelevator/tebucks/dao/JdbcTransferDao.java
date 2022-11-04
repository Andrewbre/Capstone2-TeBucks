package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.tebucks.model.TransferStatusUpdateDto;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.techelevator.tebucks.model.Transfer.*;

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

        String sql = "SELECT transfer_id, user_id, recipient_id, amount::numeric, transfer_type FROM transfers WHERE user_id = ?;";
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
        String sql = "SELECT transfer_id, user_id, recipient_id, amount::numeric, transfer_type FROM transfers WHERE transfer_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()) {
            return mapRowToTransfer(result);
        }
        return null;
    }

    @Override
    public Transfer createNewTransfer(NewTransferDto newTransfer) {

        String sql = "INSERT INTO transfers (user_id, recipient_id, amount, " +
                "transfer_type) VALUES (?, ?, ?, ?) RETURNING transfer_id;";
        Transfer transfer = mapTransferDtoToTransfer(newTransfer);
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getUserFrom(),
                newTransfer.getUserTo(), newTransfer.getAmount(), newTransfer.getTransferType());
        try {
            transfer.setTransferStatus("Pending");
            if (transfer.getTransferType().equals("Request")) {
                String sql2 = "Update transfers set transfer_status = ? where transfer_id = ?";
                jdbcTemplate.update(sql2,TRANSFER_STATUS_PENDING,transfer.getTransferId());
            }
            transfer.setTransferId(transferId);
        } catch (NullPointerException e) {
            e.getStackTrace();
            return null;
        }
        if (transfer.getTransferType().equals("Send")) {
            if (completeTransferSend(transfer, transfer.getUserFrom(), transfer.getUserTo())) {
                return transfer;
            } else {
                return null;
            }
        } else {

        }
        return transfer;
    }

    public boolean completeTransferSend (Transfer transfer, User userFrom, User userTo) {
        if (transfer.getTransferType().equals("Send")) {
            userFrom.setBalance(userDao.getBalanceByUserId(userFrom.getId()));
            userTo.setBalance(userDao.getBalanceByUserId(userTo.getId()));
            if ( transfer.getAmount().compareTo(userFrom.getBalance()) <= 0) {
                String sql = "update users set balance = ? where user_id = ? RETURNING balance::numeric";
                String sql2 = "update users set balance = ? where user_id = ? RETURNING balance::numeric";
                BigDecimal userFromBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userFrom.getBalance().subtract(transfer.getAmount()),userFrom.getId());
                BigDecimal userToBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userTo.getBalance().add(transfer.getAmount()),userTo.getId());
                transfer.setTransferStatus("Approved");
                userFrom.setBalance(userFrom.getBalance().subtract(transfer.getAmount()));
                userTo.setBalance(userTo.getBalance().add(transfer.getAmount()));
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public boolean approveTransferRequest (Transfer transfer, User userFrom, User userTo) {
        if (transfer.getTransferType().equals("Request")) {
            userFrom.setBalance(userDao.getBalanceByUserId(userFrom.getId()));
            userTo.setBalance(userDao.getBalanceByUserId(userTo.getId()));
            if (transfer.getAmount().compareTo(userTo.getBalance()) <= 0) {
                String sql1 = "update user set balance = ? where user_id = ? RETURNING balance";
                String sql2 = "update transfers set transfer_status = ? where transfer_id = ?";
                BigDecimal addedBalance = jdbcTemplate.queryForObject(sql1, BigDecimal.class, userFrom.getBalance().add(transfer.getAmount()),userFrom.getId());
                BigDecimal subtractedBalance = jdbcTemplate.queryForObject(sql1, BigDecimal.class, userTo.getBalance().subtract(transfer.getAmount()),userTo.getId());
                SqlRowSet rowSet3 = jdbcTemplate.queryForRowSet(sql2,TRANSFER_STATUS_APPROVED,transfer.getTransferId());
                transfer.setTransferStatus(TRANSFER_STATUS_APPROVED);
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

        String sql = "UPDATE transfers SET transfer_status = ? WHERE transfer_id = ? RETURNING transfer_id, user_id, " +
                "recipient_id, amount, transfer_type, transfer_status;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferStatusUpdateDto.getTransferStatus(), id);
        Transfer transfer = mapRowToTransfer(results);
        if (transfer.getTransferStatus().equals(TRANSFER_STATUS_REJECTED)) {
            rejectTransferRequest(transfer);
            return transfer;
        } else if (transfer.getTransferStatus().equals(TRANSFER_STATUS_APPROVED)) {
            approveTransferRequest(transfer, transfer.getUserFrom(), transfer.getUserTo());
            return transfer;
        }
        return null;
    }

    private Transfer mapRowToTransfer(SqlRowSet rowSet) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rowSet.getInt("transfer_id"));
        transfer.setUserFrom(userDao.getUserById(rowSet.getInt("user_id")));
        transfer.setUserTo(userDao.getUserById(rowSet.getInt("recipient_id")));
        transfer.setAmount(rowSet.getBigDecimal("amount"));
        transfer.setTransferType(rowSet.getString("transfer_type"));
        transfer.setTransferStatus(rowSet.getString("transfer_status"));
        return transfer;
    }

    private Transfer mapTransferDtoToTransfer(NewTransferDto dto) {
        Transfer transfer = new Transfer();
        transfer.setUserFrom(userDao.getUserById(dto.getUserFrom()));
        transfer.setUserTo(userDao.getUserById(dto.getUserTo()));
        transfer.setAmount(dto.getAmount());
        transfer.setTransferType(dto.getTransferType());
        return transfer;
    }

}

