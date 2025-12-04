package com.omar.bank.model;

import com.omar.bank.exception.InsufficientAmountException;
import com.omar.bank.exception.InvalidAccountException;
import com.omar.bank.exception.InvalidAmountException;

import static com.omar.bank.util.NumberFormatter.customFormatter;

abstract public class Account {
    private static final int ACCOUNT_NUMBER_LENGTH = 16;
    protected static final double WITHDRAW_FEE_PERCENT =0.01 ;


    //    validate: only numbers + length 16 + not changeable (Bank code + Branch code + Serial)
    private final String accountNumber;
    protected double balance;
//
    private Customer owner;
//    Composition Relationship & validate accountNumber
    public Account(String accountNumber, Customer owner)  {

        if (accountNumber == null) {
            throw new InvalidAccountException("Account number cannot be null");
        }
        accountNumber = accountNumber.trim();
        if (accountNumber.length() !=  ACCOUNT_NUMBER_LENGTH) {
            throw new InvalidAccountException("Account number must be " + ACCOUNT_NUMBER_LENGTH + " digits");
        }
        if (accountNumber.isBlank()){
            throw new InvalidAccountException("Account number cannot be Empty or Blank");
        }
        if (!accountNumber.matches("\\d{" + ACCOUNT_NUMBER_LENGTH + "}")){
            throw new InvalidAccountException("Account number must contain digits only");
        }
        if (owner == null) {
            throw new InvalidAccountException("Owner cannot be null");
        }
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

//    public void setBalance(double balance) {
//        this.balance = balance;
//    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }
    public void deposit(double amount) throws InvalidAmountException {
        if (amount<=0)
        {
            throw new InvalidAmountException("Invalid amount");
        }
        balance = balance + amount;
//        this.balanceEnquery();
//        System.out.println();

    }
    public abstract  double withdraw(double amount) throws InvalidAmountException, InsufficientAmountException;

//    polymorphism
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", owner=" + owner +
                '}';
    }

}
