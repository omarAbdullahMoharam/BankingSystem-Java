package com.omar.bank.service;

import com.omar.bank.exception.DuplicateAccountException;
import com.omar.bank.exception.DuplicateNationalIdException;
import com.omar.bank.exception.InvalidAccountException;
import com.omar.bank.exception.InvalidNationalIdException;
import com.omar.bank.model.Account;
import com.omar.bank.model.Customer;
import com.omar.bank.util.IdGenerator;
import com.omar.bank.util.NationalIdValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.omar.bank.util.AccountValidator.validateAccountNumber;


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

    String systemId = IdGenerator.generateCustomerId();
    Customer customer = new Customer(name, nationalId, systemId);
//    System.out.println(customer.getSystemId());
    customersByNationalId.put(nationalId, customer);
    return customer;
}


    //    open account method --> Polymorphism
    public void openAccount(Account account)
            throws DuplicateAccountException, InvalidAccountException {

        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }

        String accNum = account.getAccountNumber();
        if (accNum == null || accNum.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be null or empty");
        }

        // validate structure (length, prefix, digits, etc.)
        validateAccountNumber(accNum);

        // check duplicate
        boolean accountExists = accounts.stream()
                .anyMatch(a -> accNum.equals(a.getAccountNumber()));

        if (accountExists) {
            throw new DuplicateAccountException("Account with this account number already exists");
        }

        Customer owner = account.getOwner();
        if (owner == null) {
            throw new IllegalArgumentException("Account owner cannot be null");
        }

        // ensure owner is registered customer
        if (!customersByNationalId.containsKey(owner.getNationalId())) {
            throw new IllegalArgumentException("Account owner is not registered as a customer");
        }

        // persist
        accounts.add(account);
        owner.addAccount(account);
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
