package com.pluralsight;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.Console;
import java.io.*;





public class CliApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Use console


        System.out.println("============ Welcome To Our Financial Manager App =============");

        ArrayList<Transaction> transations = loadTransactions();
        ArrayList<Transaction> ledger = new ArrayList<>();


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
                    displayLedger(); //Work on the ledger method first
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

        LocalDate date = LocalDate.parse(parts[0]);
        LocalTime time  = LocalTime.parse(parts[1]);
        String description = parts[2];
        String vendor = parts[3];
        double amount = Double.parseDouble(parts[4]);

        return new Transaction(date, time, description, vendor, amount);


    }





    public static void displayLedger(ArrayList<Transaction> ledger ){
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======Ledger Screen ==========");

        System.out.println("What Would You Like TO Do?");

        String option;
        do {

            String ledgerMenu = """
                    
                    A- Display all transactions
                    D- Display deposits
                    P- Display payments
                    R- Filter/Search reports 
                    H- Return to home screen
                    
                    
                    """;
            System.out.println(ledgerMenu);
            option = scanner.nextLine();




        switch (option){
            case "A":
                displayAllTransactions();
            break;
            case "D":
                displayDeposits();
                break;
            case "P":
                displayPayments();
                break;
            case "R":
                filterSearch();
                break;
            case "H":
                return;
            default:
                System.out.println("Invalid Entry");


        }while (true);













    }










}
