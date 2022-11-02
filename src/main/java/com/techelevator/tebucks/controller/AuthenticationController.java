package com.techelevator.tebucks.controller;

import javax.validation.Valid;

import com.techelevator.tebucks.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.method.P;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.techelevator.tebucks.dao.UserDao;
import com.techelevator.tebucks.security.jwt.TokenProvider;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Controller to authenticate users.
 */
@RestController
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserDao userDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public LoginResponseDto login(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);

        User user = userDao.findByUsername(loginDto.getUsername());

        return new LoginResponseDto(jwt, user);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@Valid @RequestBody RegisterUserDto newUser) {
        if (!userDao.create(newUser.getUsername(), newUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User registration failed.");
        }

    }


    @RequestMapping(path = "/api/transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id) {
        return getTransferById(id);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/transfers", method = RequestMethod.POST)
    public void transfer(@Valid @RequestBody Transfer newTransfer) {
        if (!userDao.equals(newTransfer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer does not ");
        }
        userDao.equals(newTransfer);
    }

    @RequestMapping(path = "/api/account/transfers", method = RequestMethod.GET)
    public List<Transfer> getAccountTransfers() {
        return null;
    }

    @RequestMapping(path = "/api/transfers/{id}/status", method = RequestMethod.PUT)
    public void postTransferById() {

    }

    @RequestMapping(path = "/api/users", method = RequestMethod.GET)
    public List<User> getListUsers() {
        return null;

    }

    @RequestMapping(path = "/api/account/balance", method = RequestMethod.GET)
    public Account getAccountBalance() {

        return null;

    }
}


