package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int id);

    User findByUsername(String username);

    List<Transfer> getAllTransfers(int id);

    int findIdByUsername(String username);

    BigDecimal getBalanceByUserId(int userId);

    boolean create(String username, String password);
}
