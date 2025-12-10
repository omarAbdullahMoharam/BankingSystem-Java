# Banking Employee System—Java OOP

A simple **Banking System** implemented in **Java**, following clean **OOP design principles** and focusing on **robust exception handling**.

This project simulates a **core banking system for bank employees**, allowing staff to manage customers, validate national IDs, and open savings/current accounts using a fully interactive CLI application.

---

## 📂 Project Structure

```text
src/
└── com/omar/bank
    ├── app/
    │   └── Main.java
    │
    ├── exception/
    │   ├── DuplicateNationalIdException.java
    │   ├── DuplicateAccountException.java
    │   ├── InvalidAmountException.java
    │   ├── InsufficientAmountException.java
    │   ├── InvalidAccountException.java
    │   └── InvalidNationalIdException.java
    │
    ├── model/
    │   ├── Account.java
    │   ├── CurrentAccount.java
    │   ├── SavingsAccount.java
    │   ├── Customer.java
    │   └── Person.java
    │
    ├── service/
    │   └── BankService.java
    │
    └── util/
        ├── IdGenerator.java
        ├── NationalIdValidator.java
        └── NumberFormatter.java

```
## ⚙️ Main Features

* **Interactive CLI for bank employees:**
    * `1. Create Customer` — validates national ID and prevents duplicates.
    * `2. Add Account` — creates Savings or Current accounts for an existing customer.
    * `3. Show Customers` — lists customers and their accounts.
    * `4. Exit`
* **Egyptian National ID validation** using `NationalIdValidator`.
* **Automatic account number generation**.
* **Overdraft support** in `CurrentAccount`.
* **Custom exception handling**.
* **Clean separation of concerns** (Models / Services / Utilities / Exceptions).
* **In-memory management** of customers and accounts.

---

## ❗ Custom Exceptions

| Exception | Purpose |
|-----------|---------|
| `DuplicateNationalIdException` | Customer with the same national ID already exists |
| `DuplicateAccountException` | Account number already exists |
| `InvalidNationalIdException` | Invalid Egyptian national ID format |
| `InvalidAmountException` | Deposit/Withdraw amount is zero or negative |
| `InsufficientAmountException` | Balance not sufficient |
| `InvalidAccountException` | Null or invalid account reference |

---

## 🏗️ Core Classes & Responsibilities

### 🔹 Main
Interactive CLI entry point.

### 🔹 BankService (Singleton)
Handles all:
* Customer creation
* Account management
* Validation
* Duplicate checks

### 🔹 Customer / Person
Represent customer identity and personal information.

### 🔹 Account (Abstract Class)
Contains:
* `deposit()`
* `withdraw()`
* `getBalance()`
* shared logic across account types

### 🔹 SavingsAccount
Standard account with no overdraft.

### 🔹 CurrentAccount
Supports overdraft limits.

### 🔹 IdGenerator
Creates:
* Unique account numbers
* Customer IDs

### 🔹 NationalIdValidator
Validates Egyptian national ID:
* Format
* Date
* Governorate code

### 🔹 NumberFormatter
Formats currency and numbers for display.

---

## 🚀 How to Run

### ▶ Option 1: Using an IDE (Recommended)

1. Open project in IntelliJ / Eclipse / VS Code
2. Run:
   ```
   com.omar.bank.app.Main
   ```

### ▶ Option 2: Using Terminal

```bash
javac src/com/omar/bank/app/Main.java
java -cp src com.omar.bank.app.Main
```

---

## 🔍 Example CLI Flow

### Create Customer
```
1 → Enter Name → Enter National ID → Validated → Customer Created
```

### Add Account
```
2 → Select Customer → Choose Savings/Current → Enter overdraft (if current)
```

### Show Customers
Displays:
* Names
* National IDs
* Number of accounts
* Account types and numbers

**Errors are handled with custom exceptions and printed cleanly.**

---

## 🧠 Concepts Demonstrated

* **OOP Design**
* **Encapsulation**
* **Abstraction**
* **Inheritance**
* **Polymorphism**
* **Exception Handling**
* **Validation**
* **Clean architecture and separation of concerns**

---

## 📌 Future Improvements

* Database persistence (H2 / MySQL / SQLite)
* Employee Login System
* Role-based access (Admin / Teller)
* Account statement & transaction history
* Logging (Log4j / SLF4J)
* Unit testing (JUnit)
* GUI or Spring Boot REST API version

---

## 👨‍💻 Author

**Omar Abdullah Moharam**  
GitHub: [https://github.com/omarAbdullahMoharam](https://github.com/omarAbdullahMoharam)

*This project is for educational purposes to practice Java OOP, clean design, and exception handling.*