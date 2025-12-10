package com.omar.bank.service;

import com.omar.bank.exception.DuplicateAccountException;
import com.omar.bank.exception.DuplicateNationalIdException;
import com.omar.bank.model.Account;
import com.omar.bank.model.Customer;
import com.omar.bank.util.IdGenerator;

import java.util.ArrayList;
import java.util.List;


public class BankService {
    private final List<Customer> customers = new ArrayList<>();
    private final List<Account> accounts = new ArrayList<>();
//    private final Map<Customer, List<Account>> customerAccountsMap =  new java.util.HashMap<>();

    private BankService() {

    }
    private static class BankServiceHolder {
        private static final BankService INSTANCE = new BankService();
    }
    public static BankService getInstance() {
        return BankServiceHolder.INSTANCE;
    }

//    create customer method
    public Customer createCustomer(String name, String nationalId) throws DuplicateNationalIdException {
        Customer customer = new Customer(name, nationalId);
        boolean customerExists = customers.stream()
                .anyMatch(c -> c.getNationalId().equals(nationalId));
        if (customerExists) {
            throw new DuplicateNationalIdException("Customer with this nationalId already exists");
        }
        String id = IdGenerator.generateCustomerId();
        customer.setSystemId(id);
        customers.add(customer);
        return customer;
    }


//    open account method --> Polymorphism
    public void openAccount(Account accountType) throws DuplicateAccountException, NullPointerException {
        // Implementation for opening an account

        if (accountType == null) {
            throw new NullPointerException("Account type is null");
        }

//        check if the account already exists
        boolean accountExists = accounts.stream()
                .anyMatch((a) -> a.getAccountNumber().equals(accountType.getAccountNumber()));
        if (accountExists) {
            throw new DuplicateAccountException("Account with this account number already exists");
        }

        accounts.add(accountType);
        Customer owner = accountType.getOwner();
//        System.out.println("Adding account to owner: " + owner.getName());
        owner.addAccount(accountType);

    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Account> getAccounts() {
        return accounts;
    }


}
