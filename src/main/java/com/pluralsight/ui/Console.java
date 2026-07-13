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

    public String promptForString(String prompt){
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    public String promptOrDefault(String prompt, String defaultValue) {
        String input = promptForString(prompt);

        // if user presses enter, return the default value
        if (input.isEmpty()) {
            return defaultValue;
        }

        return input; // if not, return what they typed
    }
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
    public LocalTime parseTime(String input){
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm");
        try {
            return LocalTime.parse(input, timeFormatter);
        }catch (DateTimeParseException e){
            throw new IllegalArgumentException("Error: Invalid Time. Please Try Again.");
        }
    }

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

    public LocalDateTime promptForDateTime(String prompt){
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
