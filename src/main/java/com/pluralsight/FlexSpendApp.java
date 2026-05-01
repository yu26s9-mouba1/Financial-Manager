package com.pluralsight;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.io.*;


public class FlexSpendApp {

    private static final String PRODUCT_FILE = "data/transactions.csv";

    public static void main(String[] args) {

        System.out.println("  ================================================== Welcome To Flex Spend ===================================================");
        System.out.println("                                                     www.FlexSpend.com");

        /*
         * Display local date and time to the main screen
         */
        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("E, MMM dd, yyyy  HH:mm:ss");
        String formattedDate = today.format(fmt);
        System.out.println("                                                " + formattedDate);


        /*
         * Loads transactions from the CSV file
         */
        ArrayList<Transaction> transactions = FIleManager.loadTransactions();


        /*
         * Displays the Home Screen Menu using a do while loop and switch methods
         */
        String userOption;

        do {

            String homeMenu = """
                    What Would You Like To Do Today?
                         D- Add Deposit
                         P- Add Payment
                         L- Ledger
                         X- Exit
                    """;

            System.out.println(homeMenu);
            userOption = Console.promptForString(" Enter Command: ").trim().toUpperCase();

            switch (userOption.toUpperCase()) {
                case "D":
                    addDeposit();
                    break;
                case "P":
                    addPayment();
                    break;
                case "L":
                    displayLedger(transactions);
                    break;
                case "X":
                    System.out.println("Thank you for using Flex Spend!");
                    break;

                default:
                    System.out.println("Invalid Entry, Please try again!");

            }

        } while (!userOption.equalsIgnoreCase("X"));


    }


    /**
     * This Screen allows users to add deposits to the transaction history
     */
    public static void addDeposit() {
        System.out.println("************* Deposits ******************");


        try {

            String text;

            String date = Console.promptForDate(" Please, enter date (yyyy-MM--dd): ");

            String time = Console.promptForString("Please, enter time (HH:mm:ss): ");

            String description = Console.promptForString("Please, enter description: ");

            String vendor = Console.promptForString("Please, enter vendor: ");

            double amount = Console.promptForDouble("Please, enter amount: ");

            System.out.println();
            System.out.println("** Deposit Successfully Added! **");


            text = String.format("%s|%s|%s|%s|%.2f\n",
                    date, time, description, vendor, amount);


            /**
             * Reads and writes user inputs to the cvs file
             * The append mode (true) will add a new transaction data to the end of the existing file
             **/
            FileWriter fw = new FileWriter(PRODUCT_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(text);
            bw.close();


        } catch (IOException e) {
            System.out.println("There was an error:");
            System.out.println(e.getMessage());
        }

    }


    /**
     * This method allows users to add payments to the transaction history
     */
    public static void addPayment() {
        System.out.println("************* Payments ******************");

        try {

            String text;

            String date = Console.promptForDate("Please, enter date (yyyy-MM--dd): ");

            String time = Console.promptForString("Please, enter time (HH:mm:ss): ");

            String description = Console.promptForString("Please, enter description: ");

            String vendor = Console.promptForString("Please, enter vendor: ");

            double amount = Console.promptForDouble("Please, enter amount: ");
            amount *= -1;

            System.out.println();
            System.out.println("** Payment Successfully Added! **");

            text = String.format("%s|%s|%s|%s|%.2f\n",
                    date, time, description, vendor, amount);

            FileWriter fw = new FileWriter(PRODUCT_FILE, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(text);
            bw.close();


        } catch (IOException e) {
            System.out.println("There was an error:");
            System.out.println(e.getMessage());

        }
    }


    /**
     * The ledger screen displays multiple features that allows users to view different types of transactions
     */

    public static void displayLedger(ArrayList<Transaction> ledger) {

        System.out.println("******************************** Ledger Screen ***************************************");

        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("E, MMM dd, yyyy  HH:mm:ss");
        String formattedDate = today.format(fmt);
        System.out.println("                            " + formattedDate);

        System.out.println("What Would You Like To Do?");

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


        } while (true); //sets the ledger to keep running unless user chooses to quit


    }

    /**
     * One of the features of the ledger screen which allows users to view all types of transactions that have been made
     * The loop will loop through each transaction in the ledger and display them
     */
    public static void displayAllTransactions(ArrayList<Transaction> ledger) {

        System.out.println("======================== Transaction History ==========================================");
        for (int i = ledger.size() - 1; i >= 0; i--) {

            Transaction t = ledger.get(i);

            System.out.printf("%s|%s|%s|%s|%.2f\n",
                    t.getTransactionDate(),
                    t.getTransactionTime(),
                    t.getTransactionDescription(),
                    t.getTransactionVendor(),
                    t.getTransactionAmount());


        }


    }

    /**
     * A feature from the ledger screen that allows users to view 'deposits transactions' only
     * The loop will get each transaction that are only deposits
     */
    public static void displayDeposits(ArrayList<Transaction> ledger) {


        System.out.println("=========================== Deposit History ==============================");

        for (int i = ledger.size() - 1; i >= 0; i--) {

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


    /**
     * A feature from the ledger screen that allows users to view only deposits payments
     * The loop will get transactions that are payments only
     */
    public static void displayPayments(ArrayList<Transaction> ledger) {
        System.out.println("=========================== Payment History ============================");

        for (int i = ledger.size() - 1; i >= 0; i--) {

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


    /**
     * This feature gives users different options to search for any specific transaction they would like to view
     * A do while loop displays options and keeping the search feature running unless the user wants to quit
     * The switch method will call any option that the user wants to perform
     */
    public static void filterSearch(ArrayList<Transaction> ledger) {


        System.out.println("***************************** You Are Now On The Report Screen *************************");

        LocalDateTime today = LocalDateTime.now();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("E, MMM dd, yyyy  HH:mm:ss");
        String formattedDate = today.format(fmt);
        System.out.println("                               " + formattedDate);


        System.out.println(" Choose A Report To View: ");

        String option;

        do {
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

            System.out.println(menu);
            option = Console.promptForString("Enter Command: ").trim().toUpperCase();


            switch (option) {
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
                    System.out.println("Invalid Input, please try again!");
            }

        } while (true);


    }

    /**
     * Allows users to view transactions between a specific month to date
     */
    public static void displayMonthToDate(ArrayList<Transaction> ledger) {
        System.out.println("============================== Month To Date Transactions ============================");

        LocalDate today = LocalDate.now();

        for (int i = ledger.size() - 1; i >= 0; i--) {

            Transaction t = ledger.get(i);

            if (t.getTransactionDate().getMonthValue() == today.getMonthValue()
                    && t.getTransactionDate().getYear() == today.getYear()) {
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

    /**
     * Allows users to view transactions from the previous month
     */
    public static void displayPreviousMonth(ArrayList<Transaction> ledger) {

        System.out.println("============================== Previous Month Transactions ===========================");

        LocalDate today = LocalDate.now();

        LocalDate previousMonth = today.minusMonths(1);

        for (int i = ledger.size() - 1; i >= 0; i--) {

            Transaction t = ledger.get(i);

            if (t.getTransactionDate().getMonthValue() == previousMonth.getMonthValue()
                    && t.getTransactionDate().getYear() == previousMonth.getYear()) ;

            System.out.printf(

                    "%s|%s|%s|%s|%.2f\n",
                    t.getTransactionDate(),
                    t.getTransactionTime(),
                    t.getTransactionDescription(),
                    t.getTransactionVendor(),
                    t.getTransactionAmount());


        }


    }

    /**
     * Allows users to view transactions from a specific year to a specific date
     */
    public static void displayYearToDate(ArrayList<Transaction> ledger) {
        System.out.println("================================== Year To Date Transactions =======================");

        LocalDate today = LocalDate.now();

        for (int i = ledger.size() - 1; i >= 0; i--) {
            Transaction t = ledger.get(i);

            if (t.getTransactionDate().getYear() == today.getYear()) {

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


    /**
     * Allows users to view previous year transactions
     */
    public static void displayPreviousYear(ArrayList<Transaction> ledger) {

        System.out.println("========================= Previous Year Transactions ==============================");

        int previousYear = LocalDate.now().getYear() - 1;

        for (int i = ledger.size() - 1; i >= 0; i--) {

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

    /**
     * Allows users to search for a vendor name and see all transactions from that specific vendor
     */
    public static void searchByVendor(ArrayList<Transaction> ledger) {
        System.out.println("================================ Search By Vendor ================================");

        try {


            String vendorName = Console.promptForString("Please, enter vendor name: ");

            boolean found = false;

            for (Transaction t : ledger) {

                if (t.getTransactionVendor().toLowerCase().contains(vendorName)) {

                    System.out.println("*** " + t.getTransactionVendor() + "'s" + " Transactions: " + "***");

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

            if (!found) {
                System.out.println("Sorry, vendor not found, try again: ");
            }


        } catch (Exception e) {
            System.out.println("Invalid entry, please try again! ");

        }


    }


    /**
     * Gives users features where they can search for transactions based on dates,description, amount and vendor of transactions
     */
    public static void displayCustomSearch(ArrayList<Transaction> ledger) {

        try {

            System.out.println("************************* Custom Search Screen ************************************");
            LocalDateTime today = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("E, MMM dd, yyyy  HH:mm:ss");
            String formattedDate = today.format(fmt);
            System.out.println("                       " + formattedDate);

            System.out.println();


            /**
             * manages user inputs from the console class
             */
            String startDate = Console.promptForString("Please, enter start date of transaction (YYYY-MM-dd): ").trim();
            System.out.println();
            String endDate = Console.promptForString("Please, enter end date of transaction (YYYY-MM-dd): ").trim();
            System.out.println();
            String description = Console.promptForString("Please, enter description of transaction: ").trim();
            System.out.println();
            String amount = Console.promptForString("Please, enter amount of transaction: ");
            System.out.println();
            String vendor = Console.promptForString("Please, enter vendor of transaction: ");

            LocalDate parsedStartDate = null;
            LocalDate parsedEndDate = null;
            Double parsedAmount = null;

            if (!startDate.isEmpty()) {
                parsedStartDate = LocalDate.parse(startDate);
            }

            if (!endDate.isEmpty()) {
                parsedEndDate = LocalDate.parse(endDate);
            }

            if (!amount.isEmpty()) {
                parsedAmount = Double.parseDouble(amount);
            }


            /**
             * Loops through and get each transaction that matches the user entry and display them while parsing the dates
             */
            for (Transaction t : ledger) {

                boolean matches = true;

                if (parsedStartDate != null && t.getTransactionDate().isBefore(parsedStartDate)) {
                    matches = false;
                }


                if (parsedEndDate != null && t.getTransactionDate().isAfter(parsedEndDate)) {
                    matches = false;
                }

                if (!description.isEmpty() && !t.getTransactionDescription().toLowerCase().contains(description.toLowerCase())) {
                    matches = false;
                }


                if (!vendor.isEmpty() && !t.getTransactionVendor().toLowerCase().contains(vendor.toLowerCase())) {
                    matches = false;
                }


                if (parsedAmount != null && Double.compare(t.getTransactionAmount(), parsedAmount) != 0) {
                    matches = false;
                }


                if (matches) {
                    System.out.println();
                    System.out.println("****************** Your transactions Are listed below ******************* ");
                    System.out.printf("%s | %s | %s | %s | %.2f%n",
                            t.getTransactionDate(),
                            t.getTransactionTime(),
                            t.getTransactionDescription(),
                            t.getTransactionVendor(),
                            t.getTransactionAmount());
                }
            }


        } catch (DateTimeParseException e) {
            System.out.println("Invalid date entry, please enter yyyy-MM-dd! ");
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount, please, try again! ");
        }


    }

}
