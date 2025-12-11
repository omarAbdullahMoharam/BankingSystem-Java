package com.omar.bank.util;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Set;

import com.omar.bank.exception.InvalidNationalIdException;

public class NationalIdValidator  {

    private static final Set<String> GOVERNORATE_CODES = Set.of(
            "01","02","03","04","11","12","13","14","15","16",
            "17","18","19","21","22","23","24","25","26","27",
            "28","29","31","32","33","34","35"
    );

    public static void validateNationalId(String id) {
        if (id == null || !id.matches("^[23]\\d{13}$")) {
            throw new InvalidNationalIdException("National ID must be 14 digits and start with 2 or 3");
        }
        id = id.trim();
//        must be 14 digits and start with 2 or 3
        if (!id.matches("^[23]\\d{13}$")) {
            throw new InvalidNationalIdException("National ID must be 14 digits and start with 2 or 3");
        }

        // year of birth: first digit -> century, next two digits -> year within century
        int century = (id.charAt(0) == '2') ? 1900 : 2000;
        int year = century + Integer.parseInt(id.substring(1, 3));
        int month = Integer.parseInt(id.substring(3, 5));
        int day = Integer.parseInt(id.substring(5, 7));

        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new InvalidNationalIdException("Invalid birth date in National ID", e);
        }

        String govCode = id.substring(7, 9);
        if (!GOVERNORATE_CODES.contains(govCode)) {
            throw new InvalidNationalIdException("Invalid governorate code in National ID: " + govCode);
        }
    }
}
