package com.pluralsight;

import com.pluralsight.data.TransactionFileReader;
import com.pluralsight.models.Transaction;
import com.pluralsight.ui.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class HomeScreen {
    private final Console console;
    private final TransactionFileReader transactionFileReader;
    private static final int DESCRIPTION_MAX_CHARACTER_COUNT = 35;
    private static final int VENDOR_MAX_CHARACTER_COUNT = 25;
    private final ArrayList<Transaction> transactions = new ArrayList<>();

    public HomeScreen(Console console, TransactionFileReader transactionFileReader) {
        this.console = console;
        this.transactionFileReader = transactionFileReader;

    }

    public void mainDisplay() {
        String choice;
        do {
            double balance = getBalance();
            System.out.printf("""
                    \n
                    ------------------------------------
                                Home Screen
                      Current Balance : $%.2f
                    [D] Add a deposit
                    [P] Make a Payment
                    [L] Ledger
                    [X] Exit
                    ------------------------------------
                    """, balance
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
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please select valid option.");
            }
        } while (!choice.equalsIgnoreCase("x"));
    }


        private void addDeposit () {

            LocalDate localDate = console.promptForDate("Enter Date: ");
            LocalTime localTime = console.promptForTime("Enter Time: ");

            // These have to be typed by the user
            String description = console.promptForString("Description: ");

            String vendor = console.promptForString("Vendor: ");

            double amount = console.promptForDouble("Amount: ");

            // transaction object
            Transaction t = new Transaction(LocalDateTime.of(localDate, localTime), description, vendor, amount);

            // saves it
            transactionFileReader.saveTransaction(t);

            System.out.println("Deposit added");
        }

        private void addPayment () {
            LocalDate localDate = console.promptForDate("Enter Date: ");
            LocalTime localTime = console.promptForTime("Enter Time: ");

  private void addPayment() {
      LocalDateTime localDateTime = console.promptForDateTime();

      // These have to be typed by the user
      String description = console.characterCountLimit("Description: ", 1, DESCRIPTION_MAX_CHARACTER_COUNT );

      String vendor = console.characterCountLimit("Vendor: ", 1, VENDOR_MAX_CHARACTER_COUNT);
      double amount = console.promptForDouble("Amount: ");
      amount = -Math.abs(amount);

      Transaction t = new Transaction(localDateTime, description, vendor, amount);
            String description = console.promptForString("Description: ");

            String vendor = console.promptForString("Vendor: ");

            double amount = console.promptForDouble("Amount: ");
            amount = -Math.abs(amount);

            Transaction t = new Transaction(LocalDateTime.of(localDate, localTime), description, vendor, amount);

            transactionFileReader.saveTransaction(t);

            System.out.println("Payment added");
        }

    }

