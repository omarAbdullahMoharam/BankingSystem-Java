package com.omar.bank.exception;

public class InsufficientAmountException extends Exception {
    InsufficientAmountException(){}
    public InsufficientAmountException(String msg){
        super(msg);
    }
}
