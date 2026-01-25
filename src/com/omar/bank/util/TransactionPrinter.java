package com.omar.bank.util;

import com.omar.bank.model.Account;

import java.io.PrintWriter;

import static com.omar.bank.util.NumberFormatter.timeFormatter;

public final class TransactionPrinter {
//  disable instantiation of utility class or objects
    private TransactionPrinter() {
        // prevent instantiation
    }

    public static void exportToCsv(Account account, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {

            writer.println(
                    "Type,Amount,Balance After,Timestamp,Transaction ID"
            );

            account.getTransactions().forEach(t ->
                    writer.printf(
                            "%s,%s,%s,%s,%s%n",
                            t.getType(),
                            t.getAmount(),
                            t.getBalanceAfter(),
                            t.getTimestamp(),
                            t.getTransactionId()
                    )
            );

            System.out.println("✓ Exported to " + fileName);

        } catch (Exception e) {
            System.out.println("[!] Export failed: " + e.getMessage());
        }
    }
}
