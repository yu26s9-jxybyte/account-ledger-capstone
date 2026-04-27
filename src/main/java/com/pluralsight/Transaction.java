package com.pluralsight;

public class Transaction {
    //assignments
    String date;
    String time;
    String description;
    String shop;
    double amount;

    public Transaction(String date, String time, String description, String shop, double amount){
        this.date = date;
        this.time = time;
        this.description = description;
        this.shop = shop;
        this.amount = amount;
    }

    public String getDate(){
        return date;
    }
    public String getTime(){
        return time;
    }
    public String getDescription(){
        return description;
    }
    public String getShop(){
        return shop;
    }
    public double getAmount(){
        return amount;
    }

    //converting back to a csv line how it shown in the actual file
    public String toCSV(){
        return date + "|" + time + "|" + description + "|" + shop + "|" + amount;
    }
}

