package com.omar.bank.service;

import com.omar.bank.exception.DuplicateAccountException;
import com.omar.bank.exception.DuplicateNationalIdException;
import com.omar.bank.exception.InvalidNationalIdException;
import com.omar.bank.model.Account;
import com.omar.bank.model.Customer;
import com.omar.bank.util.IdGenerator;
import com.omar.bank.util.NationalIdValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BankService {
    private final Map<String, Customer> customersByNationalId = new HashMap<>();
    private final List<Account> accounts = new ArrayList<>();

    private BankService() {

    }
    private static class BankServiceHolder {
        private static final BankService INSTANCE = new BankService();
    }
    public static BankService getInstance() {
        return BankServiceHolder.INSTANCE;
    }



    //    create customer method
public Customer createCustomer(String name, String nationalId)
        throws DuplicateNationalIdException, InvalidNationalIdException {

    if (nationalId == null) throw new InvalidNationalIdException("National ID cannot be null");

    nationalId = nationalId.trim();
    NationalIdValidator.validateNationalId(nationalId);
    name = (name == null) ? null : name.trim();

    if (customersByNationalId.containsKey(nationalId)) {
        throw new DuplicateNationalIdException("Customer with this nationalId already exists");
    }

    Customer customer = new Customer(name, nationalId);
    customer.setSystemId(IdGenerator.generateCustomerId());
//    System.out.println(customer.getSystemId());
    customersByNationalId.put(nationalId, customer);
    return customer;
}


    //    open account method --> Polymorphism
    public Account openAccount(Account account) throws DuplicateAccountException {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        String accNum = account.getAccountNumber();
        if (accNum == null || accNum.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }

        // duplicate check (null-safe)
        boolean accountExists = accounts.stream()
                .anyMatch(a -> a.getAccountNumber() != null && a.getAccountNumber().equals(accNum));
        if (accountExists) {
            throw new DuplicateAccountException("Account with this account number already exists");
        }

        Customer owner = account.getOwner();
        if (owner == null) {
            throw new IllegalArgumentException("Account owner cannot be null");
        }

        // optionally ensure the owner is a known customer
        boolean ownerRegistered = customersByNationalId.containsKey(owner.getNationalId());
        if (!ownerRegistered) {
            throw new IllegalArgumentException("Account owner is not registered as a customer");
        }

        // persist
        accounts.add(account);
        owner.addAccount(account);

        return account;
    }

    public Customer findCustomerByNationalId(String nationalId) {
        return nationalId == null ? null : customersByNationalId.get(nationalId.trim());
    }

    public List<Customer> getCustomers() {
        return new ArrayList<>(customersByNationalId.values());
    }


    public List<Account> getAccounts() {
        return accounts;
    }


}
