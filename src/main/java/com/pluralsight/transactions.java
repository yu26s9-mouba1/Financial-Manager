package com.pluralsight;

public class transactions {

    private String TransactionDate;
    private int TransactionTime;
    private String TransactionDescription;
    private String TransactionVendor;
    private float TransactionAmount;

    public transactions(String transactionDate, int transactionTime, String transactionDescription, String transactionVendor, float transactionAmount) {
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

    public int getTransactionTime() {
        return TransactionTime;
    }

    public void setTransactionTime(int transactionTime) {
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

    public float getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(float transactionAmount) {
        TransactionAmount = transactionAmount;
    }
}


