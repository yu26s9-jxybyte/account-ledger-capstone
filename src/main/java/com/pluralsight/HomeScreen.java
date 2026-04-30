package com.pluralsight;

import java.time.LocalDateTime;
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
    //lets the user press Enter to use a default value
    private static String promptOrDefault(Scanner scanner, String message, String defaultValue) {
        System.out.print(message);
        String input = scanner.nextLine().trim();

        // if user presses enter, return the default value
        if (input.isEmpty()) {
            return defaultValue;
        }

        return input; // if not, return what they typed
    }


    private static void addDeposit(Scanner scanner, ArrayList<Transaction> transactions) {

        // grabs the system's current date and time
        LocalDateTime now = LocalDateTime.now();
        String defaultDate = now.toLocalDate().toString();
        String defaultTime = now.toLocalTime().withNano(0).toString();

        // ask user for date, but allow enter to auto-fill
        String date = promptOrDefault(scanner, "Date (YYYY-MM-DD) [Press Enter for " + defaultDate + "]: ", defaultDate);

        // Ask user for time, but allow enter to auto-fill
        String time = promptOrDefault(
                scanner, "Time (HH:MM:SS) [Press Enter for " + defaultTime + "]: ", defaultTime);

        // These have to be typed by the user
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String shop = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        // transaction object
        Transaction t = new Transaction(date, time, description, shop, amount);

        // saves it
        TransactionService.saveTransaction(t);
        transactions.add(t);

        System.out.println("Deposit added");
    }

    private static void addPayment(Scanner scanner, ArrayList<Transaction> transactions){
        LocalDateTime now = LocalDateTime.now();
        String defaultDate = now.toLocalDate().toString();
        String defaultTime = now.toLocalTime().withNano(0).toString();

        String date = promptOrDefault(scanner, "Date (YYYY-MM-DD) [Enter for " + defaultDate + "]: ", defaultDate);
        String time = promptOrDefault(scanner, "Time (HH:MM:SS) [Enter for " + defaultTime + "]: ", defaultTime);

        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        System.out.print("Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());
        amount = -Math.abs(amount);

        Transaction t = new Transaction(date, time, description, vendor, amount);

        TransactionService.saveTransaction(t);
        transactions.add(t);

        System.out.println("Payment added");
    }

}
