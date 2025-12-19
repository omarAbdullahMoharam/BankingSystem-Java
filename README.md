# 🏦 Banking Employee System — Java OOP

A **Banking Employee System** implemented in **Java**, following clean **Object-Oriented Programming (OOP)** principles and focusing on **robust validation and exception handling**.

This project simulates a **bank back-office system for employees**, allowing staff to manage customers, open accounts, perform deposits and withdrawals, and review transaction history through a **console-based (CLI) application**.

> ⚠️ **Important Note**  
> This system represents **employee-assisted banking operations only**.  
> Customer self-service features (such as online transfers) are intentionally out of scope.

---

## 📂 Project Structure

```
src/
└── com/omar/bank/
    ├── app/
    │   └── Main.java
    │
    ├── exception/
    │   ├── DuplicateAccountException.java
    │   ├── DuplicateNationalIdException.java
    │   ├── InsufficientAmountException.java
    │   ├── InvalidAccountException.java
    │   ├── InvalidAmountException.java
    │   └── InvalidNationalIdException.java
    │
    ├── model/
    │   ├── Account.java
    │   ├── AccountType.java
    │   ├── CurrentAccount.java
    │   ├── SavingsAccount.java
    │   ├── Customer.java
    │   ├── Person.java
    │   ├── Transaction.java
    │   └── TransactionType.java
    │
    ├── service/
    │   └── BankService.java
    │
    └── util/
        ├── AccountValidator.java
        ├── IdGenerator.java
        ├── NationalIdValidator.java
        └── NumberFormatter.java
```

---

## ⚙️ Main Features

### 🧑‍💼 Employee Operations (CLI)

- **Create Customer**
    - Validates Egyptian National ID
    - Prevents duplicate customers

- **Add Account**
    - Savings Account
    - Current Account with overdraft support

- **Deposit**
    - Performed by employee after selecting customer and account

- **Withdraw**
    - Supports overdraft rules for current accounts

- **Show Customers**
    - Displays customers and number of accounts

- **Show Accounts by National ID**

- **Show Transaction History**
    - Read-only audit log per account

---

## 🇪🇬 Egyptian National ID Validation

Implemented via `NationalIdValidator`, including:
- Format validation (14 digits)
- Birth date validation
- Governorate code validation

---

## 💳 Account Types

### Savings Account
- No overdraft
- Withdrawals limited to available balance

### Current Account
- Supports overdraft up to a defined limit
- Overdraft usage is validated per withdrawal

---

## 📜 Transactions

Each **deposit** or **withdrawal** creates a `Transaction` record containing:
- Transaction type
- Amount
- Balance after operation
- Timestamp
- Unique transaction ID

Transactions are:
- **Read-only**
- Used for auditing and account history
- Fully valid in an employee banking system

---

## ❗ Custom Exceptions

| Exception | Purpose |
|-----------|---------|
| `DuplicateNationalIdException` | Customer already exists |
| `DuplicateAccountException` | Account number already exists |
| `InvalidNationalIdException` | Invalid Egyptian national ID |
| `InvalidAmountException` | Amount is zero or negative |
| `InsufficientAmountException` | Balance or overdraft exceeded |
| `InvalidAccountException` | Invalid or null account |

---

## 🧠 Architecture Overview

- **Presentation Layer:** CLI (`Main`)
- **Service Layer:** `BankService` (Singleton)
- **Domain Layer:** Accounts, Customers, Transactions
- **Utility Layer:** Validators & Generators

The system emphasizes:
- Encapsulation
- Separation of concerns
- Domain-driven design
- Clean error handling

---

## 🚀 How to Run

### ▶️ Using an IDE

1. Open the project in IntelliJ IDEA / Eclipse / VS Code
2. Run:
   ```
   com.omar.bank.app.Main
   ```

### ▶️ Using Terminal

```bash
javac src/com/omar/bank/app/Main.java
java -cp src com.omar.bank.app.Main
```

---

## 🔍 Example CLI Flow

```
1 → Create Customer
2 → Add Account
5 → Deposit (select customer → select account)
6 → Withdraw
7 → Show Transaction History
```

The employee selects accounts using National ID, not raw account numbers, ensuring better UX and fewer errors.

---

## 🧠 Concepts Demonstrated

- Object-Oriented Programming (OOP)
- Encapsulation & Abstraction
- Inheritance & Polymorphism
- Exception Handling
- Input Validation
- Singleton Pattern
- Clean Architecture
- Banking Domain Modeling

---

## 📌 Future Improvements

- Database persistence (H2 / MySQL / SQLite)
- Employee authentication & roles
- Account statements export
- Logging (SLF4J / Log4j)
- Unit testing (JUnit)
- REST API or GUI version

---

## 👨‍💻 Author

**Omar Abdullah Moharam**  
GitHub: [omarAbdullahMoharam](https://github.com/omarAbdullahMoharam)

> This project was built for educational purposes to practice Java OOP, clean design, and realistic banking system architecture.

---

## 📄 License

This project is open source and available for educational purposes.