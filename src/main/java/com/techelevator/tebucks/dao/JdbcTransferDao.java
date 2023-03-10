package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.springframework.http.HttpStatus;
import com.techelevator.tebucks.services.LoginService;
import com.techelevator.tebucks.services.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import com.techelevator.tebucks.model.TransferStatusUpdateDto;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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

        String sql = "SELECT transfer_id, user_id, recipient_id, amount::numeric, transfer_type, transfer_status FROM transfers WHERE user_id = ? OR recipient_id = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId, userId);
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
        String sql = "SELECT transfer_id, user_id, recipient_id, amount::numeric, transfer_type, transfer_status FROM transfers WHERE transfer_id = ?;";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql, transferId);
        if (result.next()) {
            return mapRowToTransfer(result);
        }
        return null;
    }

    @Override
    public Transfer createNewTransfer(NewTransferDto newTransfer) {

        if (newTransfer.getUserFrom() == newTransfer.getUserTo() || newTransfer.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String transfer_status = "";
        String sql = "INSERT INTO transfers (user_id, recipient_id, amount, " +
                "transfer_type, transfer_status) VALUES (?, ?, ?, ?, ?) RETURNING transfer_id;";
        if (newTransfer.getTransferType().equals(TRANSFER_TYPE_SEND)) {
            transfer_status = TRANSFER_STATUS_APPROVED;
        } else {
            transfer_status = TRANSFER_STATUS_PENDING;
        }
        Transfer transfer = mapTransferDtoToTransfer(newTransfer);
        Integer transferId = jdbcTemplate.queryForObject(sql, Integer.class, newTransfer.getUserFrom(),
                newTransfer.getUserTo(), newTransfer.getAmount(), newTransfer.getTransferType(), transfer_status);
        transfer.setTransferId(transferId);
        if (transfer.getAmount().compareTo(BigDecimal.valueOf(1000)) >= 0) {
            LoginService login = new LoginService();
            login.addTransfer(transfer, "Transaction over 1000");
        }
        if (transfer.getTransferType().equalsIgnoreCase(TRANSFER_TYPE_SEND)) {
            if (completeTransferSend(transfer, transfer.getUserFrom(), transfer.getUserTo())) {
                transfer.setTransferId(transferId);
                return transfer;
            } else {
                return null;
            }
        }
        try {
            if (transfer.getTransferType().equalsIgnoreCase(TRANSFER_TYPE_REQUEST)) {
                String sql2 = "Update transfers set transfer_status = ? where transfer_id = ?";
                jdbcTemplate.update(sql2,TRANSFER_STATUS_PENDING,transfer.getTransferId());
            }
            transfer.setTransferId(transferId);
        } catch (NullPointerException e) {
            e.getStackTrace();
            return null;
        }

        return transfer;
    }

    public boolean completeTransferSend (Transfer transfer, User userFrom, User userTo) {
        if (transfer.getTransferType().equalsIgnoreCase(TRANSFER_TYPE_SEND)) {
            userFrom.setBalance(userDao.getBalanceByUserId(userFrom.getId()));
            userTo.setBalance(userDao.getBalanceByUserId(userTo.getId()));
            if ( transfer.getAmount().compareTo(userFrom.getBalance()) <= 0) {
                String sql = "update users set balance = ? where user_id = ? RETURNING balance::numeric";
                String sql2 = "update transfers set transfer_status = ? where transfer_id = ?;";
                BigDecimal userFromBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userFrom.getBalance().subtract(transfer.getAmount()),userFrom.getId());
                BigDecimal userToBalance = jdbcTemplate.queryForObject(sql, BigDecimal.class, userTo.getBalance().add(transfer.getAmount()),userTo.getId());
                transfer.setTransferStatus(TRANSFER_STATUS_APPROVED);
                jdbcTemplate.update(sql2, transfer.getTransferStatus(), transfer.getTransferId());

                userFrom.setBalance(userFrom.getBalance().subtract(transfer.getAmount()));
                userTo.setBalance(userTo.getBalance().add(transfer.getAmount()));

                return true;
            } else {
                LoginService login = new LoginService();
                login.addTransfer(transfer, "Overdraft");
                String sql = "UPDATE transfers SET transfer_status = ? WHERE transfer_id = ?";
                transfer.setTransferStatus(TRANSFER_STATUS_REJECTED);
                jdbcTemplate.update(sql, TRANSFER_STATUS_REJECTED, transfer.getTransferId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        return false;
    }
    public boolean approveTransferRequest (Transfer transfer, User userFrom, User userTo) {
        if (transfer.getTransferType().equalsIgnoreCase(TRANSFER_TYPE_REQUEST)) {
            userFrom.setBalance(userDao.getBalanceByUserId(userFrom.getId()));
            userTo.setBalance(userDao.getBalanceByUserId(userTo.getId()));
            if (transfer.getAmount().compareTo(userFrom.getBalance()) <= 0) {
                String sql1 = "update users set balance = ? where user_id = ? RETURNING balance::numeric";
                String sql2 = "update transfers set transfer_status = ? where transfer_id = ?";
                BigDecimal addedBalance = jdbcTemplate.queryForObject(sql1, BigDecimal.class, userFrom.getBalance().subtract(transfer.getAmount()),userFrom.getId());
                BigDecimal subtractedBalance = jdbcTemplate.queryForObject(sql1, BigDecimal.class, userTo.getBalance().add(transfer.getAmount()),userTo.getId());
                jdbcTemplate.update(sql2,TRANSFER_STATUS_APPROVED,transfer.getTransferId());
                transfer.setTransferStatus(TRANSFER_STATUS_APPROVED);

                return true;
            } else {
                LoginService login = new LoginService();
                login.addTransfer(transfer, "Overdraft");
                String sql = "UPDATE transfers SET transfer_status = ? WHERE transfer_id = ?";
                jdbcTemplate.update(sql, TRANSFER_STATUS_REJECTED, transfer.getTransferId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        return false;
    }
    public void rejectTransferRequest (Transfer transfer) {
        String sql = "update transfers set transfer_status = ? where transfer_id = ?";
        jdbcTemplate.update(sql,TRANSFER_STATUS_REJECTED,transfer.getTransferId());

    }
    public void pendingTransferRequest(Transfer transfer) {
        String sql = "UPDATE transfers SET transfer_status = ? WHERE transfer_id = ?";
        jdbcTemplate.update(sql, TRANSFER_STATUS_PENDING, transfer.getTransferId());
    }

    @Override
    public Transfer updateTransfer(int id, TransferStatusUpdateDto transferStatusUpdateDto) {

        String sql = "UPDATE transfers SET transfer_status = ? WHERE transfer_id = ? RETURNING transfer_id, user_id, " +
                "recipient_id, amount::numeric, transfer_type, transfer_status;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferStatusUpdateDto.getTransferStatus(), id);
        if (results.next()) {
            Transfer transfer = mapRowToTransfer(results);

            if (transfer.getTransferStatus().equalsIgnoreCase(TRANSFER_STATUS_REJECTED)) {
                rejectTransferRequest(transfer);
            } else if (transfer.getTransferStatus().equalsIgnoreCase(TRANSFER_STATUS_APPROVED)) {
                approveTransferRequest(transfer, transfer.getUserFrom(), transfer.getUserTo());
            } else if (transfer.getTransferStatus().equalsIgnoreCase(TRANSFER_STATUS_PENDING)) {
                pendingTransferRequest(transfer);
            }
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

