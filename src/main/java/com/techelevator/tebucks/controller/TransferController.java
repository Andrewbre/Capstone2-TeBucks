package com.techelevator.tebucks.controller;

import com.techelevator.tebucks.dao.TransferDao;
<<<<<<< HEAD
=======

import com.techelevator.tebucks.model.NewTransferDto;
>>>>>>> 141c3aa559ad5c1b70c2676dff856e727fad4157
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.TransferStatusUpdateDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class TransferController {
    private final TransferDao transferDao;

    public TransferController(TransferDao transferDao) {
        this.transferDao = transferDao;
<<<<<<< HEAD

=======
>>>>>>> 141c3aa559ad5c1b70c2676dff856e727fad4157
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
    public Transfer updateTransferByIdStatus(@PathVariable int id, @Valid @RequestBody TransferStatusUpdateDto transferStatusUpdateDto) {
<<<<<<< HEAD
        TransferStatusUpdateDto input = new TransferStatusUpdateDto();
       if (!input.getTransferStatus()) {
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found");
       }
        return transferDao.updateTransfer( id,transferStatusUpdateDto);
=======
//        if (!input.getTransferStatus()) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found");
//        }
        return transferDao.updateTransfer(id, transferStatusUpdateDto);
>>>>>>> 141c3aa559ad5c1b70c2676dff856e727fad4157

    }
}
