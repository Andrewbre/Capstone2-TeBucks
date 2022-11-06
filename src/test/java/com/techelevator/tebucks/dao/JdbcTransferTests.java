package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.NewTransferDto;
import com.techelevator.tebucks.model.Transfer;
import com.techelevator.tebucks.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class JdbcTransferTests extends BaseDaoTests {

    private static final User USER_1 = new User(1, "user1", "user1", "USER");
    private static final User USER_2 = new User(2, "user2", "user2", "USER");
    private static final User USER_3 = new User(3, "user3", "user3", "USER");
    private static final Transfer TRANSFER_1 = new Transfer(1, "Send", "Approved", USER_1, USER_2, BigDecimal.valueOf(750.00), LocalDate.now());
    private static final Transfer TRANSFER_2 = new Transfer(2, "Request", "Denied", USER_2, USER_1, BigDecimal.valueOf(50.60), LocalDate.now());
    private static final Transfer TRANSFER_3 = new Transfer(3, "Send", "Pending", USER_3, USER_2, BigDecimal.valueOf(-25.00), LocalDate.now());


    private JdbcTransferDao sut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);
        Transfer testTransfer = new Transfer(9, "send", "Approved", USER_1, USER_2, BigDecimal.valueOf(45.00), LocalDate.now());

    }


 @Test
 public void getTransferByUserId_not_null() {
    Transfer actual = sut.getTransferById(2);
    Assert.assertNotNull(actual);
 }

 @Test
 public void createTransfer_new_id_not_null(NewTransferDto testTransfer) {

     Transfer createdTransfer = sut.createNewTransfer(testTransfer); //create Java Object
     int newId = createdTransfer.getTransferId(); //get primary key from object returned to us
     Assert.assertTrue(newId > 0); // validate, no longer 0

     Assert.assertNotNull(newId);

 }
    @Test
    public void updateTransfer_not_null() {
        Transfer updatedTransfer = sut.getTransferById(1);
        updatedTransfer.setAmount(BigDecimal.valueOf(740.00));
        updatedTransfer.setUserTo(USER_2);
        updatedTransfer.setTransferStatus("Pending");
        updatedTransfer.setTransferType("Request");


        Assert.assertNotNull(updatedTransfer);

    }

    @Test
    public void getTransferByUserId_Happy_Path() {
        Transfer actual = sut.getTransferById(1);
        Assert.assertEquals(actual, 1);
    }

    @Test
    public void updateTransfer_Happy_Path() {
        Transfer updatedTransfer = sut.getTransferById(3);
        
        updatedTransfer.setAmount(BigDecimal.valueOf(454.00));
        updatedTransfer.setUserTo(USER_2);
        updatedTransfer.setUserFrom(USER_1);
        updatedTransfer.setTransferStatus("Pending");
        updatedTransfer.setTransferType("Request");
        updatedTransfer.setLoggedTime(LocalDate.now());

        Assert.assertEquals(updatedTransfer, TRANSFER_3);
    }


    @Test
    public void createTransfer_Happy_Path() {
        Transfer input = new Transfer();

        input.setTransferId(10);
        input.setTransferType("Send");
        input.setAmount(BigDecimal.valueOf(33.00));
        input.setTransferStatus("Approved");
        input.setLoggedTime(LocalDate.now());
        input.setUserFrom(USER_3);
        input.setUserTo(USER_1);

        Assert.assertEquals(input.getTransferId(), 10);


    }


    @Test
    public void update_transfer_status_happy_path() {
        Transfer updatedTransferStatus = sut.getTransferById(1);
        updatedTransferStatus.setTransferStatus("Pending");

        Assert.assertEquals(updatedTransferStatus.getTransferStatus(), TRANSFER_1.getTransferStatus());
    }


    @Test
    public void getAllTransfersByUserId_Happy_Path() {
        List<Transfer> actual = sut.getAllTransfersByUserId(1);
        Assert.assertEquals(1, actual.size());

        Assert.assertEquals(TRANSFER_1, actual.get(0));
        Assert.assertEquals(TRANSFER_2, actual.get(1));


    }

    @Test
    public void updateTransfer_amount_negative() {
        Transfer input = sut.getTransferById(2);
        input.setAmount(BigDecimal.valueOf(-22.00));

        Assert.assertNotSame(input.getAmount(), TRANSFER_2.getAmount());


    }

    @Test
    public void transfer_zero() {
        Transfer input = sut.getTransferById(3);
        input.setAmount(BigDecimal.valueOf(0.00));

        Assert.assertNotSame(input.getAmount(), TRANSFER_3.getAmount());

    }


}
