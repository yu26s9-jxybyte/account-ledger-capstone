package com.pluralsight.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Console {
    private final Scanner scanner;
    private final static LocalDate MIN_DATE = LocalDate.parse("1970-01-01");
    public Console(){
        scanner = new Scanner(System.in);
    }

    /**
     * Checks if user input is between minimum and maximum character count.
     * @param input the string the user entered.
     * @param minCharacterCount the minimum characters allowed to be in input.
     * @param maxCharacterCount the maximum characters allowed to be in input.
     * @return String the user entered.
     */
    public String characterCountLimit(String input, int minCharacterCount, int maxCharacterCount){
        if (input.length() < minCharacterCount){
            throw new IllegalArgumentException("Error: Character Count Cannot be less than " + minCharacterCount);
        }
        if (input.length() > maxCharacterCount){
            throw new IllegalArgumentException("Error: Character Count Cannot Exceed " + maxCharacterCount);
        }
        return input;
    }

    /**
     * Prompts user to enter a string.
     * @param prompt the message displayed to the user.
     * @return String the user entered.
     */
    public String promptForString(String prompt){
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Prompts the user for a date.
     * @param prompt the message displayed to the user.
     * @return LocalDate the parsed and validated date the user entered.
     */
    public LocalDate promptForDate(String prompt){
        while(true) {
            try {
                String userInput = promptForString(prompt);
                return parseDate(userInput);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     * Parses and validates date in format YYYY-M-D or YYYY-MM-DD not exceeding today's date.
     * @param input the date being parsed and validated.
     * @return LocalDate the date user enters.
     */
    public LocalDate parseDate(String input){
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-M-d");
        try {
            LocalDate parseDate = LocalDate.parse(input, fmt);
            if (parseDate.isAfter(LocalDate.now()) || parseDate.isBefore(MIN_DATE)){
                throw new IllegalArgumentException("Error: Date has to be between 1970-01-01 and today");
            }
            return parseDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Error: Invalid Date. Please Try Again.");
        }
    }
    /**
     * Parses and validates time in HH:MM format.
     * @param input the time being parsed and validated.
     * @return LocalTime, the time the user entered.
     */
    public LocalTime parseTime(String input){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        try {
            return LocalTime.parse(input, timeFormatter);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Error: Invalid Time. Please Try Again.");
        }
    }
    /**
     * Prompts the user for a time.
     * @param prompt the message displayed to the user.
     * @return LocalTime the validated and parsed time the user entered.
     */
    public LocalTime promptForTime(String prompt){
        while(true){
            try{
                String userInput = promptForString(prompt);
                return parseTime(userInput);
            }catch(IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public LocalDateTime promptForDateTime(){
        boolean isFuture;
        LocalDateTime dateTime;

        LocalDate date = promptForDate("Enter Date (YYYY-MM-DD): ");
        do {
            LocalTime time = promptForTime("Enter Time (24:00): ");
            dateTime = LocalDateTime.of(date, time);
            isFuture = dateTime.isAfter(LocalDateTime.now());

            if (isFuture){
                System.out.println("Error: Date and Time cannot be in the future.");
            }

        }while(isFuture);
        return dateTime;
    }

    public Double promptForDouble(String prompt){
        while (true) {
            String input = promptForString(prompt);
            try {
                return Double.parseDouble(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Must enter a valid number.");
            }
        }
    }

    /**
     * Prompts user for a menu option input.
     * @param prompt the message displayed to the user.
     * @param options the options user has to choose from.
     * @return the String the user entered.
     */
    public String promptForStringOptions(String prompt, String ...options){
        while(true) {
            String userInput = promptForString(prompt);
            for (String option : options) {
                if (userInput.equalsIgnoreCase(option)) {
                    return option;
                }
            }
            System.out.println("Must Enter a Valid Option.");
        }
    }
    public int promptForInt(String prompt, int min, int max){
        int parseInt;
        while(true){
            try {
                System.out.print(prompt);
                parseInt = Integer.parseInt(scanner.nextLine().strip());
                if (parseInt >= min && parseInt <= max){
                    return parseInt;
                }
                System.out.println("Please enter an option between " + min + "-" + max);
            }catch(NumberFormatException e){
                System.out.println("Error: Invalid Character. Please Try Again!");
            }
        }
    }
}
