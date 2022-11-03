package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.TransferDao;
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

}
