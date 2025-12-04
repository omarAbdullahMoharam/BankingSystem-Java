package com.omar.bank.model;

import java.util.UUID;

public abstract class Person {
//  -- is a UUID---> unique ID in the entire system
    private UUID systemId;
    private String name;
    private String nationalId;
    private String emailAddress;
    private String phoneNumber;


    public Person(UUID systemId, String name, String nationalId, String emailAddress, String phoneNumber) {
        this.systemId = systemId;
        this.name = name;
        this.nationalId = nationalId;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;

    }

    public UUID getSystemId() {
        return systemId;
    }

    public void setSystemId(UUID systemId) {
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
