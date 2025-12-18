package com.omar.bank.model;

import com.omar.bank.exception.InsufficientAmountException;
import com.omar.bank.exception.InvalidAccountException;
import com.omar.bank.exception.InvalidAmountException;

public class CurrentAccount extends Account{

    private double overdraftLimit;

    public CurrentAccount(String accountNumber, Customer owner, double overdraftLimit) throws InvalidAccountException {
        super(accountNumber, owner, AccountType.CURRENT);
        this.overdraftLimit = overdraftLimit;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public void withdraw(double amount) throws InvalidAmountException, InsufficientAmountException {
        if (amount<=0){
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        double feeAmount = amount * Account.getWithdrawFeePercent();
        double total = amount+feeAmount;
        double validatedBalance = balance-total;

        if (validatedBalance < -overdraftLimit) {
            throw new InsufficientAmountException("Overdraft limit exceeded");
        }
        balance = validatedBalance;
        Transaction transaction = new Transaction(TransactionType.WITHDRAWAL,amount,feeAmount,balance);
        recordTransaction(transaction);

    }

}
