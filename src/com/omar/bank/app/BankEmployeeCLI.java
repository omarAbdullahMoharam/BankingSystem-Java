package com.omar.bank.app;
import com.omar.bank.exception.*;
import com.omar.bank.model.*;
import com.omar.bank.service.BankService;
import com.omar.bank.util.IdGenerator;
import com.omar.bank.util.NationalIdValidator;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import static com.omar.bank.util.NumberFormatter.timeFormatter;

public class BankEmployeeCLI {
    private static final BankService bankService = BankService.getInstance();
//TODO: Change DataTypte from Double to BigDecimal for monetary values everywhere in the project
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        // Menu Options:
            // 1 - Create Customer
            // 2 - Add Account
            // 3 - Show Customers
            // 4 - Show Accounts By National ID
            // 5 - Deposit
            // 6 - Withdraw
            // 7 - Transaction History
            // 8 - Exit


        while (true) {
            printMenu();
            System.out.print("Enter your choice: ");
            String choice = in.nextLine().trim();

            switch (choice) {
                case "1" -> handleCreateCustomer(in);
                case "2" -> handleAddAccount(in);
                case "3" -> handleShowCustomers();
                case "4" -> handleShowAccountsByNationalId(in);
                case "5" -> handleDeposit(in);
                case "6" -> handleWithdraw(in);
                case "7" -> handleTransactionHistory(in);
                case "8" -> {
                    handleExit();
                    return;
                }
                default -> {
                    System.out.println();
                    System.out.println("[!] Invalid choice. Please try again.");
                    System.out.println();
                }
            }
        }
}

// will be in the user ATM interface not in the bank service
    /**
     * ⚠️ NOTE:
     * This transfer operation simulates a customer self-service transaction.
     * In a real-world banking system, such functionality would belong to
     * a customer-facing channel (ATM, Mobile App),
     * NOT to the bank employee back-office system.

     * This method is kept here for educational and demonstration purposes only.
     */

    private static void handleTransfer(Scanner in) {
        BankService bankService = BankService.getInstance();

        try {
            System.out.print("Enter FROM account number: ");
            String fromAccount = in.nextLine().trim();

            System.out.print("Enter TO account number: ");
            String toAccount = in.nextLine().trim();

            System.out.print("Enter transfer amount: ");
            double amount = Double.parseDouble(in.nextLine().trim());

            bankService.transferFunds(fromAccount, toAccount, amount);

            System.out.println();
            System.out.println("✓ Transfer completed successfully");
            System.out.println();

        } catch (InvalidAccountException e) {
            System.out.println("[!] Invalid amount: " + e.getMessage());

        }catch (InvalidNationalIdException e) {
            System.out.println("[!] Error: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("[!] Unexpected error: " + e.getMessage());
        }
    }

    private static Account chooseAccountFromCustomer(Scanner in, Customer customer) {
        var accounts = customer.getAccounts();

        if (accounts.isEmpty()) {
            System.out.println("[!] Customer has no accounts.");
            return null;
        }

        System.out.println();
        System.out.println("Customer Accounts:");
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            System.out.printf(
                    "%s | %s%n",
                    i + 1,
//                    acc.getAccountNumber(),
                    acc.getAccountType()
            );
        }

        System.out.print("Choose account number: ");
        int choice = Integer.parseInt(in.nextLine());

        if (choice < 1 || choice > accounts.size()) {
            System.out.println("[!] Invalid selection.");
            return null;
        }

        return accounts.get(choice - 1);
    }


    private static void printMenu() {
        System.out.println("----------------------------------------");
        System.out.println("         Finance Bank - Main Menu       ");
        System.out.println("----------------------------------------");
        System.out.println("1. Create Customer");
        System.out.println("2. Add Account");
        System.out.println("3. Show Customers");
        System.out.println("4. Show Accounts By National ID");
        System.out.println("5. Deposit");
        System.out.println("6. Withdraw");
        System.out.println("7. Show Transaction History");
        System.out.println("8. Exit");
        System.out.println("----------------------------------------");
    }
    private static void handleTransactionHistory(Scanner in) {
        System.out.println();
        System.out.print("Enter Customer National ID: ");
        String nationalId = in.nextLine().trim();

        Customer customer = bankService.findCustomerByNationalId(nationalId);
        if (customer == null) {
            System.out.println("[!] Customer not found.");
            return;
        }

        Account account = chooseAccountFromCustomer(in, customer);
        if (account == null) return;

        var transactions = account.getTransactions();

        System.out.println();
        System.out.println("===== Transaction History =====");
//        System.out.printf("Account Number: %s%n", account.getAccountNumber());
        System.out.printf("Account Type  : %s%n", account.getAccountType().label());
        System.out.println("--------------------------------");

        if (transactions.isEmpty()) {
            System.out.println("No transactions found for this account.");
        } else {
            System.out.println("(Most recent transactions first)");
            transactions.stream()
                    .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                    .forEach(t -> {
                        System.out.printf(
                                "%s | Amount: %.2f | Balance After: %.2f | %s | %s%n",
                                t.getType(),
                                t.getAmount(),
                                t.getBalanceAfter(),
                                timeFormatter(t.getTimestamp()),
                                t.getTransactionId()
                        );
                    });

        }

        System.out.println("================================");
        System.out.println();
    }



    private static void handleDeposit(Scanner in) {
        System.out.println();
        System.out.print("Enter Customer National ID: ");
        String nationalId = in.nextLine().trim();

        Customer customer = bankService.findCustomerByNationalId(nationalId);
        if (customer == null) {
            System.out.println("[!] Customer not found.");
            return;
        }

        Account account = chooseAccountFromCustomer(in, customer);
        if (account == null) return;

        System.out.print("Enter deposit amount: ");
        double amount = Double.parseDouble(in.nextLine());

        try {
            account.deposit(amount);
            System.out.println("✓ Deposit successful");
            System.out.printf("New Balance: %.2f%n", account.getBalance());
        } catch (InvalidAmountException e) {
            System.out.println("[!] " + e.getMessage());
        }
    }

    private static void handleWithdraw(Scanner in) {
        System.out.print("\nEnter Customer National ID: ");
        String nationalId = in.nextLine().trim();

        Customer customer = bankService.findCustomerByNationalId(nationalId);
        if (customer == null) {
            System.out.println("[!] Customer not found.");
            return;
        }

        Account account = chooseAccountFromCustomer(in, customer);
        if (account == null) return;

        System.out.print("Enter withdrawal amount: ");
        double amount = Double.parseDouble(in.nextLine());

        try {
            account.withdraw(amount);
            System.out.println("✓ Withdrawal successful");
            System.out.printf("New Balance: %.2f%n", account.getBalance());
        } catch (InvalidAmountException | InsufficientAmountException e) {
            System.out.println("[!] " + e.getMessage());
        }
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

    private static void handleShowAccountsByNationalId(Scanner in) {
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