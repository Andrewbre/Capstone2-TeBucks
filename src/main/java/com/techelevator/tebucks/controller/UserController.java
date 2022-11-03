package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.JdbcUserDao;
import com.techelevator.tebucks.dao.TransferDao;
import com.techelevator.tebucks.dao.UserDao;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    UserDao dao;
    TransferDao dao2;

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



    @RequestMapping(path = "/api/account/transfers", method = RequestMethod.GET)
    public List<Transfer> getAccountTransfers(Principal principal) {
        return dao2.getAllTransfersByUserId(dao.findIdByUsername(principal.getName()));

    }

    }
