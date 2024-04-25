import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {
    /**
     * The main method for the entire programme
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     * @throws InterruptedException
     *                      Thrown when a thread is waiting, sleeping, or otherwise paused for a long time and another thread interrupts it.
     */
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner input = new Scanner(System.in);

        boolean programStatus = true; // 'false' status signals action for program shutdown
        Match[] matchList = loadSavedMatches();

        do {
            printHeader();
            homepageMenu();

            int ch = input.nextInt();
            System.out.println();

            switch (ch) {
                case 0 -> programStatus = false;
                case 1 -> {
                    if (customerLogin())
                        customerMenu(matchList);
                }
                case 2 -> {
                    if (customerSignUp())
                        customerMenu(matchList);
                }
                case 3 -> {
                    if (staffLogin())
                        matchList = staffMenu(matchList);
                }
                case 4 -> queueSimulation();
                default -> System.out.println("Wrong choice");
            }

        } while (programStatus);

        try {
            saveMatchToFile(matchList);
        } finally {
            System.out.println("Bye Bye! See you Soon!");
        }
    }

    /**
     * Method to print the header of the programme
     */
    protected static void printHeader() {
        int borderNum = 40;
        System.out.println();
        for (int i = 0; i < borderNum; i++)
            System.out.print("=");

        System.out.println("\n\tWelcome to the 2026 World Cup!!!");

        for (int i = 0; i < borderNum; i++)
            System.out.print("=");
        System.out.println();
    }

    /**
     * HomepageMenu acts as a homepage interface for both the customers and the
     * staffs
     */
    protected static void homepageMenu() {
        System.out.println("\n1. Customers Login");
        System.out.println("2. Customers Registration");
        System.out.println("3. Staff Login");
        System.out.println("4. Queue Simulation");
        System.out.println("\n0. Shut down");

        System.out.print("\nEnter your choice: ");
    }

    /**
     * Method to allow customers to login via reading data from a file
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    private static boolean customerLogin() throws IOException  {

        ArrayList<Customer> customers = loginCustomerFromFile();
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Login Page");

        do {

            System.out.print("Enter customer's pin (6 digits): ");
            int newId = input.nextInt();
            System.out.print("Enter customer name: ");
            input.nextLine(); // read away unwanted newline.
            String newName = input.nextLine(); // read user input.

            // ensure correct name and password
            for (int i = 0; i < customers.size(); i++) {
                Customer currentCustomer = customers.get(i);
                // reject if either name or password is wrong or both are wrong
                if (newId == currentCustomer.getId() && newName.equals(currentCustomer.getName())) {
                    System.out.println("Correct password and username!!");
                    System.out.println();
                    return true;
                }

            }
            System.out.println("Wrong password or username");

        } while (true);
    }

    /**
     * Method to validate login for staffs
     * 
     * @param who
     *              Who acts as if it is a customer or an admin loging into the system (type of person)
     * @param name
     *              Name is either (customer or admin)
     * @param password
     *              The password of the user 
     * @return 
     *              Returns the name and password parameters
     */
    private static boolean validateLogin(String who, String name, String password) {
        if (who.equals("customer"))
            return name.equals("Customer") && password.equals("abc123");

        return name.equals("Admin") && password.equals("Admin123");
    }

    /**
     * Method to allow customers to login via reading data from a file
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     * 
     * @return
     *                      Returns customers from the arraylist list
     */
    protected static ArrayList<Customer> loginCustomerFromFile() throws IOException {
        ArrayList<Customer> customers = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("customer.csv"));

        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            // items[0] is id, items[1] is name
            int id = Integer.parseInt(items[0]); // convert String to int
            customers.add(new Customer(id, items[1]));
        }
        return customers;
    }

    /**
     * Customer SignUP is the method to allow customers to sign up
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    protected static boolean customerSignUp() throws IOException {

        ArrayList<Customer> customers = readCustomerFromFile();
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to SignUp Page");

        do {

            System.out.print("Enter a new pin (6 digits): ");
            int newId = input.nextInt();
            System.out.print("Enter customer name: ");
            input.nextLine(); // read away unwanted newline.
            String newName = input.nextLine(); // read user input.

            boolean idExist = false;
            for (int i = 0; i < customers.size(); i++) {
                Customer currentCustomer = customers.get(i);
                // reject id if it already exists
                if (newId == currentCustomer.getId()) {
                    System.out.println("Cannot add id " + newId + " because it already exists.");
                    idExist = true;
                    break;
                }
            }

            customers.add(new Customer(newId, newName));
            if (idExist == false) {
                System.out.println("Saving customer details to file...");
                saveCustomerToFile(customers);
                System.out.println("Saving successful");
                return true;
            }
        } while (true);

    }

    /**
     * Saves customer data into the file for signup purposes
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     * @param customers 
     *                      Uses the customers variable from the arraylist which refers to Customer.java
     */
    protected static void saveCustomerToFile(ArrayList<Customer> customers) throws IOException {
        // read students.csv into a list of lines.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customers.size(); i++)
            sb.append(customers.get(i).toCSVString() + "\n");
        Files.write(Paths.get("customer.csv"), sb.toString().getBytes());
    }

    /**
     * Reads customer data from file to check the data in the file(if there are
     * duplicates for signup for example)
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     * @param customers 
     *                      Uses the customers variable from the arraylist which refers to Customer.java
     * @return
     *                      Return customers from to the already initiated arraylist
     */
    protected static ArrayList<Customer> readCustomerFromFile() throws IOException {
        ArrayList<Customer> customers = new ArrayList<>();

        // read customer.csv into a list of lines.

        List<String> lines = Files.readAllLines(Paths.get("customer.csv"));

        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            // items[0] is id, items[1] is name
            int id = Integer.parseInt(items[0]); // convert String to int
            customers.add(new Customer(id, items[1]));
        }
        return customers;
    }

    /**
     * Method to validate staff login
     */
    protected static boolean staffLogin() {

        Scanner input = new Scanner(System.in);

        do {
            System.out.println("\tStaff Login");
            for (int i = 0; i < 24; i++)
                System.out.print("-");

            System.out.println("\nEnter '0' to return to the homepage.\n");

            System.out.print("Username: ");
            String name = input.nextLine();

            if (name.equals("0"))
                return false;

            System.out.print("Password: ");
            String password = input.nextLine();

            if (validateLogin("staff", name, password)) {
                System.out.println("\n" + name + ", successful login as staff\n");
                return true;
            } else
                System.out.println("Invalid username or password.\n");

        } while (true);
    }

    /**
     * Customer menu is used to navigate the customers as a form of a interface
     * 
     * @param matchList
     *                      List of matches to be print out
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     * @throws InterruptedException
     *                      Thrown when a thread is waiting, sleeping, or otherwise paused for a long time and another thread interrupts it.
     */
    protected static void customerMenu(Match[] matchList) throws IOException, InterruptedException {

        boolean continueStatus = true;

        do {
            System.out.println("1. Search and book");
            System.out.println("2. View all Matches");
            System.out.println("3. View seat layout of all Matches");
            System.out.println("0. Log Out");
            System.out.print("\nEnter your choice: ");

            Scanner input = new Scanner(System.in);
            int ch = input.nextInt();

            switch (ch) {
                case 0 -> {
                    return;
                }
                case 1 -> searchAndBook(matchList);
                case 2 -> viewAllMatches(matchList);
                case 3 -> viewCustomersAndSeats();
                default -> System.out.println("Wrong choice");
            }
            System.out.print("\nDo you want to continue (1.yes / 2.No) : ");
            if (input.nextInt() == 2)
                continueStatus = false;
            System.out.println("\n");

        } while (continueStatus);

    }

    /**
     * Entire booking process for customers
     * 
     * @param matchList
     *                      List of matches to be print out
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     * @throws InterruptedException
     *                      Thrown when a thread is waiting, sleeping, or otherwise paused for a long time and another thread interrupts it.
     * @return 
     *                      Returns result as if it is true or false
     * 
     */
    protected static boolean searchAndBook(Match[] matchList) throws IOException, InterruptedException {

        ArrayList<TicketSold> tickets = readTicketSoldFromFile();
        Scanner input = new Scanner(System.in);

        boolean result = true;
        System.out.print("\nEnter Match Name (Ex. Brazil vs Chile): ");
        String matchname = input.nextLine();
        do {
            for (Match match : matchList) {
                if (match.getMatchName().equals(matchname)) {
                    match.viewAllDetails();
                    result = true;
                    System.out.println("Would you like to book tickets for this match?");
                    System.out.print("Enter 1 to book and 2 to exit: ");
                    int c = input.nextInt();
                    if (c == 1) {

                        System.out.println("");
                        System.out.println("\t \t \t Stadium Seat Booking");
                        System.out.println("--------------------------------------------------------------------");
                        System.out.println("");
                        String[][] matrix = new String[11][5];

                        String filePath = match.getFilePath();
                        BufferedReader in = new BufferedReader(
                                new FileReader("C:/Users/User/Downloads/Final4/Final - Latest/" + filePath));

                        String line;
                        for (int column = 1; column <= 4; column++) {
                            System.out.print("\tCol" + (column) + "\t");
                        }
                        System.out.println("");

                        Object[] ticketsLines = in.lines().toArray();
                        // loop for row fields from old file and insert into matrix
                        for (int rownum = 1; rownum <= ticketsLines.length; rownum++) {
                            System.out.print("Row" + rownum + "|\t");
                            String ticketLine = ticketsLines[rownum - 1].toString().trim();
                            for (int col = 1; col <= 4; col++) {
                                String[] dataRow = ticketLine.split("\t\t");
                                matrix[rownum][col] = dataRow[col - 1];
                                System.out.print(matrix[rownum][col] + "\t\t");
                            }
                            System.out.println();
                        }

                        while (true) {
                            System.out.print("Please enter your name: ");
                            String name = input.next();

                            System.out.print("Enter row and column separated by space: ");
                            int x = input.nextInt();
                            int y = input.nextInt();
                            System.out.println();
                            System.out.println("row: " + x);
                            System.out.println("column: " + y);

                            // System.out.println("This is the problem");
                            // System.out.println(matrix[x][y]);

                            if ((x > 0 && y > 0) && (matrix[x][y].contains("*"))) {
                                System.out.println("\t \t \t Stadium Seat Booking");
                                System.out.println();

                                for (int column = 1; column <= 4; column++) {
                                    System.out.print("\tCol" + (column) + "\t");
                                }
                                System.out.println();
                                System.out.println(
                                        "--------------------------------------------------------------------");

                                for (int rownum = 1; rownum <= 10; rownum++) {
                                    System.out.print("Row" + (rownum) + "| \t");
                                    for (int col = 1; col <= 4; col++) {
                                        // matrix [x][y]=name;
                                        if (rownum == x && col == y) {
                                            matrix[rownum][col] = name;
                                        }
                                        System.out.print(matrix[rownum][col] + "\t\t");
                                    }
                                    System.out.println();
                                }

                                System.out.println("You have reserved a match seat!");
                                System.out.print("Do you want to reserve another seat? (y/n): ");
                                char answer = input.next().charAt(0);
                                if (answer == 'y' || answer == 'Y') {
                                    continue;
                                } else {
                                    System.out.println("Enjoy the match!");
                                    System.out.print("Enter the number of tickets: ");
                                    int tick = input.nextInt();
                                    double tc = match.getTicketPrice() * tick;
                                    System.out.println("Amount to pay: RM " + tc);
                                    printWithDelays("PLEASE SCAN THE QR CODE TO MAKE PAYMENT", TimeUnit.MILLISECONDS,
                                            200);
                                    printWithDelays(" ", TimeUnit.MILLISECONDS, 2000);
                                    System.out.println();
                                    printWithDelays("THANK YOU FOR PAYING RM " + tc + " ONLY", TimeUnit.MILLISECONDS,
                                            200);
                                    tickets.add(new TicketSold(tick, tc));
                                    saveTicketSoldToFile(tickets);

                                    StringBuilder builder = new StringBuilder();
                                    for (int i = 1; i <= 10; i++)// for each row
                                    {
                                        for (int j = 1; j <= 4; j++)// for each column
                                        {
                                            builder.append(matrix[i][j] + "\t\t");// append to the output string
                                            builder.append(" ");// then add comma (if you don't like commas you can use
                                                                // spaces)
                                        }
                                        builder.append("\n");// append new line at the end of the row
                                    }
                                    BufferedWriter writer = new BufferedWriter(
                                            new FileWriter("C:/Users/User/Downloads/Final4/Final - Latest/" + filePath));
                                    writer.write(builder.toString());// save the string representation of the board
                                    writer.close();

                                    return true;

                                }

                            } else {
                                System.out.println("Invalid seat booked! Try again!");
                            }

                        }

                    } else

                        break;
                } else {
                    result = false;
                }

            }
            System.out.println("Match not found or incomplete booking!");
            break;
        } while (true);
        return result;

    }

    /**
     * This method is used to delay the output shown on the terminal
     * 
     * @param data
     *              data parameter to receive the data in String
     * @param unit
     *              unit parameter to receive the unit in milliseconds (as per this programme)
     * @param delay
     *              the amount of time delayed to print the statement
     */
    public static void printWithDelays(String data, TimeUnit unit, long delay)
            throws InterruptedException {
        for (char ch : data.toCharArray()) {
            System.out.print(ch);
            unit.sleep(delay);
        }
    }

    /**
     * Print out the details of all the matches
     * 
     * @param matchList
     *                  List of matches to be print out
     */
    protected static void viewAllMatches(Match[] matchList) {

        if (matchList == null) {
            System.out.println("No match data");
        }

        else {
            System.out.println();

            for (Match match : matchList) {
                match.viewAllDetails();
                System.out.println();

            }
        }
    }

    /**
     * To show the interface of the staff menu in the terminal
     * 
     * @param matchList
     *                  List of matches to be print out
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    protected static Match[] staffMenu(Match[] matchList) throws IOException {

        boolean continueStatus = true;

        do {
            System.out.println("1. Add Match details");
            System.out.println("2. View Customers and Seats");
            System.out.println("3. View Statistic of Matches");
            System.out.println("0. Log Out");
            System.out.print("\nEnter your choice: ");

            Scanner input = new Scanner(System.in);
            int ch = input.nextInt();

            switch (ch) {
                case 0 -> {
                    return matchList;
                }
                case 1 -> matchList = addMatch(matchList);
                case 2 -> viewCustomersAndSeats();
                case 3 -> viewStatistics();
                default -> System.out.println("Wrong choice");
            }
            System.out.print("\nDo you want to continue (1.yes / 2.No) : ");
            if (input.nextInt() == 2)
                continueStatus = false;
            System.out.println("\n");

        } while (continueStatus);

        return matchList;
    }

    /**
     * Staff addMatch is the method to allow staff to add new matches
     * 
     * @param matchList
     *                  List of matches to be print out
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     * 
     */
    protected static Match[] addMatch(Match[] matchList) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter the number of new matches: ");
        int number = input.nextInt();
        System.out.println();
        input.nextLine();

        int filledMatch = matchList.length;
        matchList = resizeMatchList(matchList, number);

        for (int i = filledMatch; i < matchList.length; i++) {

            int y = i;
            System.out.println("Match #" + ++y + " Details ");
            System.out.println("----------------");

            System.out.print("\tID: ");
            String matchID = input.nextLine();

            System.out.print("\tName: ");
            String matchName = input.nextLine();

            System.out.print("\tVenue: ");
            String venue = input.nextLine();

            System.out.print("\tNumber of Tickets: ");
            int numOfTickets = input.nextInt();

            System.out.print("\tPrice Per Tickets: ");
            double ticketPrice = input.nextDouble();

            // consumes the dangling newline character ("The enter keyword")
            input.nextLine();

            System.out.print("\tFilePath: ");
            String filePath = input.nextLine();

            matchList[i] = new Match(matchID, matchName, venue, numOfTickets, ticketPrice, filePath);

            saveMatchToFile(matchList);
        }
        System.out.println("New Matches Saved");
        return matchList;
    }

    private static Match[] resizeMatchList(Match[] matchList, int newSize) {
        newSize = matchList.length + newSize;
        Match[] newMatchList = new Match[newSize];

        int i = 0;
        for (Match match : matchList) {
            newMatchList[i++] = match;
        }

        return newMatchList;
    }

    /**
     * Method to view all the customers and seats of the programme
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    protected static void viewCustomersAndSeats() throws IOException {

        System.out.println("1. Brazil vs Chile");
        System.out.println("2. England vs France");
        System.out.println("3. USA vs Argentina");
        System.out.println("4. Malaysia vs Singapore");
        System.out.println("5. India vs Pakistan");
        System.out.print("View customers and seats of match number:");

        Scanner input = new Scanner(System.in);
        int mat = input.nextInt();

        switch (mat) {
            case 1 -> {
                MatchLayout("tickets.csv");
            }
            case 2 -> {
                MatchLayout("tickets2.csv");
            }
            case 3 -> {
                MatchLayout("tickets3.csv");
            }
            case 4 -> {
                MatchLayout("tickets4.csv");
            }
            case 5 -> {
                MatchLayout("tickets5.csv");
            }

        }
    }

    /**
     * Method to help differentiate the name of the match with the seats and
     * customers using OOP
     * @param fileName
     *                  to receive the fileName paramter to ensure it can be used again in the BufferedReader (in this case according to the BufferedReader library)
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */

    private static void MatchLayout(String fileName) throws IOException {

        BufferedReader in = new BufferedReader(new FileReader("C:/Users/User/Downloads/Final4/Final - Latest/" + fileName));

        System.out.println("");
        System.out.println("\t \t \t Stadium Seat Booking");
        System.out.println("--------------------------------------------------------------------");
        System.out.println("");
        String[][] matrix = new String[11][5];
        for (int column = 1; column <= 4; column++) {
            System.out.print("\tCol" + (column) + "\t");
        }
        System.out.println("");

        Object[] ticketsLines = in.lines().toArray();
        // loop for row fields from old file and insert into matrix
        for (int rownum = 1; rownum <= ticketsLines.length; rownum++) {
            System.out.print("Row" + rownum + "|\t");
            String ticketLine = ticketsLines[rownum - 1].toString().trim();
            for (int col = 1; col <= 4; col++) {
                String[] dataRow = ticketLine.split("\t\t");
                matrix[rownum][col] = dataRow[col - 1];
                System.out.print(matrix[rownum][col] + "\t\t");
            }
            System.out.println();
        }
    }

    private static Match[] loadSavedMatches() throws IOException {
        int i = 0;

        List<String> lines = Files.readAllLines(Paths.get("match.csv"));
        Match[] matchList = new Match[lines.size()];
        for (String line : lines) {
            String[] items = line.split(",");
            String ID = items[0];
            String name = items[1];
            String venue = items[2];
            int numOfTickets = Integer.parseInt(items[3]);
            double ticketPrice = Double.parseDouble(items[4]);
            String filePath = items[5];
            matchList[i++] = new Match(ID, name, venue, numOfTickets, ticketPrice, filePath);
        }
        return matchList;
    }

    private static void saveMatchToFile(Match[] matches) throws IOException {
        try (FileWriter output = new FileWriter("match.csv")) {
            for (Match match : matches) {
                output.write(match.toCSVString() + "\n");
            }
        }
    }

    /**
     * Method to view statistics of matches
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    protected static void viewStatistics() throws IOException {

        System.out.println("Matches Statistics");
        System.out.println("Combined Capacity: " + totalCapacity());
        System.out.println("Combined Seats Sold Out: " + ticketSold());
        System.out.println("Combined Revenue: " + totalSold());

    }

    private static double totalSold() throws IOException {
        double totalSold = 0;

        List<String> lines = Files.readAllLines(Paths.get("ticketSold.csv"));

        for (String line : lines) {
            String[] items = line.split(",");
            double ticketPrice = Double.parseDouble(items[1]);
            totalSold = totalSold + ticketPrice;
        }
        return totalSold;
    }

    private static int ticketSold() throws IOException {
        int ticketSold = 0;

        List<String> lines = Files.readAllLines(Paths.get("ticketSold.csv"));

        for (String line : lines) {
            String[] items = line.split(",");
            int numOfTickets = Integer.parseInt(items[0]);
            ticketSold = ticketSold + numOfTickets;
        }
        return ticketSold;
    }

    private static int totalCapacity() throws IOException {
        int totalCapacity = 0;

        List<String> lines = Files.readAllLines(Paths.get("match.csv"));

        for (String line : lines) {
            String[] items = line.split(",");
            int numOfTickets = Integer.parseInt(items[3]);
            totalCapacity = totalCapacity + numOfTickets;
        }
        return totalCapacity;
    }

    /**
     * Saves tickets data into the file for statistics purposes
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    protected static void saveTicketSoldToFile(ArrayList<TicketSold> tickets) throws IOException {
        // read students.csv into a list of lines.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tickets.size(); i++)
            sb.append(tickets.get(i).toCSVString() + "\n");
        Files.write(Paths.get("ticketSold.csv"), sb.toString().getBytes());
    }

    /**
     * Reads tickets sold data from file to get the data in the file(to calculate
     * the statistics data)
     * 
     * @return the ticket buy by customer
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    protected static ArrayList<TicketSold> readTicketSoldFromFile() throws IOException {
        ArrayList<TicketSold> tickets = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("ticketSold.csv"));

        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            // items[0] is id, items[1] is name
            int numOfTickets = Integer.parseInt(items[0]); // convert String to int
            double ticketPrice = Double.parseDouble(items[1]);
            tickets.add(new TicketSold(numOfTickets, ticketPrice));
        }
        return tickets;
    }

    /**
     * Menu to choose which match to simulate the queue for.
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    protected static void queueSimulation() throws IOException {

        System.out.println("1. Brazil vs Chile");
        System.out.println("2. England vs France");
        System.out.println("3. USA vs Argentina");
        System.out.println("4. Malaysia vs Singapore");
        System.out.println("5. India vs Pakistan");
        System.out.println("6. General Match");
        System.out.print("\nView Queue Simulation for match: ");

        Scanner input = new Scanner(System.in);
        int mat = input.nextInt();

        switch (mat) {
            case 1 -> {
                simulate("tickets.csv");
            }
            case 2 -> {
                simulate("tickets2.csv");
            }
            case 3 -> {
                simulate("tickets3.csv");
            }
            case 4 -> {
                simulate("tickets4.csv");
            }
            case 5 -> {
                simulate("tickets5.csv");
            }
            case 6 -> simulate("tickets6.csv");
        }
    }

    /**
     * Simulate the queue given the name of the match's csv file.
     * 
     * @param filename
     *                 The name of the csv file to be simulated
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    protected static void simulate(String filename) throws IOException {

        System.out.println();

        int row = 10;
        int column = 4;

        if (filename.equals("tickets6.csv")) { // override row & column
            row = 4;
            column = 3;
        }

        String[][] matrix = new String[row][column];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++)
                matrix[i][j] = "-";
        }

        Queue<String> queueE1 = new LinkedList<>();
        Queue<String> queueE2 = new LinkedList<>();
        Map<String, String> seatingCoordinate = new HashMap<>();

        int mid = row / 2;
        queueE1 = readLines(filename, 0, mid, column);
        System.out.println("Ticket sold for E1: " + queueE1.toString());
        queueE2 = readLines(filename, mid, row, column);
        System.out.println("Ticket sold for E2: " + queueE2.toString());
        System.out.println();

        if (!filename.equals("tickets6.csv")) {
            seatingCoordinate = readLines(filename);
        }

        randomiseQueue(queueE1);
        randomiseQueue(queueE2);

        Scanner input = new Scanner(System.in);
        String enterPressed = "";
        do {
            printQueueMatrix(matrix, row, column);
            System.out.println("\nQueue at E1: " + queueE1.toString());
            System.out.println("Queue at E2: " + queueE2.toString());
            System.out.print("\nPress Enter to serve next spectator in queues...");

            if (filename.equals("tickets6.csv")) {
                updateMatrix(matrix, queueE1.poll());
                updateMatrix(matrix, queueE2.poll());
            } else {
                updateMatrix(matrix, seatingCoordinate, queueE1.poll());
                updateMatrix(matrix, seatingCoordinate, queueE2.poll());
            }

            enterPressed = input.nextLine();

        } while (true && (!queueE1.isEmpty() || !queueE2.isEmpty()));

        printQueueMatrix(matrix, row, column);
        System.out.println("\nQueue at E1: " + queueE1.toString());
        System.out.println("Queue at E2: " + queueE2.toString());
        System.out.print("\nPress Enter to return to home screen...");
        enterPressed = input.nextLine();
    }

    /**
     * Read the ticket.csv file and translate the csv into a coordinate Map
     * consisting the name of customer as the key and seating coordinate as the
     * value for queue simulation
     * 
     * @param filename
     *                 The name of the csv file to be simulated
     * 
     * @return Map<String, String>
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    private static Map<String, String> readLines(String filename) throws IOException {
        Map<String, String> coordinatesMap = new HashMap<>();
        String path = ("C:/Users/User/Downloads/Final4/Final - Latest/" + filename);

        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = 0; i < lines.size(); i++) {
            String[] items = lines.get(i).split("\t\t");
            for (int j = 0; j < items.length; j++) {
                if (items[j].contains(" *")) {
                    // seat not booked
                }
                else if (items[j].contains("*")) {
                    // seat not booked
                }

                else {
                    String coordinate = "";
                    coordinate += i;
                    coordinate += j;
                    coordinatesMap.put(items[j], coordinate);
                }
            }
        }
        return coordinatesMap;
    }

    /**
     * Read the ticket.csv file, starting from and until a specific row, and put the
     * customers' name into a queue
     * 
     * @param filename
     *                 The name of the csv file to be simulated
     * 
     * @param start
     *                 The row number of which to start to read the lines from the
     *                 csv
     * 
     * @param end
     *                 The row number of which to end reading the lines from the csv
     * 
     * @param column
     *                 The number of column of data from the csv file
     * 
     * @return
     *         The queue of customers' name from the csv file in order according to
     *         their seating position
     * 
     * @throws IOException
     *                     If the input filename does not exist or the file path
     *                     is not defined properly according to the system
     */
    private static Queue<String> readLines(String filename, int start, int end,
            int column) throws IOException {
        Queue<String> queue = new LinkedList<>();
        String path = "C:/Users/User/Downloads/Final4/Final - Latest/" + filename;

        List<String> lines = Files.readAllLines(Paths.get(path));
        for (int i = start; i < end; i++) {
            String[] items = lines.get(i).split("\t\t");
            for (int j = 0; j < column; j++)
            if (items[j].contains(" *")) {

                // seat not booked
            }
            else if (items[j].contains("*")) {
                // seat not booked
            }
            else {
                queue.offer(items[j]);

            }
        }
        return queue;
    }

    /**
     * Randomised the order of customers in the queue - simulating the real world
     * case in which customers come in random order
     * 
     * @param queue
     *              the source of queue to be randomised
     */
    private static void randomiseQueue(Queue<String> queue) {
        List<String> qList = new ArrayList<>(queue);
        Collections.shuffle(qList);

        queue.clear();
        for (int i = 0; i < qList.size(); i++) {
            queue.offer(qList.get(i));
        }
    }

    private static void updateMatrix(String[][] matrix, String customer) {
        if (customer == null) {
            // do nothing
        } else {
            int row = (int) customer.charAt(0) - 65;
            // System.out.println("\nrow: " + row);
            int column = customer.charAt(1) - 49;
            // System.out.println("cloumn: " + column);
            matrix[row][column] = "/";
        }
    }

    private static void updateMatrix(String[][] matrix,
            Map<String, String> coordinatesMap, String customer) {

        if (customer == null) {
            // do nothing
        } else {
            customer = coordinatesMap.get(customer);
            int row = customer.charAt(0) - 48;
            int column = customer.charAt(1) - 48;
            matrix[row][column] = "/";
        }
    }

    private static void printQueueMatrix(String[][] matrix, int row, int column) {
        for (int i = 1; i <= column; i++)
            System.out.print("\t" + i);

        for (int i = 0; i < row; i++) {
            if (i == row - 1) {
                System.out.print("E2   " + i + "  ");
                for (int j = 0; j < column - 1; j++) {
                    System.out.print(matrix[i][j] + "\t");
                }
                System.out.println(matrix[i][column - 1] + " |");
            } else if (i == 0) {
                System.out.print("\n    |" + i + "  ");
                for (int j = 0; j < column - 1; j++) {
                    System.out.print(matrix[i][j] + "\t");
                }
                System.out.println(matrix[i][column - 1] + "   E1");
            } else {
                System.out.print("    |" + i + "  ");
                for (int j = 0; j < column - 1; j++) {
                    System.out.print(matrix[i][j] + "\t");
                }
                System.out.println(matrix[i][column - 1] + " |");
            }
        }
    }

}