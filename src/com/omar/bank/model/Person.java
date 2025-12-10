package com.omar.bank.model;

import com.omar.bank.util.NationalIdValidator;

import java.util.UUID;

public abstract class Person {
//  -- is a UUID---> unique ID in the entire system
    private String systemId;
    private String name;
    private String nationalId;
    private String emailAddress;
    private String phoneNumber;

    public Person(String name, String nationalId) {
        try {
            NationalIdValidator.validateNationalId(nationalId);
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        this.name = name;
        this.nationalId = nationalId;
    }

    public Person(String systemId, String name, String nationalId, String emailAddress, String phoneNumber) {
        try {
            NationalIdValidator.validateNationalId(nationalId);
        }catch (RuntimeException e) {
            System.out.println("Error : " + e.getMessage());
        }
        this.systemId = systemId;
        this.name = name;
        this.nationalId = nationalId;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;

    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
