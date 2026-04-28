package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;

public class HomeScreen {
    public static void main(String[] args){
    Scanner scanner = new Scanner(System.in);

    ArrayList<Transaction> transactions = TransactionService.loadTransactions();

    while (true){
        System.out.println("Home Screen");
        System.out.println("Choose an option: ");
        System.out.println("D) Add a deposit");
        System.out.println("P) Make a payment");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");

        String choice = scanner.nextLine().trim().toUpperCase();

        switch(choice){
            case "D":
                addDeposit(scanner, transactions);
                break;

            case "P":
                addPayment(scanner, transactions);
                break;

            case "L":
                LedgerScreen.showLedger(scanner, transactions);
                break;

            case "X":
                System.out.println("Goodbye!");
                return;

            default:
                System.out.println("Invalid choice. Please select valid option.");
        }
    }
  }
  private static void addDeposit(Scanner scanner, ArrayList<Transaction> transactions){
        System.out.print("Date (YYYY-MM-DD):");
        String date = scanner.nextLine();

        System.out.print("Time (HH:MM:SS)");
        String time = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Shop: ");
        String shop = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Transaction t = new Transaction(date, time, description, shop, amount);

        TransactionService.saveTransaction(t);
        transactions.add(t);

        System.out.println("Deposit added");
  }
  private static void addPayment(Scanner scanner, ArrayList<Transaction> transactions){
        System.out.print("Date (YYYY-MM-DD):" );
        String date = scanner.nextLine();

        System.out.print("Time (HH:MM:SS):" );
        String time = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        amount = -Math.abs(amount); //

        Transaction t = new Transaction(date, time, description, vendor, amount);

        TransactionService.saveTransaction(t);
        transactions.add(t);

        System.out.println("Payment added");
  }
}
