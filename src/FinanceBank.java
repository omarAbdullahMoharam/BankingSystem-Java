import com.omar.bank.exception.InsufficientAmountException;
import com.omar.bank.exception.InvalidAmountException;

import java.text.DecimalFormat;

import static com.omar.bank.util.NumberFormatter.customFormatter;

public class FinanceBank {
    private double balance;
    private static final double WITHDRAW_FEE_PERCENT = 0.01;


//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setNationalId(String nationalId) {
//        this.nationalId = nationalId;
//    }

    public FinanceBank() {}
//
//    public FinanceBank(String name, String nationalId, String email) {
//        this.name = name;
//        this.nationalId = nationalId;
//        this.email = email;
//    }

    public void balanceEnquery() {
        System.out.println("Current balance : " + customFormatter(balance));
    }

    public void deposit(double amount) throws InvalidAmountException {
        if (amount<=0)
        {
           throw new InvalidAmountException("Invalid amount");
        }
        balance = balance + amount;
        System.out.println("Deposited : " + customFormatter(amount));
        this.balanceEnquery();
        System.out.println();

    }

    public void withdraw(double amount) throws InsufficientAmountException, InvalidAmountException {
        double feeAmount = amount * WITHDRAW_FEE_PERCENT;
        double total = amount+feeAmount;
        if (total<=0)
        {
            throw new InvalidAmountException("Amount must be greater than 0");
        } else if (total>balance) {
            throw new InsufficientAmountException("Insufficient funds");
        }
        else {
            balance -= total;
            System.out.println("withdrawn : " + customFormatter(amount));
            System.out.println("Fee : " + customFormatter(feeAmount));
            System.out.println("Total out : "+customFormatter(amount) + " Will be withdrawn\n");

//            void df.format(amount);
        }


    }
    private void notifyMail(){

    }
}
