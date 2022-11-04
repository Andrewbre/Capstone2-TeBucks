package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    List<User> allUsersExceptCurrent(String username);

    User getUserById(int id);

    User findByUsername(String username);

    int findIdByUsername(String username);

    BigDecimal getBalanceByUserId(int userId);

    boolean create(String username, String password);
}
