package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class TransactionService {

    public static ArrayList<Transaction> loadTransactions() {
        ArrayList<Transaction> transactions = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"));
            String line;

            while ((line = reader.readLine()) != null) {

                // fix: for both comma and pipe formats
                String[] parts = line.contains(",") ? line.split(",") : line.split("\\|");

                if (parts.length < 5) {
                    continue;
                }

                String date = parts[0];
                String time = parts[1];
                String description = parts[2];
                String shop = parts[3];
                double amount = Double.parseDouble(parts[4]);

                Transaction t = new Transaction(date, time, description, shop, amount);
                transactions.add(t);
            }

            reader.close();
        } catch (Exception e) {
            System.out.println("Could not load all transactions.");
        }

        return transactions;
    }

    public static void saveTransaction(Transaction t) {
        try {
            FileWriter writer = new FileWriter("transactions.csv", true);

            // Save in pipe format (matches your newest entries)
            writer.write(
                    t.getDate() + "|" +
                            t.getTime() + "|" +
                            t.getDescription() + "|" +
                            t.getShop() + "|" +
                            t.getAmount() + "\n"
            );

            writer.close();
        } catch (Exception e) {
            System.out.println("Could not save transaction.");
        }
    }
}

