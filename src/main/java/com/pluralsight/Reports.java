package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Reports {

    // month to date
    public static void monthTODate(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());

            if (date.getMonth() == today.getMonth() &&
                    date.getYear() == today.getYear()) {
                printTransaction(t);
            }
        }
    }

    // previous month
    public static void previousMonth(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());

            if (date.getMonth() == lastMonth.getMonth() &&
                    date.getYear() == lastMonth.getYear()) {
                printTransaction(t);
            }
        }
    }

    // year to date
    public static void yearToDate(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());

            if (date.getYear() == today.getYear()) {
                printTransaction(t);
            }
        }
    }

    // previous year
    public static void previousYear(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        int lastYear = today.getYear() - 1;

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());

            if (date.getYear() == lastYear) {
                printTransaction(t);
            }
        }
    }

    // search by shop
    public static void searchByShop(ArrayList<Transaction> transactions, String shopName) {
        for (Transaction t : transactions) {
            if (t.getShop().equalsIgnoreCase(shopName)) {
                printTransaction(t);
            }
        }
    }

    // reports menu this is what LedgerScreen calls
    public static void reportsMenu(ArrayList<Transaction> transactions, Scanner scanner) {

        while (true) {
            System.out.println("\nReports");
            System.out.println("1) Month To Date");
            System.out.println("2) Previous Month");
            System.out.println("3) Year To Date");
            System.out.println("4) Previous Year");
            System.out.println("5) Search by Shop");
            System.out.println("0) Back");
            System.out.print("Choose an option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    monthTODate(transactions);
                    break;

                case "2":
                    previousMonth(transactions);
                    break;

                case "3":
                    yearToDate(transactions);
                    break;

                case "4":
                    previousYear(transactions);
                    break;

                case "5":
                    System.out.print("Enter shop name: ");
                    String shop = scanner.nextLine().trim();
                    searchByShop(transactions, shop);
                    break;

                case "0":
                    return;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    // helper method to print a transaction
    private static void printTransaction(Transaction t) {
        System.out.println(
                t.getDate() + " | " +
                        t.getTime() + " | " +
                        t.getDescription() + " | " +
                        t.getShop() + " | " +
                        t.getAmount()
        );
    }
}
