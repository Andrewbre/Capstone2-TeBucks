package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.TransferDao;

import com.techelevator.tebucks.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class TransferController {
    private final TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/api/transfers", method = RequestMethod.POST)
    public Transfer createTransfer(@Valid @RequestBody NewTransferDto newTransfer) {
        return transferDao.createNewTransfer(newTransfer);
    }


    @RequestMapping(path = "/api/transfers/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id) {
        return transferDao.getTransferById(id);

    }

    @RequestMapping(path = "/api/transfers/{id}/status", method = RequestMethod.PUT)
    public Transfer updateTransferByIdStatus(@PathVariable int id, @RequestBody TransferStatusUpdateDto transferStatusUpdateDto) {
        return transferDao.updateTransfer(id, transferStatusUpdateDto);
    }

}
