package com.pluralsight;

import com.pluralsight.models.Transaction;
import com.pluralsight.ui.Console;

import java.util.ArrayList;
import java.util.Scanner;

public class LedgerScreen {
    private final Console console;
    private final ArrayList<Transaction> transactions;
    public LedgerScreen(Console console, ArrayList<Transaction> transactions){
        this.console = console;
        this.transactions = transactions;
    }


    public void showLedger() {
        while (true) {
            System.out.println("\nLedger");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");

            String choice = console.promptForStringOptions("Choose an option: ", "a","d","p","r","h");

            switch (choice.toUpperCase()) {
                case "A":
                    showAll(transactions);
                    break;

                case "D":
                    showDeposits(transactions);
                    break;

                case "P":
                    showPayments(transactions);
                    break;

                case "R":
                    Reports reports = new Reports(console);
                    reports.reportsMenu(transactions);
                    break;

                case "H":
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void showAll(ArrayList<Transaction> transactions) {
        System.out.println("\nAll Transactions");
        printHeader();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            printTransaction(transactions.get(i));
        }
    }

    private void showDeposits(ArrayList<Transaction> transactions) {
        System.out.println("\nDeposits Only");
        printHeader();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() > 0) {
                printTransaction(t);
            }
        }
    }

    private void showPayments(ArrayList<Transaction> transactions) {
        System.out.println("\nPayments Only");
        printHeader();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() < 0) {
                printTransaction(t);
            }
        }
    }

    private void printHeader() {
        System.out.println("Date | Time | Description | Vendor | Amount");
        System.out.println("----------------------------------------------------");
    }

    private void printTransaction(Transaction t) {
        System.out.println(
                t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + t.getVendor() + " | " + t.getAmount());
    }
}
