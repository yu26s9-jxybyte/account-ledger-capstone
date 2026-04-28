package com.pluralsight;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;

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

}
