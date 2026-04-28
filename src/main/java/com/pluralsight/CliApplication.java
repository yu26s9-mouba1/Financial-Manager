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

        ArrayList<Transaction> transations = loadTransactions();   //////////////////////////
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

                Transaction t = getTransaction(line);  //////////////////////////////
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
        double amount = Double.parseDouble(parts[4]);  //////////////////////////////////////

        return new Transaction(date, time, description, vendor, amount);


    }


    public static void addDeposit( ArrayList<Transaction> transactions, Scanner scanner) {


        try {


            FileWriter fw = new FileWriter("data/transactions.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            String text;

            System.out.println("Please, enter date of transaction (yyyy-MM-dd): ");
            String date = scanner.nextLine();

            System.out.println("Please, enter time of transaction (HH:mm:ss): ");
            String time = scanner.nextLine();

            System.out.println("Please, enter description: ");
            String description = scanner.nextLine();

            System.out.println("Please, enter vendor: ");
            String vendor = scanner.nextLine();

            System.out.printf("Please, enter amount: ");
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

        try{


            FileWriter fw = new FileWriter("data/transactions.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);

            String text;

            System.out.println("Please, enter date of payment (yyyy-MM-dd): ");
            String date = scanner.nextLine();

            System.out.println("Please, enter time of payment (HH:mm:ss): ");
            String time = scanner.nextLine();

            System.out.println("Please, enter description of payment: ");
            String description = scanner.nextLine();

            System.out.println("Please, enter vendor: ");
            String vendor = scanner.nextLine();

            System.out.printf("Please, enter amount of payment: ");
            double amount = Double.parseDouble(scanner.nextLine());
            amount *= -1;




            text = String.format("%s|%s|%s|%s|$%.2f\n",
                    date, time, description, vendor, amount);

            bw.write(text);
            bw.close();


        } catch (IOException e) {
            System.out.println("There was an error:");
            e.getMessage();


        }


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


        System.out.println("======== All Deposits ==============");
        for (int i = ledger.size() - 1;  i >=0; i--) {

            Transaction t = ledger.get(i);

            if (t.getTransactionAmount() > 0) {

                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        t.getTransactionDate(),
                        t.getTransactionTime(),
                        t.getTransactionDescription(),
                        t.getTransactionVendor(),
                        t.getTransactionAmount());

            }
        }

    }

    public static void displayPayments(ArrayList<Transaction> ledger){
        System.out.println("========== Payment History ============");

        for (int i = ledger.size() - 1;  i >=0; i--) {

            Transaction t = ledger.get(i);

            if (t.getTransactionAmount() < 0) {

                System.out.printf("%s | %s | %s | %s | $%.2f\n",
                        t.getTransactionDate(),
                        t.getTransactionTime(),
                        t.getTransactionDescription(),
                        t.getTransactionVendor(),
                        t.getTransactionAmount());

            }
        }






    }


    public static void filterSearch(ArrayList<Transaction> ledger){
        Scanner scanner = new Scanner(System.in);

        System.out.println("======= You are now on the report screen =======");
        System.out.println(" Choose A Report To View ");

        String option;

        do{
            String menu = """
                    
                    1- Month to date
                    2- Previous Month
                    3- Year to Date 
                    4- Previous Year
                    5- Search by Vendor
                    0- Return to ledger page
                    H- Return to home page
                    
                    Enter a Command:
                    """;

            System.out.println(menu);
            option = scanner. nextLine().trim().toUpperCase();


            switch (option){
                case "1":
                    displayMonthToDate(ledger);
                    break;
                case "2":
                    displayPreviousMonth(ledger);
                    break;
                case "3":
                    displayYearToDate(ledger);
                    break;
                case "4":
                    displayPreviousYear(ledger);
                    break;
                case "5":
                    searchByVendor();
                    break;
                case "0":
                    displayLedger(ledger);
                case "H":
                    return;
                default:
                    System.out.println("Invalid Input, try again!");
            }

        } while(true);



    }




    public static void displayMonthToDate(ArrayList<Transaction> ledger){
        System.out.println("====== Month To Date Transactions ========");

        LocalDate today = LocalDate.now();

        for (int i = ledger.size() -1 ; i >= 0; i --){

            Transaction t = ledger.get(i);

             if (t.getTransactionDate().getMonthValue() == today.getMonthValue()
                    && t.getTransactionDate().getYear() == today.getYear()){
                 System.out.printf(
                         "%s | %s | %s | %s | $%.2f\n",
                         t.getTransactionDate(),
                         t.getTransactionTime(),
                         t.getTransactionDescription(),
                         t.getTransactionVendor(),
                         t.getTransactionAmount());

             }

        }






    }


    public static void displayPreviousMonth(ArrayList<Transaction> ledger){

        System.out.println("========= Previous Month Transactions ================");

        LocalDate today = LocalDate.now();

        LocalDate previousMonth = today.minusMonths(1);

        for (int i = ledger.size() -1 ; i >= 0; i --) {

            Transaction t = ledger.get(i);

            if (t.getTransactionDate().getMonthValue() == previousMonth.getMonthValue()
            && t.getTransactionDate().getYear() == previousMonth.getYear());

            System.out.printf(

                            "%s | %s | %s | %s | $%.2f\n",
                    t.getTransactionDate(),
                    t.getTransactionTime(),
                    t.getTransactionDescription(),
                    t.getTransactionVendor(),
                    t.getTransactionAmount());




        }





    }


    public static void displayYearToDate(ArrayList<Transaction> ledger){
        System.out.println("=========== Year To Date Transactions ===============");

        LocalDate today = LocalDate.now();

//        LocalDate YearToDate = today.withDayOfYear()

        for (int i = ledger.size() - 1; i >= 0; i --){
            Transaction t = ledger.get(i);

            if (t.getTransactionDate().getYear() == today.getYear()){

                System.out.printf(

                        "%s | %s | %s | %s | $%.2f\n",
                        t.getTransactionDate(),
                        t.getTransactionTime(),
                        t.getTransactionDescription(),
                        t.getTransactionVendor(),
                        t.getTransactionAmount());


            }
        }




    }

    public static void displayPreviousYear(ArrayList<Transaction> ledger){

        System.out.println("========= Previous Year Transactions ==========");
        LocalDate today = LocalDate.now();

        int previousYear = LocalDate.now().getYear() - 1;

        for (int i = ledger.size() -1 ; i >= 0; i --) {

            Transaction t = ledger.get(i);

            if (t.getTransactionDate().getYear() == previousYear) {

                System.out.printf(

                        "%s | %s | %s | %s | $%.2f\n",
                        t.getTransactionDate(),
                        t.getTransactionTime(),
                        t.getTransactionDescription(),
                        t.getTransactionVendor(),
                        t.getTransactionAmount());

            }


        }
    }


    public static void searchByVendor(){



    }












}
