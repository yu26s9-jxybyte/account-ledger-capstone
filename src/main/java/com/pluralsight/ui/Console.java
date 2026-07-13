package com.pluralsight.ui;

import java.util.Scanner;

public class Console {
    private final Scanner scanner;
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
}
