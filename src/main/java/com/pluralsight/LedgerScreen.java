package com.pluralsight;

import com.pluralsight.data.TransactionFileReader;
import com.pluralsight.models.Transaction;
import com.pluralsight.ui.Console;

import java.util.ArrayList;
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
                    [H] Home""");

            String choice = console.promptForStringOptions("Choose an option: ", "a","d","p","r","h");

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
