package com.pluralsight.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


public class Transaction {
    //assignments
    private LocalDateTime dateTime;
    private String description;
    private String vendor;
    private double amount;

    public Transaction(LocalDateTime dateTime, String description, String vendor, double amount){
        this.dateTime = dateTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public LocalDate getDate(){
        return dateTime.toLocalDate();
    }

    public LocalTime getTime(){
        return dateTime.toLocalTime();
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription(){
        return description;
    }
    public String getVendor(){
        return vendor;
    }
    public double getAmount(){
        return amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    //converting back to a csv line how it shown in the actual file
    public String toCSV(){
        return getDate() + "|" + getTime() + "|" + description + "|" + vendor + "|" + amount;
    }
}

