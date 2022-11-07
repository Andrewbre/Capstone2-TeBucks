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
    private static final Transfer TRANSFER_1 = new Transfer(1, "Send", "Approved", USER_1, USER_2, new BigDecimal("750.00"), LocalDate.now());
    private static final Transfer TRANSFER_2 = new Transfer(2, "Request", "Rejected", USER_2, USER_1, new BigDecimal("50.60"), LocalDate.now());
    private static final Transfer TRANSFER_3 = new Transfer(3, "Send", "Approved", USER_3, USER_2,new BigDecimal("1001.00"), LocalDate.now());


    private JdbcTransferDao sut;

    private Transfer testTransfer;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(dataSource, new JdbcUserDao(jdbcTemplate));
        Transfer testTransfer = new Transfer(9, "send", "Approved", USER_1, USER_2, BigDecimal.valueOf(45.00), LocalDate.now());

    }


    @Test
    public void getTransferByUserId_not_null() {
        Transfer actual = sut.getTransferById(2);
        Assert.assertNotNull(actual);
    }

    @Test
    public void createTransfer_new_id_not_null() {

        NewTransferDto testTransfer = new NewTransferDto();
        testTransfer.setAmount(BigDecimal.TEN);
        testTransfer.setTransferType("Send");
        testTransfer.setUserFrom(USER_1.getId());
        testTransfer.setUserTo(USER_2.getId());
        Transfer createdTransfer = sut.createNewTransfer(testTransfer); //create Java Object
        int newId = createdTransfer.getTransferId(); //get primary key from object returned to us
        Assert.assertTrue(newId > 0); // validate, no longer 0

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
    public void getTransferById_Happy_Path() {
        Transfer actual = sut.getTransferById(1);
        assertTransfersEqual(TRANSFER_1, actual);
    }

    @Test
    public void createTransfer_Happy_Path() {
        NewTransferDto input = new NewTransferDto();
        input.setUserTo(3);
        input.setUserFrom(2);
        input.setTransferType("Send");
        input.setAmount(new BigDecimal("10.00"));
        Transfer expected = new Transfer();
        expected.setUserTo(USER_3);
        expected.setUserFrom(USER_2);
        expected.setTransferStatus("Approved");
        expected.setTransferType("Send");
        expected.setAmount(new BigDecimal("10.00"));

        Transfer output = sut.createNewTransfer(input);

        Assert.assertNotEquals(0, output.getTransferId());
        expected.setTransferId(output.getTransferId());

        assertTransfersEqual(expected, output);

    }

    @Test
    public void getAllTransfersByUserId_Happy_Path() {

        List<Transfer> actual = sut.getAllTransfersByUserId(1);
        Assert.assertEquals(2, actual.size());

        assertTransfersEqual(TRANSFER_1, actual.get(0));
        assertTransfersEqual(TRANSFER_2, actual.get(1));
    }

    @Test
    public void transfer_zero() {
        Transfer input = sut.getTransferById(3);
        input.setAmount(BigDecimal.valueOf(0.00));

        Assert.assertNotSame(input.getAmount(), TRANSFER_3.getAmount());

    }

    private void assertTransfersEqual(Transfer expectedTransfer, Transfer actualTransfer) {
        Assert.assertEquals(expectedTransfer.getAmount(), actualTransfer.getAmount());
        Assert.assertEquals(expectedTransfer.getTransferStatus(), actualTransfer.getTransferStatus());
        Assert.assertEquals(expectedTransfer.getTransferType(), actualTransfer.getTransferType());
        Assert.assertEquals(expectedTransfer.getUserFrom().getId(), actualTransfer.getUserFrom().getId());
        Assert.assertEquals(expectedTransfer.getUserTo().getId(), actualTransfer.getUserTo().getId());
    }

}
