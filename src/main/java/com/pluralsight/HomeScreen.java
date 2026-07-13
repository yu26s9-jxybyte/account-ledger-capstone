package com.pluralsight;

import com.pluralsight.data.TransactionFileReader;
import com.pluralsight.models.Transaction;
import com.pluralsight.ui.Console;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class HomeScreen {
    private final Console console;
    private final TransactionFileReader transactionFileReader;
    //ArrayList<Transaction> transactions = TransactionService.loadTransactions();
    public HomeScreen(Console console, TransactionFileReader transactionFileReader) {
        this.console = console;
        this.transactionFileReader = transactionFileReader;
    }

    public void mainDisplay(){

    while (true){
        String choice = console.promptForString("""
                Home Screen
                [D] Add a deposit
                [P] Make a Payment
                [L] Ledger
                [X] Exit
                Choose an option:
                """);

        switch(choice){
            case "D":
                addDeposit(transactions);
                break;
            case "P":
                addPayment(transactions);
                break;
           case "L":
                LedgerScreen.showLedger(transactions);
                break;
            case "X":
                System.out.println("Goodbye!");
                return;
            default:
                System.out.println("Invalid choice. Please select valid option.");
        }
    }
  }
    //lets the user press Enter to use a default value



    private void addDeposit(ArrayList<Transaction> transactions) {

        // grabs the system's current date and time
        LocalDateTime now = LocalDateTime.now();
        String defaultDate = now.toLocalDate().toString();
        String defaultTime = now.toLocalTime().withNano(0).toString();

        // ask user for date, but allow enter to auto-fill
        String date = console.promptOrDefault( "Date (YYYY-MM-DD) [Press Enter for " + defaultDate + "]: ", defaultDate);

        // Ask user for time, but allow enter to auto-fill
        String time = console.promptOrDefault(
                 "Time (HH:MM:SS) [Press Enter for " + defaultTime + "]: ", defaultTime);

        // These have to be typed by the user
        String description = console.promptForString("Description: ");


        String shop = console.promptForString("Vendor: ");

        System.out.print("Amount: ");
        double amount = Double.parseDouble(console.nextLine());

        // transaction object
        Transaction t = new Transaction(date, time, description, shop, amount);

        // saves it
        TransactionFileReader.saveTransaction(t);
        transactions.add(t);

        System.out.println("Deposit added");
    }

    private void addPayment(ArrayList<Transaction> transactions){
        LocalDateTime now = LocalDateTime.now();
        String defaultDate = now.toLocalDate().toString();
        String defaultTime = now.toLocalTime().withNano(0).toString();

        String date = console.promptOrDefault("Date (YYYY-MM-DD) [Enter for " + defaultDate + "]: ", defaultDate);
        String time = console.promptOrDefault("Time (HH:MM:SS) [Enter for " + defaultTime + "]: ", defaultTime);

        String description = console.promptForString("Description: ");

        String vendor = console.promptForString("Vendor: ");

        double amount = console.promptForDouble("Amount: ");
        amount = -Math.abs(amount);

        Transaction t = new Transaction(date, time, description, vendor, amount);

        TransactionFileReader.saveTransaction(t);
        transactions.add(t);

        System.out.println("Payment added");
    }

}
