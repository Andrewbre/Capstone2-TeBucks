package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.TransferDao;
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

    public TransferController(TransferDao transferDao, TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.transferDao = transferDao;

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
    public Transfer updateTransferByIdStatus(@PathVariable int id, @Valid @RequestBody TransferStatusUpdateDto transferStatusUpdateDto) {
        TransferStatusUpdateDto input = new TransferStatusUpdateDto();
       if (!input.getTransferStatus()) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found");
       }
        return transferDao.updateTransfer( id,transferStatusUpdateDto);

    }
}
