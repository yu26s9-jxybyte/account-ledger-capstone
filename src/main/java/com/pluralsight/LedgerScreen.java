package com.pluralsight;

import com.pluralsight.models.Transaction;
import com.pluralsight.ui.Console;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
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
            System.out.println("""
                    \nLedger
                    [A] All Transactions
                    [D] Deposits Only
                    [P] Payments Only
                    [R] Reports
                    [C] Custom Search
                    [H] Home
                    """);
            // Added option to choose custom search
            String choice = console.promptForStringOptions("Choose an option: ", "a","d","p","r","h","c");

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
        // functionality once you choose "C"
                case "C":
                    customSearch(transactions);
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
// Custom search method to filter through transactions by any attribute
    public void customSearch(ArrayList<Transaction> ledger){
        String startDate = console.promptForString("What is the start date? (yyyy-mm-dd) ");
        startDate = dateCheck(startDate);
        String endDate = console.promptForString("What is the end date? (yyyy-mm-dd) ");
        endDate = dateCheck(endDate);
        if (!startDate.isBlank() && !endDate.isBlank()){
            while (true){
                if (LocalDate.parse(endDate).isBefore(LocalDate.parse(startDate))) {
                    endDate = console.promptForString("End date can't be before the start date. Please try again. (yyyy-mm-dd) ");
                    endDate = dateCheck(endDate);
                } else {
                    break;
                }
            }
        }
        String description = console.promptForString("What is the description? ");
        String vendor = console.promptForString("What is the vendor? ");
        String amount = console.promptForString("What is the amount? ");
        ArrayList<Transaction> custom = new ArrayList<>(ledger);
        if (!startDate.isBlank()) {
            for (int i = 0; i < custom.size(); i++) {
                if (!custom.get(i).getDate().isAfter(LocalDate.parse(startDate))) {
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }

        if (!endDate.isBlank()){
            for (int i = 0; i < custom.size(); i++){
                if (!custom.get(i).getDate().isBefore(LocalDate.parse(endDate))){
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }

        if (!description.isBlank()){
            for (int i = 0; i < custom.size(); i++){
                if (!custom.get(i).getDescription().equalsIgnoreCase(description)){
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }

        if (!vendor.isBlank()){
            for (int i = 0; i < custom.size(); i++){
                if (!custom.get(i).getVendor().equalsIgnoreCase(vendor)){
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }

        if (!(amount.isBlank())){
            for (int i = 0; i < custom.size(); i++){
                if (!(custom.get(i).getAmount() == Double.parseDouble(amount))){
                    custom.remove(custom.get(i));
                    i--;
                }
            }
        }
        Collections.reverse(custom);
        for ( Transaction transaction : custom){
            printTransaction(transaction);
        }
        if (custom.isEmpty()){
            System.out.println("Looks like there isn't anything that matches your filters. Try broadening your search.");
        }

    }
// Helper method to customSearch to make sure the inputted date is in the proper format
    public static String dateCheck(String stringDate){
        Scanner scanner = new Scanner(System.in);
        if (stringDate.isBlank()){
            return stringDate;
        }
        while (true){
            try {
                LocalDate date = LocalDate.parse(stringDate);
                return stringDate;

            } catch (DateTimeParseException e){
                System.out.println("Date not in correct format, please try again. (yyyy-mm-dd) ");
                stringDate = scanner.nextLine();
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
