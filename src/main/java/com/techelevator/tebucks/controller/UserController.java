package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.JdbcUserDao;
import com.techelevator.tebucks.dao.UserDao;
import com.techelevator.tebucks.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    UserDao dao;

    public UserController(JdbcUserDao dao) {
        this.dao = dao;
    }

    @GetMapping("/api/account/balance")
    public BigDecimal getUserBalance(Principal principal) {
        return dao.getBalanceByUserId(dao.findIdByUsername(principal.getName()));
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return dao.findAll();
    }

}
