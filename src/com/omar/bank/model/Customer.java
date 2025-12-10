package com.omar.bank.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer extends Person {


   private List<Account> accounts;

   public Customer(String name, String nationalId) {
       super(name, nationalId);
       System.out.println("Creating customer with name: " + name + " and nationalId: " + nationalId);
       this.accounts = new ArrayList<>();
   }
    public Customer(String systemId, String name, String nationalId, String emailAddress, String phoneNumber) {
        super(systemId, name, nationalId, emailAddress, phoneNumber);
        this.accounts = new ArrayList<>();
    }
    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {

        try {
            this.accounts = accounts;
        } catch (IllegalArgumentException e) {
            System.out.println("Error setting accounts: " + e.getMessage());
        }
    }
    public void addAccount(Account account){
        this.accounts.add(account);
    }
}
