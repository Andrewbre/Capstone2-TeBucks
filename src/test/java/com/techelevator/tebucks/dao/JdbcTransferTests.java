package com.techelevator.tebucks.dao;

import com.techelevator.tebucks.model.Transfer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTransferTests extends BaseDaoTests {


    protected static final Transfer TRANSFER_1 = new Transfer(1, );
    protected static final Transfer TRANSFER_2 = new Transfer(2, );
    protected static final Transfer TRANSFER_3 = new Transfer(3, );


    private JdbcTransferDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcTransferDao(jdbcTemplate);


        //Checking for null
        @Test
        public void getTransferByUserId_null () {

        }

        @Test
        public void createTransfer_null() {

        }

        @Test
        public void updateTransfer_null() {
            sut.updateTransfer();


        }
        //Happy Path
        @Test void getTransferByUserId_Happy_Path() {

        }
        @Test void createTransfer_Happy_Path(){

        }
        @Test void updateTransfer_Happy_Path(){

        }
        @Test void getAllTransfersByUserId_Happy_Path(){

        }
        //Negative & decimal
        @Test void createTransfer_negative(){

        }
        @Test void updateTransfer_negative(){

        }
        @Test void createTransfer_decimal(){

        }
        @Test void updateTransfer_decimal(){

        }
    }
}
