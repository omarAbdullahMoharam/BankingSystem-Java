package com.omar.bank.model;

import com.omar.bank.exception.InsufficientAmountException;
import com.omar.bank.exception.InvalidAccountException;
import com.omar.bank.exception.InvalidAmountException;
import com.omar.bank.util.AccountValidator;


abstract public class Account {
    private static final int ACCOUNT_NUMBER_LENGTH = 16;
    private static final double WITHDRAW_FEE_PERCENT =0.01 ;

    private final Customer owner;
    private final AccountType accountType;
    protected double balance;

    //    validate: only numbers + length 16 + not changeable (Bank code + Branch code + Serial)
    private final String accountNumber;



//    Composition Relationship & validate accountNumber
protected Account(String accountNumber, Customer owner, AccountType accountType) throws InvalidAccountException {
    this.accountType = accountType;

    // validate (throw on error)
    AccountValidator.validateAccountNumber(accountNumber);
    AccountValidator.validateOwner(owner);

    this.accountNumber = accountNumber;
    this.owner = owner;
    this.balance = 0;
}

    public String getAccountNumber() {
        return accountNumber;
    }
    public AccountType getAccountType() { return accountType; }

    public double getBalance() {
        return balance;
    }

//    public void setBalance(double balance) {
//        this.balance = balance;
//    }

    public Customer getOwner() {
        return owner;
    }

//    public void setOwner(Customer owner) {
//        this.owner = owner;
//    }
    public void deposit(double amount) throws InvalidAmountException {
        if (amount<=0)
        {
            throw new InvalidAmountException("Invalid amount");
        }
        balance = balance + amount;
//        this.balanceEnquery();
//        System.out.println();

    }

    public abstract double withdraw(double amount) throws InvalidAmountException, InsufficientAmountException;
    // helper for printing
    public String getTypeName() { return accountType.label(); }



    public static int getAccountNumberLength() {
        return ACCOUNT_NUMBER_LENGTH;
    }

    public static double getWithdrawFeePercent() {
        return WITHDRAW_FEE_PERCENT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountNumber != null && accountNumber.equals(account.accountNumber);
    }

    @Override
    public int hashCode() {
        return accountNumber != null ? accountNumber.hashCode() : 0;
    }


    //    polymorphism
    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                ", ownerNationalId=" + (owner != null ? owner.getNationalId() : "null") +
                '}';
    }


}
