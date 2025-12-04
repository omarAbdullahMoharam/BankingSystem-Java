import com.omar.bank.exception.InvalidAmountException;
import com.omar.bank.model.CurrentAccount;
import com.omar.bank.model.Customer;
import com.omar.bank.model.SavingsAccount;

import java.util.Scanner;
import java.util.UUID;

import static com.omar.bank.util.NumberFormatter.customFormatter;

public class Main {
    public static void main(String[] args) throws InvalidAmountException {
        Scanner in = new Scanner(System.in);
         UUID systemId =  UUID.randomUUID();
         String name= "Omar Abdullah Moharam";
         String nationalId="30212121700915";
         String emailAddress="omarmoharam790@gmail.com";
         String phoneNumber="01064385332";
        Customer customer = new Customer(systemId, name, nationalId, emailAddress, phoneNumber);
//        SavingsAccount fb = new SavingsAccount(
//                "1234567891011456",
//                    customer
//        );
        CurrentAccount fb = new CurrentAccount(
                "1234567891011456",
                customer,
                500
        );
//        fb.deposit(1000);

//        FinanceBank fb = new FinanceBank();
        String option ;
        do{
            System.out.println("Welcome to the Finance Bank ^_^");
            System.out.println("Please enter your choice");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Balance");
            System.out.println("4. Exit");

            option = in.next();
            switch (option){
                case "1":
                {
                    System.out.println("Please enter the amount you want to deposit");
                    double amount = in.nextDouble();
                    try{
                        fb.deposit(amount);
                        System.out.println("Deposited : " + customFormatter(amount));
                        System.out.println("Balance after deposit: "+fb.getBalance()+'\n');
                    }catch (com.omar.bank.exception.InvalidAmountException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                }
                case "2":
                    {
                    System.out.println("Please enter the amount you want to withdraw");
                    double amount = in.nextDouble();
                        try {
                            fb.withdraw(amount);
                            System.out.println("Withdrawn: " + amount);
                            System.out.println("Balance now: " + fb.getBalance());
                        }catch (com.omar.bank.exception.InvalidAmountException | com.omar.bank.exception.InsufficientAmountException e){
                            System.out.println(e.getMessage());
                        }
                        break;
                    }
                    case "3":
                    {
                        System.out.println( fb.getBalance());
                        break;
                    }
                    case "4":{
                        return;
                    }
                default:{
                    System.out.println("Invalid choice. Try again\n");
                }

            }
        }while (!(option.equalsIgnoreCase("4")));


    }
}