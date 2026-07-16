package com.pluralsight;

import com.pluralsight.data.TransactionFileReader;
import com.pluralsight.models.Transaction;
import com.pluralsight.ui.Colors;
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
            System.out.printf("""
                    
                    %s------------------------------------
                                LEDGER MENU
                    %s------------------------------------
                    %s  [%sA%s] All Transactions
                    %s  [%sD%s] Deposits Only
                    %s  [%sP%s] Payments Only
                    %s  [%sR%s] Reports
                    %s  [%sC%s] Custom Search
                    %s  [%sH%s] Home
                    %s------------------------------------
                    """ + Colors.RESET,
                    Colors.CYAN,
                    Colors.CYAN,
                    Colors.RESET, Colors.CYAN_BOLD, Colors.RESET,
                    Colors.RESET, Colors.GREEN_BOLD, Colors.RESET,
                    Colors.RESET, Colors.RED_BOLD, Colors.RESET,
                    Colors.RESET, Colors.CYAN_BOLD, Colors.RESET,
                    Colors.RESET, Colors.YELLOW_BOLD, Colors.RESET,
                    Colors.RESET, Colors.RED_BOLD, Colors.RESET,
                    Colors.CYAN
            );
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
                    customSearch();
                    break;

                case "H":
                    return;

                default:
                    System.out.println(Colors.RED_BOLD + "Invalid option. Try again." + Colors.RESET);
            }
        }
    }

    private void showAll() {
        displayTransactionsMenu(Colors.CYAN_BOLD + "\nAll Transactions" + Colors.RESET, transactionFileReader.getTransactions());
    }

    private void showDeposits() {
        ArrayList<Transaction> transactions = transactionFileReader.getTransactions();
        ArrayList<Transaction> deposits = transactions.stream().filter(transaction ->
                        transaction.getAmount() > 0)
                .collect(Collectors.toCollection(ArrayList::new));
        displayTransactionsMenu(Colors.GREEN_BOLD + "\nDeposits Only" + Colors.RESET, deposits);
    }

    private void showPayments() {
        ArrayList<Transaction> transactions = transactionFileReader.getTransactions();
        ArrayList<Transaction> payments = transactions.stream().filter(transaction ->
                        transaction.getAmount() < 0)
                .collect(Collectors.toCollection(ArrayList::new));
        displayTransactionsMenu(Colors.RED_BOLD + "\nPayments Only" + Colors.RESET, payments);
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
            System.out.println(Colors.YELLOW_BOLD + "You are on the first page." + Colors.RESET);
            return currentPage + 1;
        }
        else if (currentPage > lastPage ){
            System.out.println(Colors.YELLOW_BOLD + "You have reached the last page." + Colors.RESET);
            return currentPage - 1;
        }

        System.out.printf(Colors.CYAN + "%75s %d %n" + Colors.RESET, "PAGE", currentPage);
        System.out.printf("%s %n%-20s %-20s %-45s %-35s %s %n", header, Colors.CYAN_BOLD + "DATE" + Colors.RESET, Colors.CYAN_BOLD + "TIME" + Colors.RESET, Colors.CYAN_BOLD + "DESCRIPTION" + Colors.RESET, Colors.CYAN_BOLD + "VENDOR" + Colors.RESET, Colors.CYAN_BOLD + "AMOUNT" + Colors.RESET);

        System.out.println(Colors.CYAN + "=".repeat(145) + Colors.RESET);
        for (int i = previousTransactionsDisplayed; i< transaction.size() && i < previousTransactionsDisplayed + 10 ; i++){
            Transaction t = transaction.get(i);
            String valueColor = (t.getAmount() < 0) ? Colors.RED : Colors.GREEN;
            System.out.printf("%-20s %-20s %-45s %-35s %s%s%s%n",
                    t.getDate(),
                    t.getTime(),
                    t.getDescription(),
                    t.getVendor(),
                    valueColor,
                    String.format("$%,.2f", t.getAmount()),
                    Colors.RESET
            );
        }
        return currentPage;
    }
    // Custom search method to filter through transactions by any attribute
    public void customSearch(){

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
        ArrayList<Transaction> custom = new ArrayList<>(transactionFileReader.getTransactions());
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
        displayTransactionsMenu(Colors.CYAN_BOLD + "\nCustom Search" + Colors.RESET, custom);
        if (custom.isEmpty()){
            System.out.println(Colors.YELLOW_BOLD + "Looks like there isn't anything that matches your filters. Try broadening your search." + Colors.RESET);
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
                System.out.println(Colors.RED_BOLD + "Date not in correct format, please try again. (yyyy-mm-dd) " + Colors.RESET);
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
            System.out.print(Colors.CYAN + "=".repeat(51) + Colors.RESET);
            System.out.print(" [" + Colors.CYAN_BOLD + "P" + Colors.RESET + "] PREVIOUS PAGE [" + Colors.CYAN_BOLD + "N" + Colors.RESET + "] NEXT PAGE [" + Colors.RED_BOLD + "X" + Colors.RESET + "] EXIT ");
            System.out.println(Colors.CYAN + "=".repeat(53) + Colors.RESET);
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