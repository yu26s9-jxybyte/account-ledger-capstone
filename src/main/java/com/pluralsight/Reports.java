package com.pluralsight;

import com.pluralsight.models.Transaction;
import com.pluralsight.ui.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Reports {

    // month to date
    private final Console console;
    public Reports(Console console){
        this.console = console;
    }
    public void monthToDate(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();

        for (Transaction t : transactions) {
            LocalDateTime localDateTime = t.getDateTime();
            LocalDate date = localDateTime.toLocalDate();

            if (date.getMonth() == today.getMonth() &&
                    date.getYear() == today.getYear()) {
                printTransaction(t);
            }
        }
    }

    // previous month
    public void previousMonth(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);

        for (Transaction t : transactions) {
            LocalDateTime localDateTime = t.getDateTime();
            LocalDate date = localDateTime.toLocalDate();

            if (date.getMonth() == lastMonth.getMonth() &&
                    date.getYear() == lastMonth.getYear()) {
                printTransaction(t);
            }
        }
    }

    // year to date
    public void yearToDate(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();

        for (Transaction t : transactions) {
            LocalDateTime localDateTime = t.getDateTime();
            LocalDate date = localDateTime.toLocalDate();

            if (date.getYear() == today.getYear()) {
                printTransaction(t);
            }
        }
    }

    // previous year
    public void previousYear(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        int lastYear = today.getYear() - 1;

        for (Transaction t : transactions) {
            LocalDateTime localDateTime = t.getDateTime();
            LocalDate date = localDateTime.toLocalDate();

            if (date.getYear() == lastYear) {
                printTransaction(t);
            }
        }
    }

    // search by shop
    public void searchByShop(ArrayList<Transaction> transactions, String vendor) {
        for (Transaction t : transactions) {
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                printTransaction(t);
            }
        }
    }

    // reports menu this is what LedgerScreen calls
    public void reportsMenu(ArrayList<Transaction> transactions) {

        while (true) {
            System.out.println("\nReports");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Shop");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            int choice = console.promptForInt("Choose an option: ", 0,5);

            switch (choice) {
                case 1:
                    monthToDate(transactions);
                    break;

                case 2:
                    previousMonth(transactions);
                    break;

                case 3:
                    yearToDate(transactions);
                    break;

                case 4:
                    previousYear(transactions);
                    break;

                case 5:
                    String shop = console.promptForString("Enter vendor name: ");
                    searchByShop(transactions, shop);
                    break;

                case 0:
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // helper method to print a transaction
    private void printTransaction(Transaction t) {
        System.out.println(
                t.getDate() + " | " +
                        t.getTime() + " | " +
                        t.getDescription() + " | " +
                        t.getVendor() + " | " +
                        t.getAmount()
        );
    }
}
