package com.omar.bank.util;

import com.omar.bank.model.Customer;

import java.util.UUID;

public class IdGenerator {
    private static final String CUSTOMER_ID_PREFIX = "FIN-";
    private static final String BANK_CODE = "1001";
    private static final String BRANCH_CODE = "0001";
    private static int accountSequence = 1;

    public static String getBankCode() {
        return BANK_CODE;
    }
    public static String getBranchCode() {
        return BRANCH_CODE;
    }
    //    10010001
//    private static long idCounter = 1000000000L;

    public static String generateCustomerId() {

        return "FIN-" + UUID.randomUUID();
    }

    public static String generateAccountNumber() {
        String sequenceStr = String.format("%06d", accountSequence++);
        return BANK_CODE + BRANCH_CODE + sequenceStr;
    }




}

