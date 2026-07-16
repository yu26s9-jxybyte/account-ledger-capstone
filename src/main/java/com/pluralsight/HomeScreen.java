package com.pluralsight;

import com.pluralsight.data.TransactionFileReader;
import com.pluralsight.models.Transaction;
import com.pluralsight.ui.Colors;
import com.pluralsight.ui.Console;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class HomeScreen {
    private final Console console;
    private final TransactionFileReader transactionFileReader;
    private static final int DESCRIPTION_MAX_CHARACTER_COUNT = 35;
    private static final int VENDOR_MAX_CHARACTER_COUNT = 25;


    public HomeScreen(Console console, TransactionFileReader transactionFileReader) {
        this.console = console;
        this.transactionFileReader = transactionFileReader;

    }

    public void mainDisplay() {
        String choice;
        do {
            double balance = getBalance();
            String balanceColor = (balance < 0) ? Colors.RED_BOLD : Colors.GREEN_BOLD;

            System.out.printf("""
                   
                                    
                    %s------------------------------------
                                FINANCIAL LEDGER
                    %s  Current Balance : %s$%,.2f%s
                    
                    %s  [%sD%s] Add a Deposit
                    %s  [%sP%s] Make a Payment
                    %s  [%sL%s] Ledger
                    %s  [%sX%s] Exit
                    %s------------------------------------
                    """ + Colors.RESET,
                    Colors.CYAN,
                    Colors.RESET, balanceColor, balance, Colors.RESET,
                    Colors.RESET, Colors.GREEN_BOLD, Colors.RESET,
                    Colors.RESET, Colors.RED_BOLD, Colors.RESET,
                    Colors.RESET, Colors.CYAN_BOLD, Colors.RESET,
                    Colors.RESET, Colors.YELLOW_BOLD, Colors.RESET,
                    Colors.CYAN
            );

            choice = console.promptForStringOptions("Choose an option: ", "d", "p", "l", "x");
            switch (choice.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    addPayment();
                    break;
                case "L":
                    LedgerScreen ledgerScreen = new LedgerScreen(console, transactionFileReader);
                    ledgerScreen.showLedger();
                    break;
                case "X":
                    System.out.println(Colors.YELLOW_BOLD + "Goodbye!" + Colors.RESET);
                    return;
                default:
                    System.out.println(Colors.RED_BOLD + "Invalid choice. Please select a valid option." + Colors.RESET);
            }
        } while (!choice.equalsIgnoreCase("x"));

    }

    private void addDeposit () {
        System.out.println("\n" + Colors.GREEN_BACKGROUND + Colors.BLACK + "  NEW DEPOSIT ENTRY  " + Colors.RESET + "\n");

        LocalDateTime localDateTime = console.promptForDateTime();

        // These have to be typed by the user
        String description = console.promptForString("Description: ");

        String vendor = console.promptForString("Vendor: ");
    }

    /** Returns Current Balance of Account */
    private double getBalance () {
        double balance = 0;
        for (int i = 0; i < transactionFileReader.getTransactions().size(); i++) {
            balance += transactionFileReader.getTransactions().get(i).getAmount();
        }
        return balance;
    }


    private void addPayment() {
        System.out.println("\n" + Colors.RED_BACKGROUND + Colors.WHITE + "  NEW PAYMENT ENTRY  " + Colors.RESET + "\n");

        LocalDate localDate = console.promptForDate("Enter Date: ");
        LocalTime localTime = console.promptForTime("Enter Time: ");

        String description = console.promptForString("Description: ");

        String vendor = console.promptForString("Vendor: ");

        double amount = console.promptForDouble("Amount: ");
        amount = -Math.abs(amount);

        Transaction t = new Transaction(LocalDateTime.of(localDate, localTime), description, vendor, amount);
    }
}