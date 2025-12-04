# Banking System - Java OOP & Exceptions

A simple **Banking System** built with **Java**, following clean **OOP design principles** and focusing on **exception handling**.

This project simulates real banking operations such as customers, accounts, withdrawals, deposits, and validation logic using custom exceptions.

---

## 📂 Project Structure
```text
src/
└── com/omar/bank
    ├── app/
    │   └── FinanceBank.java
    │
    ├── exception/
    │   ├── InsufficientAmountException.java
    │   ├── InvalidAccountException.java
    │   └── InvalidAmountException.java
    │
    ├── model/
    │   ├── Account.java
    │   ├── CurrentAccount.java
    │   ├── SavingsAccount.java
    │   ├── Customer.java
    │   └── Person.java
    │
    ├── service/
    │   └── PersonService.java
    │
    └── util/
        └── NumberFormatter.java
```

---

## ⚙️ Features

✅ Account creation (Current / Savings)  
✅ Customer & Person abstraction  
✅ Deposit and Withdraw operations  
✅ Withdraw fee handling  
✅ Overdraft validation  
✅ Input validation  
✅ Clean separation between:
- Models
- Services
- Utilities
- Application logic

✅ Custom exception handling

---

## ❗ Custom Exceptions

The project includes custom exceptions to handle different error scenarios:

### ```InvalidAmountException```
Thrown when a negative or zero amount is used.

### ```InsufficientAmountException```
Thrown when balance is not enough to complete withdrawal.

### ```InvalidAccountException```
Thrown when an invalid or null account is used.

---

## 🏗️ Core Classes

### 🔹 Person
Base class that represents a person in the system.

### 🔹 Customer
Inherits from Person and adds customer-related data.

### 🔹 Account (Abstract / Parent Class)
Represents a bank account with basic operations such as:
- Deposit
- Withdraw
- Get balance

### 🔹 CurrentAccount & SavingsAccount
Different types of accounts extending Account:
- **CurrentAccount** → supports overdraft
- **SavingsAccount** → normal balance restriction

### 🔹 PersonService
Handles operations related to persons and customers.

### 🔹 NumberFormatter
Utility class used to format currency and numbers for display.

### 🔹 FinanceBank (Main Class)
Main entry point of the application.  
Contains the `main()` method to run the system and test banking operations.

---

## 🚀 How to Run

### Option 1: From IDE (Recommended)
1. Open project using IntelliJ / Eclipse / VS Code
2. Navigate to: `com.omar.bank.app.FinanceBank`
3. Right-click → Run

### Option 2: From Terminal
From inside `src` folder:
```bash
javac com/omar/bank/app/FinanceBank.java
java com/omar/bank/app/FinanceBank
```

---

## 🧠 OOP Concepts Applied

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction
- Method Overriding
- Exception Handling
- Clean Package Management

---

## 📌 Future Improvements

🔹 Add file or database persistence  
🔹 Create interactive menu  
🔹 Add login system  
🔹 Add unit testing with JUnit  
🔹 Improve design with interfaces and design patterns

---

## 👨‍💻 Author

**Omar Abdullah Moharam**  
GitHub: [https://github.com/omarAbdullahMoharam](https://github.com/omarAbdullahMoharam)

*This project is for educational purposes to practice core Java concepts, OOP design, and exception handling.*
