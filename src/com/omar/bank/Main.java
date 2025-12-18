package com.omar.bank;
import com.omar.bank.exception.*;
import com.omar.bank.model.Account;
import com.omar.bank.model.CurrentAccount;
import com.omar.bank.model.Customer;
import com.omar.bank.model.SavingsAccount;
import com.omar.bank.service.BankService;
import com.omar.bank.util.IdGenerator;
import com.omar.bank.util.NationalIdValidator;

import java.util.List;
import java.util.Scanner;

import static com.omar.bank.util.NumberFormatter.timeFormatter;

public class Main {
    private static final BankService bankService = BankService.getInstance();
//TODO: Change DataTypte from Double to BigDecimal for monetary values everywhere in the project
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

//        while (true) {
//            printMenu();
//            System.out.print("Enter your choice: ");
//            String choice = in.nextLine().trim();
//
//            switch (choice) {
//                case "1" -> handleCreateCustomer(in);
//                case "2" -> handleAddAccount(in);
//                case "3" -> handleShowCustomers();
//                case "4" -> handleGetAccountsByNationalId(in);
//                case "5" -> {
//                    handleExit();
//                    return;
//                }
//                default -> {
//                    System.out.println();
//                    System.out.println("[!] Invalid choice. Please try again.");
//                    System.out.println();
//                }
//            }
//        }

        SavingsAccount fb = new SavingsAccount(
                "1001000100000001",
                new Customer("Omar Abdullah Moharam","30212121700915")
        );
        try {
            fb.deposit(1000);
            fb.deposit(200);
            fb.getTransactions().forEach(t -> {
                System.out.println(
                        t.getType() + " | " +
                                t.getAmount() + " | " +
                                t.getBalanceAfter()+
                                " | " + timeFormatter(t.getTimestamp())+
                                " | " + t.getTransactionId()
                );
            });
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        }

        SavingsAccount sf = new SavingsAccount(
                "1001000100000002",
                new Customer("Ahmed Ali","29805231567890")
        );

        try {
            sf.deposit(5000);
//                sf.getTransactions().forEach(t -> {
//                    System.out.println(
//                            t.getType() + " | " + " deposit amount" +
//                                    t.getAmount() + " | " + " After Balance: "+
//                                    t.getBalanceAfter()+" Time"+
//                                    " | " + t.getTimestamp()+" Transaction ID "+
//                                    " | " + t.getTransactionId()
//                    );
//                });
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        }
        try {
            sf.withdraw(1000);
//                System.out.println("withdrawn : " + customFormatter(amount));
//                System.out.println("Fee : " + customFormatter(feeAmount));
//                System.out.println("Total out: "+customFormatter(amount) + " Will be withdrawn\n");
//                sf.getTransactions().forEach(t -> {
//                    System.out.println(
//                            t.getType() + " | " + " withdraw amount" +
//                                    t.getAmount() + " | " + " After Balance: "+
//                                    t.getBalanceAfter()+ " Time"+
//                                    " | " + t.getTimestamp()+ " Transaction ID "+
//                                    " | " + t.getTransactionId()
//                    );
//                });
        } catch (InvalidAmountException | InsufficientAmountException e) {
            System.out.println(e.getMessage());

        }
        System.out.println("Transactions for Savings Account:");
        sf.getTransactions().forEach(t -> {
            System.out.println(
                    t.getType() + " | " +
                            t.getAmount() + " | " + " After Balance: "+
                            t.getBalanceAfter()+ " Time"+
                            " | " +timeFormatter( t.getTimestamp())+ " Transaction ID "+
                            " | " + t.getTransactionId()
            );
        });
        CurrentAccount ca = new CurrentAccount(
                "1001000100000003",
                new Customer("Omar Test", "30001011234567"),
                500
        );

        try {
            ca.deposit(1000);     // balance = 1000
        } catch (InvalidAmountException e) {
            System.out.println(e.getMessage());
        }
        try {
            ca.withdraw(1200);    // balance = -200 (OK)
        } catch (InvalidAmountException|InsufficientAmountException e) {
            System.out.println(e.getMessage());
        }
        try {
            ca.withdraw(200);     // balance = -400 (OK)
        }catch (InvalidAmountException|InsufficientAmountException e) {
            System.out.println(e.getMessage());
        }

        try {
            ca.withdraw(200); // would be -600 ❌
        } catch (InsufficientAmountException | InvalidAmountException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Transactions for Current Account:");
        ca.getTransactions().forEach(t -> {
            System.out.println(
                    t.getType() + " | " +
                            t.getAmount() + " | " + " After Balance: "+
                            t.getBalanceAfter()+ " Time"+
                            " | " + timeFormatter(t.getTimestamp())+ " Transaction ID "+
                            " | " + t.getTransactionId()
            );
        });
    }

        private static void printMenu() {
        System.out.println("----------------------------------------");
        System.out.println("         Finance Bank - Main Menu       ");
        System.out.println("----------------------------------------");
        System.out.println("1. Create Customer");
        System.out.println("2. Add Account");
        System.out.println("3. Show Customers");
        System.out.println("4. Get Accounts by Customer National ID");
        System.out.println("5. Exit");
        System.out.println("----------------------------------------");
    }

    private static void handleCreateCustomer(Scanner in) {
        System.out.println();
        System.out.print("Enter Customer Name: ");
        String name = in.nextLine().trim();

        System.out.print("Enter Customer National ID: ");
        String nationalId = in.nextLine().trim();

        try {
            // validate national ID first
            NationalIdValidator.validateNationalId(nationalId);
            Customer c = bankService.createCustomer(name, nationalId);

            System.out.println();
            System.out.println("===== Customer Created Successfully =====");
            System.out.printf("System ID     : %s%n", c.getSystemId());
            System.out.printf("Customer Name : %s%n", c.getName());
            System.out.printf("National ID   : %s%n", c.getNationalId());
            System.out.println("=========================================");
            System.out.println();

        } catch (Exception e) {
            System.out.println();
            System.out.println("[Error] " + e.getMessage());
            System.out.println();
        }
    }

    private static void handleAddAccount(Scanner in) {
        System.out.println();
        System.out.print("Enter Account Type (1 for Savings, 2 for Current): ");
        String accountType = in.nextLine().trim();

        System.out.print("Enter Customer National ID: ");
        String nationalId = in.nextLine().trim();
        Customer customer = bankService.findCustomerByNationalId(nationalId);
        if (customer == null) {
            System.out.println();
            System.out.println("[!] Customer not found. Register customer first.");
            System.out.println();
            return;
        }

        String accountNumber = IdGenerator.generateAccountNumber();
        System.out.println();
        System.out.println("Generated Account Number : " + accountNumber);

        switch (accountType) {
            case "1" -> createSavingsAccount(accountNumber, customer);
            case "2" -> createCurrentAccount(in, accountNumber, customer);
            default -> System.out.println("[!] Invalid account type. Choose 1 or 2.");
        }
    }

    private static void createSavingsAccount(String accountNumber, Customer customer) {
        try {
            SavingsAccount savingsAccount = new SavingsAccount(accountNumber, customer);
            bankService.openAccount(savingsAccount);

            System.out.println();
            System.out.println("----- Savings Account Created -----");
            System.out.printf("Customer : %s%n", customer.getName());
            System.out.printf("Account  : %s%n", savingsAccount.getAccountNumber());
            System.out.println("-----------------------------------");
            System.out.println();
        } catch (InvalidAccountException e) {
            System.out.println();
            System.out.println("[Invalid Account] " + e.getMessage());
            System.out.println();
        } catch (DuplicateAccountException e) {
            System.out.println();
            System.out.println("[Duplicate Account] " + e.getMessage());
            System.out.println();
        } catch (Exception e) {
            System.out.println();
            System.out.println("[Error] " + e.getMessage());
            System.out.println();
        }
    }

    private static void createCurrentAccount(Scanner in, String accountNumber, Customer customer) {
        System.out.print("Enter Overdraft Limit: ");
        String overdraftInput = in.nextLine().trim();
        double overdraftLimit;
        try {
            overdraftLimit = Double.parseDouble(overdraftInput);
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("[!] Invalid overdraft amount. Operation cancelled.");
            System.out.println();
            return;
        }

        try {
            CurrentAccount currentAccount = new CurrentAccount(accountNumber, customer, overdraftLimit);
            bankService.openAccount(currentAccount);

            System.out.println();
            System.out.println("----- Current Account Created -----");
            System.out.printf("Customer       : %s%n", customer.getName());
            System.out.printf("Account        : %s%n", currentAccount.getAccountNumber());
            System.out.printf("Overdraft Limit: %s%n", overdraftLimit);
            System.out.println("-----------------------------------");
            System.out.println();
        } catch (InvalidAccountException e) {
            System.out.println();
            System.out.println("[Invalid Account] " + e.getMessage());
            System.out.println();
        } catch (DuplicateAccountException e) {
            System.out.println();
            System.out.println("[Duplicate Account] " + e.getMessage());
            System.out.println();
        } catch (Exception e) {
            System.out.println();
            System.out.println("[Error] " + e.getMessage());
            System.out.println();
        }
    }

    private static void handleShowCustomers() {
        System.out.println();
        List<Customer> customers = bankService.getCustomers();

        if (customers.isEmpty()) {
            System.out.println("[!] No customers registered yet.");
            System.out.println();
            return;
        }

        System.out.println("========== Customers List ==========");
        for (Customer customer : customers) {
            System.out.printf("Name        : %s%n", customer.getName());
            System.out.printf("National ID : %s%n", customer.getNationalId());

            var customerAccounts = customer.getAccounts();
            System.out.printf("Accounts    : %d%n", customerAccounts.size());
            if (customerAccounts.isEmpty()) {
                System.out.println("  (No accounts yet)");
            } else {
                System.out.println("  Accounts:");
                for (var account : customerAccounts) {

                    System.out.printf("    - %s | Type: %s%n",
                            account.getAccountNumber(), account.getAccountType().label());
                }
            }
            System.out.println("-----------------------------------");
        }
        System.out.println("====================================");
        System.out.println();
    }

    private static void handleGetAccountsByNationalId(Scanner in) {
        System.out.println();
        System.out.print("Enter Customer National ID to retrieve accounts: ");
        String nationalId = in.nextLine().trim();

        Customer customer = bankService.findCustomerByNationalId(nationalId);
        if (customer == null) {
            System.out.println();
            System.out.println("[!] Customer not found, register first then try again.");
            System.out.println();
            return;
        }

        var accounts = customer.getAccounts();
        System.out.println();
        System.out.printf("Customer: %s | Number of Accounts: %d%n", customer.getName(), accounts.size());
        if (accounts.isEmpty()) {
            System.out.println("  (No accounts found for this customer.)");
        } else {
            for (var account : accounts) {

                System.out.printf(" - Account Number: %s | Type: %s%n", account.getAccountNumber(), account.getAccountType().label());
            }
        }
        System.out.println();
    }

    private static void handleExit() {
        System.out.println();
        System.out.println("Thank you for using Finance Bank. Exiting...");
        System.out.println();
    }
}
//30212121700915


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
//
//    }
//}