package com.omar.bank.util;

import java.util.UUID;

public class IdGenerator {
    private static final String BANK_CODE = "1001";
    private static final String BRANCH_CODE = "0001";
    private static int accountSequence = 1;

//    private static long idCounter = 1000000000L;

    public static UUID generateCustomerId() {
        return UUID.fromString("FIN-" + UUID.randomUUID());
    }

    public static String generateAccountNumber() {
        String sequenceStr = String.format("%06d", accountSequence++);
        return BANK_CODE + BRANCH_CODE + sequenceStr;
    }

}

