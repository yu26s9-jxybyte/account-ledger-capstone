package com.pluralsight.data;

import com.pluralsight.models.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class TransactionFileReader {
    private final String transactionFile;
    private final ArrayList<Transaction> transactions = new ArrayList<>();

    public TransactionFileReader(String fileName){
        transactionFile = fileName;
        loadTransactions();
    }

    private void loadTransactions() {
        transactions.clear();
        try(
                BufferedReader bufferedReader = new BufferedReader(new FileReader(transactionFile))
                ){
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.isBlank()){
                    continue; //avoids blank line errors.
                }
                // fix: for both comma and pipe formats
                transactions.add(createTransactionFromString(line));
            }
        } catch (IOException ex) {
            System.out.println("Could not load all transactions.");
        }

    }

    private Transaction createTransactionFromString(String line){
        String[] parts = line.split("\\|");

        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime time = LocalTime.parse(parts[1]);
        LocalDateTime dateTime = LocalDateTime.of(date,time); // Combines the date and time objects to create a DateTime object.
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);
        return new Transaction(dateTime, description, vendor, amount);
    }

    public void saveTransaction(Transaction t) {
        try {
            FileWriter writer = new FileWriter("transactions.csv", true);
            // Save in pipe format (matches your newest entries)
            writer.write(
                    t.getDate() + "|" +
                            t.getTime() + "|" +
                            t.getDescription() + "|" +
                            t.getVendor() + "|" +
                            t.getAmount() + "\n"
            );

            writer.close();
        } catch (IOException ex) {
            System.out.println("Could not save transaction.");
        }
        loadTransactions();
    }

    public ArrayList<Transaction> getTransactions(){
        return transactions;
    }
}

