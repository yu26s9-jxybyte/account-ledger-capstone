package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class LedgerScreen {

    public static void showLedger(Scanner scanner, ArrayList<Transaction> transactions) {

        while (true) {
            System.out.println("\nLedger");
            System.out.println("A) All Transactions");
            System.out.println("D) Deposits Only");
            System.out.println("P) Payments Only");
            System.out.println("R) Reports");
            System.out.println("H) Home");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
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
                    Reports.reportsMenu(transactions, scanner);
                    break;

                case "H":
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void showAll(ArrayList<Transaction> transactions) {
        System.out.println("\nAll Transactions");
        printHeader();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            printTransaction(transactions.get(i));
        }
    }

    private static void showDeposits(ArrayList<Transaction> transactions) {
        System.out.println("\nDeposits Only");
        printHeader();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() > 0) {
                printTransaction(t);
            }
        }
    }

    private static void showPayments(ArrayList<Transaction> transactions) {
        System.out.println("\nPayments Only");
        printHeader();

        for (int i = transactions.size() - 1; i >= 0; i--) {
            Transaction t = transactions.get(i);
            if (t.getAmount() < 0) {
                printTransaction(t);
            }
        }
    }

    private static void printHeader() {
        System.out.println("Date | Time | Description | Vendor | Amount");
        System.out.println("----------------------------------------------------");
    }

    private static void printTransaction(Transaction t) {
        System.out.println(
                t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + t.getShop() + " | " + t.getAmount());
    }
}
