import com.omar.bank.exception.DuplicateNationalIdException;
import com.omar.bank.exception.InvalidAmountException;
import com.omar.bank.model.CurrentAccount;
import com.omar.bank.model.Customer;
import com.omar.bank.model.SavingsAccount;
import com.omar.bank.service.BankService;
import com.omar.bank.util.IdGenerator;

import java.util.Scanner;
import java.util.UUID;

import static com.omar.bank.util.NumberFormatter.customFormatter;

public class Main {
    public static void main(String[] args)  {
        Scanner in = new Scanner(System.in);

        BankService bankService = BankService.getInstance();
        try {
            bankService.createCustomer("Omar Abdullah Moharam","30212121700915");
        } catch (DuplicateNationalIdException e) {
            System.out.println("Error: " + e.getMessage());
        }catch (Exception e)
        {
            System.out.println("Unexpected Error: " + e.getMessage());
        }

        for (var i: bankService.getCustomers() ) {
            System.out.println("Customer Name: " + i.getName());
            System.out.println("Customer National ID: " + i.getNationalId());
        }
        do {
            System.out.println("----------------------------------");
            System.out.println("1. Create Customer");
            System.out.println("2. Add Account");
            System.out.println("3. Show Customers");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1" -> {
                    System.out.print("Enter Customer Name: ");
                    String name = in.nextLine();
                    System.out.print("Enter Customer National ID: ");
                    String nationalId = in.nextLine();
                    try {
                        bankService.createCustomer(name, nationalId);
                        System.out.println("Customer created successfully.");
                    } catch (DuplicateNationalIdException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Unexpected Error: " + e.getMessage());
                    }
                }
                case "2" -> {
                    System.out.print("Enter Account Type (1 for Savings, 2 for Current): ");
                    String accountType = in.nextLine();
                    System.out.print("Enter Customer National ID: ");
                    String nationalId = in.nextLine();
                    Customer customer = bankService.getCustomers().stream()
                            .filter(c -> c.getNationalId().equals(nationalId))
                            .findFirst()
                            .orElse(null);
                    if (customer == null) {
                        System.out.println("Customer not found.");
                        break;
                    }
                    String accountNumber = IdGenerator.generateAccountNumber();
                    System.out.println("Generated Account Number: " + accountNumber);

                    try {
                        System.out.println("Enter Account type (1 for Savings, 2 for Current): ");
                        accountType = in.nextLine();
                        switch (accountType) {
                            case "1" -> {
                                SavingsAccount savingsAccount = new SavingsAccount(accountNumber, customer);
                                bankService.openAccount(savingsAccount);
                                System.out.println("Savings Account created successfully.");
                            }
                            case "2" -> {
                                System.out.print("Enter Overdraft Limit: ");
                                double overdraftLimit = Double.parseDouble(in.nextLine());
                                CurrentAccount currentAccount = new CurrentAccount(accountNumber, customer, overdraftLimit);
                                bankService.openAccount(currentAccount);
                                System.out.println("Current Account created successfully.");
                            }
                            default -> System.out.println("Invalid account type.");
                        }
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case "3" -> {
                    System.out.println("----- Customers List -----");
                    for (var customer : bankService.getCustomers()) {
                        System.out.println("Customer Name: " + customer.getName());
                        System.out.println("Customer National ID: " + customer.getNationalId());
                        System.out.println("--------------------------");
                    }
                }
                case "4" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }while (true);


//         UUID systemId =  UUID.randomUUID();
//         String name= "Omar Abdullah Moharam";
//         String nationalId="30212121700915";
//         String emailAddress="omarmoharam790@gmail.com";
//         String phoneNumber="01064385332";
//        UUID customerId = IdGenerator.generateCustomerId();
//        Customer customer = new Customer(customerId, name, nationalId, emailAddress, phoneNumber);
//
////        SavingsAccount fb = new SavingsAccount(
////                "1234567891011456",
////                    customer
////        );
//        CurrentAccount fb = new CurrentAccount(
//                "1234567891011456",
//                customer,
//                500
//        );
////        fb.deposit(1000);
//
////        FinanceBank fb = new FinanceBank();
//        String option ;
//        do{
//            System.out.println("Welcome to the Finance Bank ^_^");
//            System.out.println("Please enter your choice");
//            System.out.println("1. Deposit");
//            System.out.println("2. Withdraw");
//            System.out.println("3. Balance");
//            System.out.println("4. Exit");
//
//            option = in.next();
//            switch (option){
//                case "1":
//                {
//                    System.out.println("Please enter the amount you want to deposit");
//                    double amount = in.nextDouble();
//                    try{
//                        fb.deposit(amount);
//                        System.out.println("Deposited : " + customFormatter(amount));
//                        System.out.println("Balance after deposit: "+fb.getBalance()+'\n');
//                    }catch (com.omar.bank.exception.InvalidAmountException e){
//                        System.out.println(e.getMessage());
//                    }
//                    break;
//                }
//                case "2":
//                    {
//                    System.out.println("Please enter the amount you want to withdraw");
//                    double amount = in.nextDouble();
//                        try {
//                            fb.withdraw(amount);
//                            System.out.println("Withdrawn: " + amount);
//                            System.out.println("Balance now: " + fb.getBalance());
//                        }catch (com.omar.bank.exception.InvalidAmountException | com.omar.bank.exception.InsufficientAmountException e){
//                            System.out.println(e.getMessage());
//                        }
//                        break;
//                    }
//                    case "3":
//                    {
//                        System.out.println( fb.getBalance());
//                        break;
//                    }
//                    case "4":{
//                        return;
//                    }
//                default:{
//                    System.out.println("Invalid choice. Try again\n");
//                }
//
//            }
//        }while (!(option.equalsIgnoreCase("4")));
//

    }
}