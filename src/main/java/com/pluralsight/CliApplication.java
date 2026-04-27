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
            userOption = scanner.nextLine();



            switch (userOption.toUpperCase()){
                case "D":
                    addDeposit(transations, scanner);
                    break;
                case "P":
                   makePayment(transations, scanner);
                   break;
                case "L":
                    displayLedger(transations); //Work on the ledger method first
                    break;
                case "X":
                    System.out.println("Thank you for using Cli App");
                    break;

                default:
                    System.out.println("Invalid Input!");

            }

        } while (! userOption.equalsIgnoreCase("X"));



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


    public static void addDeposit( ArrayList<Transaction> transactions, Scanner scanner) {


        try {

            FileWriter fw = new FileWriter("data/transactions.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            String text;

            System.out.println("Please, enter date of transaction (yyyy-MM-dd): ");
            String date = scanner.nextLine();

            System.out.println("Please, enter time of transaction: (HH:mm:ss): ");
            String time = scanner.nextLine();

            System.out.println("Please, enter description: ");
            String description = scanner.nextLine();

            System.out.println("Please, enter vendor: ");
            String vendor = scanner.nextLine();

            System.out.println("Please, enter amount: ");
            double amount = Double.parseDouble(scanner.nextLine());


            text = String.format("%s|%s|%s|%s|$%.2f\n",
            date, time, description, vendor, amount);

            bw.write(text);
            bw.close();


        } catch (IOException e) {
            System.out.println("There was an error:");
            e.getMessage();
        }

    }


    public static void makePayment( ArrayList<Transaction> transactions, Scanner scanner){

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
                    
                    Enter a command:
                                """;
            System.out.println(ledgerMenu);
            option = scanner.nextLine();


            switch (option) {
                case "A":
                    displayAllTransactions(ledger);
                    break;
                case "D":
                    displayDeposits(ledger);
                    break;
                case "P":
                    displayPayments(ledger);
                    break;
                case "R":
                    filterSearch(ledger);
                    break;
                case "H":
                    return;
                default:
                    System.out.println("Invalid Entry");


            }


            System.out.println("What Would You Want To Do Next?");


        }while (true);




    }


    public static void displayAllTransactions(ArrayList<Transaction> ledger){

        System.out.println("======== All Transactions ========");
        for (int i = ledger.size() - 1;  i >=0; i--){

            Transaction t = ledger.get(i);

            System.out.printf("%s | %s | %s | %s | $%.2f\n",
                    t.getTransactionDate(),
                    t.getTransactionTime(),
                    t.getTransactionDescription(),
                    t.getTransactionVendor(),
                    t.getTransactionAmount());





        }




    }


    public static void displayDeposits(ArrayList<Transaction> ledger){

    }

    public static void displayPayments(ArrayList<Transaction> ledger){

    }


    public static void filterSearch(ArrayList<Transaction> ledger){

    }











}
