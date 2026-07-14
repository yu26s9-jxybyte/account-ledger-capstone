package com.pluralsight.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Transaction {
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
    @Override
    public String toString(){
        DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("%-20s %-20s %-45s %-35s $%,.2f", this.getDate().format(dateFmt), this.getTime().format(timeFmt), description, vendor, amount);
    }


}

