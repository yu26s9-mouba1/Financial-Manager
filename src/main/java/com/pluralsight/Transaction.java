package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Transaction {

    private LocalDate TransactionDate;  //Change date type to locale date
    private LocalTime TransactionTime;
    private String TransactionDescription;
    private String TransactionVendor;
    private double TransactionAmount;

    public Transaction(LocalDate transactionDate, LocalTime transactionTime, String transactionDescription, String transactionVendor, double transactionAmount) {
        TransactionDate = transactionDate;
        TransactionTime = transactionTime;
        TransactionDescription = transactionDescription;
        TransactionVendor = transactionVendor;
        TransactionAmount = transactionAmount;
    }

    public LocalDate getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        TransactionDate = transactionDate;
    }

    public LocalTime getTransactionTime() {
        return TransactionTime;
    }

    public void setTransactionTime(LocalTime transactionTime) {
        TransactionTime = transactionTime;
    }

    public String getTransactionDescription() {
        return TransactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        TransactionDescription = transactionDescription;
    }

    public String getTransactionVendor() {
        return TransactionVendor;
    }

    public void setTransactionVendor(String transactionVendor) {
        TransactionVendor = transactionVendor;
    }

    public double getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        TransactionAmount = transactionAmount;
    }
}


