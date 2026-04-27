package com.pluralsight;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;




public class CliApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Use console


        System.out.println("============ Welcome To Our Financial Manager App =============");

        ArrayList<Transaction> transations = loadTransactions();
//        ArrayList<Transaction> displayLedger(ArrayList<Transaction>);


        //Home Menu Screen

        String userOption;

        do{
            String homeMenu = """
                    Choose an option:
                    D- Add Deposit
                    P- Make payment
                    L- Ledger
                    X- Exit 
                    Enter a Command: """;

            System.out.println(homeMenu);
            userOption = scanner.nextLine();



            switch (userOption){
                case D:
                    addDeposit(transations, scanner);
                    break;
                case P:
                    makePayment(transations, scanner);
                    break;
                case L:
                    legder(); //Work on the ledger method first
                    break;
                case X:
                    System.out.println("Thank you for using Cli App");
                    break;

                default:
                    System.out.println("Invalid Input!");

            }

        } while (userOption != "X");



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


public







}
