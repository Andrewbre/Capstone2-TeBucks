package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.TransferDao;
<<<<<<< HEAD
import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransferController {

    private final TransferDao dao;

    public TransferController(TransferDao dao) {
        this.dao = dao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/transfers")
    public Transfer createTransfer(@RequestBody NewTransferDto newTransfer) {
        return dao.createNewTransfer(newTransfer);
    }

    @GetMapping("/api/transfers/{id}")
    public Transfer getTransferById(@PathVariable("id") int transferId) {
        return dao.getTransferById(transferId);
    }

=======
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.TransferStatusUpdateDto;
import com.techelevator.tebucks.security.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
public class TransferController {
    private final TransferDao transferDao;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public TransferController(TransferDao transferDao, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.transferDao = transferDao;
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/transfers", method = RequestMethod.POST)
    public void transfer(@Valid @RequestBody Transfer newTransfer) {
        if (!transferDao.equals(newTransfer)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Transfer does not exist");
        }
        transferDao.equals(newTransfer);
    }


    @RequestMapping(path = "/api/transfer/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id) {
        return transferDao.getTransferById(id);

    }


    @RequestMapping(path = "/api/transfer/{id}/status", method = RequestMethod.PUT)
    public Transfer getTransferByIdStatus(@PathVariable int id, @Valid @RequestBody TransferStatusUpdateDto transferStatusUpdateDto) {
        TransferStatusUpdateDto input = new TransferStatusUpdateDto();
        if (!input.getTransferStatus()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found");
        }
        return transferDao.updateTransfer(id, transferStatusUpdateDto);

    }
>>>>>>> d46fd05498cd22910033eed709ea41cef8e79977
}
