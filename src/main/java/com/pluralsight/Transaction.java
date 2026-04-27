package com.pluralsight;

public class Transaction {

    private String TransactionDate;
    private String TransactionTime;
    private String TransactionDescription;
    private String TransactionVendor;
    private double TransactionAmount;

    public Transaction(String transactionDate, String transactionTime, String transactionDescription, String transactionVendor, double transactionAmount) {
        TransactionDate = transactionDate;
        TransactionTime = transactionTime;
        TransactionDescription = transactionDescription;
        TransactionVendor = transactionVendor;
        TransactionAmount = transactionAmount;
    }

    public String getTransactionDate() {
        return TransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        TransactionDate = transactionDate;
    }

    public String getTransactionTime() {
        return TransactionTime;
    }

    public void setTransactionTime(String transactionTime) {
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


