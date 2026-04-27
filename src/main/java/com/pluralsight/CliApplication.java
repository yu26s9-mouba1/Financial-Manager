package com.pluralsight;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class CliApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("============ Welcome To Our Financial Manager App =============");

        ArrayList<Transaction> transations = loadTransactions();




    }





    public static ArrayList<Transaction> loadTransactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("data/transactions.csv"));

            br.readLine(); //Making sure the header is skipped


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
            System.out.println("There was an error reading the file: " + e.getMessage());
        }

        return transactions;
    }

    public static Transaction getTransaction(String line){

        String[] parts = line.split("\\|");

        String date = parts[0];
        String time = parts[1];
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transaction(date, time, description, vendor, amount);


    }





}
