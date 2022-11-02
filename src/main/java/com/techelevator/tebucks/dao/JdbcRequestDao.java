package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Request;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class JdbcRequestDao implements RequestDao{
    private final JdbcTemplate jdbcTemplate;

    public JdbcRequestDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Request> listRequestsByRecipientId(int recipientId) {
        List<Request> results = new ArrayList<>();
        String sql = "Select * from request where recipient_id = ? order by transaction_id";
        SqlRowSet rowSet =jdbcTemplate.queryForRowSet(sql,recipientId);
        while (rowSet.next()) {
            results.add(mapRowToRequest(rowSet));
        }
        return results;
    }

    @Override
    public Request create(Request request) {
        String sql = "insert into request(user_id,transaction_id,recipient_id,amount,is_solved) " +
                "values (?,?,?,?,?)";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,request.getCustId(),request.getTransactionId(),request.getRecipientId(),request.getAmount(),request.isSolved());
        return mapRowToRequest(rowSet);
    }

    @Override
    public void setIsSolved(Request request) {
        String sql = "update Requests set is_solved = ? where transaction_id = ?";
        jdbcTemplate.update(sql,true,request.getTransactionId());

    }
    private Request mapRowToRequest(SqlRowSet rowSet) {
        Request r = new Request();
        r.setCustId(rowSet.getInt("user_id"));
        r.setTransactionId(rowSet.getInt("transaction_id"));
        r.setAmount(rowSet.getBigDecimal("amount"));
        r.setRecipientId(rowSet.getInt("recipient_id"));
        r.setSolved(rowSet.getBoolean("is_solved"));
        return r;
    }
}
