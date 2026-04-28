package com.pluralsight;

import java.time.LocalDate;
import java.util.ArrayList;

//month to date section
public class Reports {
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

    // the previous month
    public static void previousMonth(ArrayList<Transaction> transactions) {
        LocalDate today = LocalDate.now();
        LocalDate lastMonth = today.minusMonths(1);

        for (Transaction t : transactions) {
            LocalDate date = LocalDate.parse(t.getDate());

            if (date.getMonth() == lastMonth.getMonth() &&
            date.getYear() == lastMonth.getYear()){
                printTransaction(t);
            }
        }

    }
    //year to date section
    public static void yearToDate(ArrayList<Transaction> transactions){
        LocalDate today = LocalDate.now();

        for (Transaction t : transactions){
            LocalDate date = LocalDate.parse(t.getDate());

            if (date.getYear() == today.getYear()){
                printTransaction(t);
            }

        }
    }
    // the previous year
    public static void previousYear(ArrayList<Transaction> transactions){
        LocalDate today = LocalDate.now();
        int lastYear = today.getYear() - 1;

        for (Transaction t : transactions){
            LocalDate date = LocalDate.parse(t.getDate());

            if (date.getYear() == lastYear){
                printTransaction(t);
            }

        }
    }
    //searching by shop
    public static void searchByShop(ArrayList<Transaction> transactions){
        for (Transaction t : transactions){
            String shopName;
            if (t.getShop().equalsIgnoreCase(shopName)){
                printTransaction(t);
            }
        }
    }
    // new helper method to print transactions
    private static void printTransaction(Transaction t){
        System.out.println( t.getDate() + " | " + t.getTime() + " | " + t.getDescription() + " | " + t.getShop() + " | " + t.getAmount());

    }
}