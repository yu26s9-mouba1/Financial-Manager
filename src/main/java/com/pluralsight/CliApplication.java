package com.pluralsight;
import java.io.IOException;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.*;





public class CliApplication {

    private static final String PRODUCT_FILE = "data/transactions.csv";

    public static void main(String[] args) {



        ArrayList<Transaction> transactions = loadTransactions();



        //Home Menu Screen

        String userOption;

        do{
            System.out.println("============ Welcome To Our Financial Manager App =============");
            String homeMenu = """
                    Choose an option:
                    D- Add Deposit
                    P- Add Payment
                    L- Ledger
                    X- Exit
                    """;

            System.out.println(homeMenu);
            userOption = Console.promptForString("Enter Command: ").trim().toUpperCase();




            switch (userOption.toUpperCase()){
                case "D":
                    addDeposit(transactions);
                    break;
                case "P":
                   addPayment(transactions);
                   break;
                case "L":
                    displayLedger(transactions);
                    break;
                case "X":
                    System.out.println("Thank you for using Cli App!");
                    break;

                default:
                    System.out.println("Invalid Entry, Please try again!");

            }

        } while (! userOption.equalsIgnoreCase("X"));



    }





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


    public static void addDeposit( ArrayList<Transaction> transactions) {


        try {


                FileWriter fw = new FileWriter(PRODUCT_FILE, true);
                BufferedWriter bw = new BufferedWriter(fw);
                String text;


                String date = Console.promptForString(" Please enter date (yyyy-MM--dd): ");

                String time = Console.promptForString("Please, enter time (HH:mm:ss): ");

                String description = Console.promptForString("Please, enter description: ");

                String vendor = Console.promptForString("Please, enter vendor: ");

                double amount = Console.promptForDouble("Please, enter amount: ");

                System.out.println();
                System.out.println("Deposit Successfully Added!");

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate parsedDate = LocalDate.parse(date, fmt);
            String formattedDate = parsedDate.format(fmt);

            text = String.format("%s|%s|%s|%s|%.2f\n",
                    formattedDate, time, description, vendor, amount);

                bw.write(text);
                bw.close();


        } catch (IOException e) {
            System.out.println("There was an error:");
            System.out.println(e.getMessage());
        }

    }


    public static void addPayment( ArrayList<Transaction> transactions){

        try{


            FileWriter fw = new FileWriter(PRODUCT_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);

            String text;

            String date = Console.promptForString("Please, enter date (yyyy-MM--dd): ");

            String time = Console.promptForString("Please, enter time (HH:mm:ss): ");

            String description = Console.promptForString("Please, enter description: ");

            String vendor = Console.promptForString("Please, enter vendor: ");

            double amount = Console.promptForDouble("Please, enter amount: ");
            amount *= -1;

            System.out.println();
            System.out.println("Payment Successfully Added!");

            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");   //# 3 format not working, application crashing
            LocalDate parsedDate = LocalDate.parse(date, fmt);
            String formattedDate = parsedDate.format(fmt);

            text = String.format("%s|%s|%s|%s|%.2f\n",
                    formattedDate, time, description, vendor, amount);


            bw.write(text);
            bw.close();


        } catch (IOException e) {
            System.out.println("There was an error:");
            System.out.println(e.getMessage());


        }


    }





    public static void displayLedger(ArrayList<Transaction> ledger ){

        System.out.println("======== Ledger Screen ========");

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
            option = Console.promptForString("Enter command: ").trim().toUpperCase();


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
                    System.out.println("Invalid Entry, Please try again!");


            }

            System.out.println();
            System.out.println("What Would You Like To Do Next?");


        }while (true);




    }


    public static void displayAllTransactions(ArrayList<Transaction> ledger){

        System.out.println("======== All Transactions ========");
        for (int i = ledger.size() - 1;  i >=0; i--){

            Transaction t = ledger.get(i);

            System.out.printf("%s|%s|%s|%s|%.2f\n",
                    t.getTransactionDate(),
                    t.getTransactionTime(),
                    t.getTransactionDescription(),
                    t.getTransactionVendor(),
                    t.getTransactionAmount());





        }




    }


    public static void displayDeposits(ArrayList<Transaction> ledger){


        System.out.println("======== Deposit History ==============");

        for (int i = ledger.size() - 1;  i >=0; i--) {

            Transaction t = ledger.get(i);

            if (t.getTransactionAmount() > 0) {

                System.out.printf("%s|%s|%s|%s|%.2f\n",
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

                System.out.printf("%s | %s | %s | %s | %.2f\n",
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

        System.out.println("======= You Are Now On The Report Screen =======");
        System.out.println(" Choose A Report To View: ");

        String option;

        do{
            String menu = """
                    
                    1- Month to date
                    2- Previous Month
                    3- Year to Date 
                    4- Previous Year
                    5- Search by Vendor
                    6- Custom Search
                    0- Return to ledger page
                    H- Return to home menu

                    
                    """;
            //         H- Return to home menu   #1 Taking me back to the ledger screen instead of home screen

            System.out.println(menu);
            option = Console.promptForString("Enter Command: ").trim().toUpperCase();


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
                    searchByVendor(ledger);
                    break;
                case "6":
                    displayCustomSearch(ledger);
                    break;
                case "0":
                    return;
//                     displayLedger(ledger);
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
                         "%s|%s|%s|%s|%.2f\n",
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

                            "%s|%s|%s|%s|%.2f\n",
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

                        "%s|%s|%s|%s|%.2f\n",
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
//        LocalDate today = LocalDate.now();

        int previousYear = LocalDate.now().getYear() - 1;

        for (int i = ledger.size() -1 ; i >= 0; i --) {

            Transaction t = ledger.get(i);

            if (t.getTransactionDate().getYear() == previousYear) {

                System.out.printf(

                        "%s|%s|%s|%s|$%.2f\n",
                        t.getTransactionDate(),
                        t.getTransactionTime(),
                        t.getTransactionDescription(),
                        t.getTransactionVendor(),
                        t.getTransactionAmount());

            }


        }
    }


    public static void searchByVendor(ArrayList<Transaction> ledger){
        System.out.println("======= Search By Vendor =======");


        String vendorName = Console.promptForString("Please, enter vendor name: ");

        boolean found = false;

        for (Transaction t : ledger){

            if (t.getTransactionVendor().toLowerCase().contains(vendorName)){

                System.out.println(t.getTransactionVendor() + "'s" + " Transactions: ");

                System.out.printf(

                        "%s|%s|%s|%s|%.2f\n",
                        t.getTransactionDate(),
                        t.getTransactionTime(),
                        t.getTransactionDescription(),
                        t.getTransactionVendor(),
                        t.getTransactionAmount());

                found = true;


            }

        }

        if (!found){
            System.out.println("Sorry, vendor not found, try again: ");
        }




    }



    public static void displayCustomSearch(ArrayList<Transaction> ledger){

        System.out.println("========= Custom Search View =============");
        System.out.println();


        String startDate = Console.promptForString("Please, enter start date of transaction (YYYY-MM-dd): ").trim();
        System.out.println();
        String endDate = Console.promptForString("Please, enter end date of transaction (YYYY-MM-dd): ").trim();
        System.out.println();
        String description = Console.promptForString("Please, enter description of transaction: ").trim();
        System.out.println();
        String amount = Console.promptForString("Please, enter amount of transaction: ");
        System.out.println();
        String vendor = Console.promptForString("Please, enter vendor of transaction: ");



//        LocalDate today = LocalDate.now();
        LocalDate parsedStartDate = null;
        LocalDate parsedEndDate = null;
        Double parsedAmount = null;

        if (! startDate.isEmpty()){
            parsedStartDate = LocalDate.parse(startDate);


        }

        if (! endDate.isEmpty()){
            parsedEndDate = LocalDate.parse(endDate);


        }

        if (!amount.isEmpty()){
            parsedAmount = Double.parseDouble(amount);
        }



        for(Transaction t : ledger){

            boolean matches = true;

            if (parsedStartDate != null && t.getTransactionDate().isBefore(parsedStartDate)){
                matches = false;

            }


            if (parsedEndDate != null && t.getTransactionDate().isAfter(parsedEndDate)){
                matches = false;
            }

            if (! description.isEmpty() && !t.getTransactionDescription().toLowerCase().contains(description.toLowerCase())){
                matches = false;
            }


            if (!vendor.isEmpty() && !t.getTransactionVendor().toLowerCase().contains(vendor.toLowerCase())){

                matches = false;

            }


            if (parsedAmount != null && Double.compare(t.getTransactionAmount(),  parsedAmount) !=0) {
                matches = false;


            }


            if (matches){
                System.out.println();
                System.out.println("*********Your transaction is listed below:************* ");
                System.out.printf( "%s | %s | %s | %s | %.2f%n",
                        t.getTransactionDate(),
                        t.getTransactionTime(),
                        t.getTransactionDescription(),
                        t.getTransactionVendor(),
                        t.getTransactionAmount());




            }


        }

        }






}
