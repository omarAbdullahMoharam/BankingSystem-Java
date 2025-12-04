package com.omar.bank.model;

import java.util.List;
import java.util.UUID;

public class Customer extends Person {


   private List<Account> accounts;

    public Customer(UUID systemId, String name, String nationalId, String emailAddress, String phoneNumber) {
        super(systemId, name, nationalId, emailAddress, phoneNumber);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

}
