package com.pluralsight;

import com.pluralsight.data.TransactionFileReader;
import com.pluralsight.models.Transaction;
import com.pluralsight.ui.Console;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LedgerScreen {
    private final Console console;
    private final TransactionFileReader transactionFileReader;

    public LedgerScreen(Console console, TransactionFileReader transactionFileReader){
        this.console = console;
        this.transactionFileReader = transactionFileReader;
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
                    showAll();
                    break;

                case "D":
                    showDeposits();
                    break;

                case "P":
                    showPayments();
                    break;

                case "R":
                    Reports reports = new Reports(console);
                    reports.reportsMenu(transactionFileReader.getTransactions());
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

    private void showAll() {
        displayTransactionsMenu("\nAll Transactions", transactionFileReader.getTransactions());
    }

    private void showDeposits() {
        ArrayList<Transaction> transactions = transactionFileReader.getTransactions();
        ArrayList<Transaction> deposits = transactions.stream().filter(transaction ->
                        transaction.getAmount() > 0)
                .collect(Collectors.toCollection(ArrayList::new));
        displayTransactionsMenu("\nDeposits Only", deposits);
    }

    private void showPayments() {
        System.out.println("\nPayments Only");
        ArrayList<Transaction> transactions = transactionFileReader.getTransactions();
        ArrayList<Transaction> payments = transactions.stream().filter(transaction ->
                        transaction.getAmount() > 0)
                .collect(Collectors.toCollection(ArrayList::new));
        displayTransactionsMenu("\nPayments Only", payments);
    }

    /**
     * Displays transaction page with a maximum of 10 transactions.
     * @param currentPage the current page number.
     * @param header the title of the page.
     * @param transaction the list of transactions.
     * @return int the page number.
     */
    private int displayPage(int currentPage, String header, ArrayList<Transaction> transaction){
        int previousTransactionsDisplayed = (currentPage * 10) - 10;
        int lastPage = (transaction.size() % 10 == 0 ) ?  transaction.size() / 10 : transaction.size() / 10 + 1;
        if (currentPage < 1){
            System.out.println("You are on the first page.");
            return currentPage + 1;
        }
        else if (currentPage > lastPage ){
            System.out.println("You have reached the last page.");
            return currentPage - 1;
        }

        System.out.printf("%75s %d %n","PAGE", currentPage);
        System.out.printf("%81s %n%-20s %-20s %-45s %-35s %s %n", header, "DATE", "TIME", "DESCRIPTION", "VENDOR", "AMOUNT");

        System.out.println("=".repeat(145));
        for (int i = previousTransactionsDisplayed; i< transaction.size() && i < previousTransactionsDisplayed + 10 ; i++){
            System.out.println(transaction.get(i));
        }
        return currentPage;
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

    /**
     * Displays transactions to user.
     * @param transaction the transactions being displayed.
     */
    private void displayTransactionsMenu(String header, ArrayList<Transaction> transaction){
        String option;
        int pageNum = 1;

        displayPage(pageNum, header, transaction);
        do{
            System.out.print("=".repeat(51));
            System.out.print(" [P] PREVIOUS PAGE [N] NEXT PAGE [X] EXIT ");
            System.out.println("=".repeat(53));
            option = console.promptForStringOptions("> ","P","N","X");
            switch(option){
                case "P":
                    pageNum--;
                    pageNum = displayPage(pageNum, header, transaction);
                    break;
                case "N":
                    pageNum++;
                    pageNum = displayPage(pageNum, header, transaction);
                    break;
                case "X":
                    break;
            }

        }while(!option.equals("X"));

    }
}
