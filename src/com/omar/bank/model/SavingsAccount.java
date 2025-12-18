package com.omar.bank.model;

import com.omar.bank.exception.InsufficientAmountException;
import com.omar.bank.exception.InvalidAccountException;
import com.omar.bank.exception.InvalidAmountException;

public class SavingsAccount extends Account {

    public SavingsAccount(String accountNumber, Customer owner) throws InvalidAccountException {
        super(accountNumber, owner, AccountType.SAVINGS);
    }

    @Override
    public double withdraw(double amount) throws InsufficientAmountException, InvalidAmountException {

        if (amount<=0) // negative or zero amount logically is not accepted
        {
            throw new InvalidAmountException("Amount must be greater than 0");
        }
        double feeAmount = amount * Account.getWithdrawFeePercent();
        double total = amount+feeAmount;
        if (total>balance) { // the total must be less than balance
            throw new InsufficientAmountException("Insufficient funds");
        }
//            implement subtraction here:
            balance -= total;
            Transaction transaction = new Transaction(TransactionType.WITHDRAWAL,amount,feeAmount,balance);
            recordTransaction(transaction);
            return amount;

    }


}