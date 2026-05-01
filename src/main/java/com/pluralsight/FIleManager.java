package com.pluralsight;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class FIleManager {

    private static final String PRODUCT_FILE = "data/transactions.csv";



    public static ArrayList<Transaction> loadTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(PRODUCT_FILE));

            br.readLine();


            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;

                }

                Transaction t = getTransaction(line);
                transactions.add(t);

            }
            br.close();


        } catch ( IOException e){
            System.out.println("There was an error : " + e.getMessage());
        }

        return transactions;
    }

    public static Transaction getTransaction(String line){

        String[] parts = line.split("\\|");

        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime time  = LocalTime.parse(parts[1]);
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transaction(date, time, description, vendor, amount);


    }









}
