import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainJavaFx extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    // Label and TextFields for Sign Up
    private Label id = new Label("Enter 6 digits pin: ");
    private TextField tfId = new TextField();
    private Label name = new Label("Enter name: ");
    private TextField tfName = new TextField();

    // Label and TextFields for Customer Login
    private Label id2 = new Label("Enter 6 digits pin: ");
    private TextField tfId2 = new TextField();
    private Label name2 = new Label("Enter name: ");
    private TextField tfName2 = new TextField();

    // Label and TextFields for Staff Login 
    private Label id3 = new Label("Enter 6 digits pin: ");
    private TextField tfId3 = new TextField();
    private Label name3 = new Label("Enter name: ");
    private TextField tfName3 = new TextField();

    // Button
    Button customerSignUpButton = new Button("Customer Sign Up");
    Button customerLoginButton = new Button("Customer Login");
    Button staffLoginButton = new Button("Staff Login");
    Button backButton = new Button("Back");
    Button backButton2 = new Button("Back");
    Button backButton3 = new Button("Back");
    Button signUpButton = new Button ("Register Account");
    Button loginButton = new Button ("Login");
    Button loginButton2 = new Button ("Login Account");
    Button loginButton3 = new Button ("Login Account");
    Button searchAndBookButton = new Button ("Search and Book");
    Button viewMatchesButton = new Button ("View all Matches");
    Button logOutButton = new Button ("Log Out");
    Button addMatchButton = new Button ("Add Match details");
    Button viewCustomerAndSeatsButton = new Button ("View Customers and Seats");
    Button logOutButton2 = new Button ("Log Out");

    public void start(Stage primaryStage) {

        // Instantiating VBox class
        VBox vBox = new VBox();
        vBox.setSpacing(20);
        vBox.setAlignment(Pos.CENTER);
        
        Text tStart = new Text();
        tStart.setText("Start Menu Page");

        vBox.getChildren().addAll(tStart, customerSignUpButton, customerLoginButton, staffLoginButton);

        // Creating Start Menu Scene
        Scene scene = new Scene(vBox, 500, 500);
        primaryStage.setTitle("World Cup 2026 Ticketing System");
        primaryStage.setScene(scene);
        primaryStage.show();

        // CUSTOMER SIGN UP 
        GridPane signUpGridPane = new GridPane();
        
        signUpGridPane.setAlignment(Pos.CENTER);
        signUpGridPane.setHgap(1);
        signUpGridPane.setVgap(20);

        Text tSignUp = new Text();
        tSignUp.setText("Sign Up Form");
        signUpGridPane.add (tSignUp, 1, 0);

        signUpGridPane.add (id, 1, 1);
        signUpGridPane.add (tfId, 2, 1);
        signUpGridPane.add (name, 1, 2);
        signUpGridPane.add (tfName, 2, 2);
        signUpGridPane.add (signUpButton, 2, 3);
        signUpGridPane.add (loginButton, 2, 4);
        signUpGridPane.add (backButton, 1, 3);

        Scene signUpScene = new Scene (signUpGridPane, 500, 500);

        // Sign Up Button Action, save data to csv
        signUpButton.setOnAction(event -> {
            try {
                String stringId = tfId.getText();
                int id = Integer.parseInt(stringId); // convert string to int
                String name = tfName.getText();

                    try {
                        signUpInformation(id, name);
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            catch (Exception e) {
                System.out.println("Register Error");
            }
        });

        GridPane loginGridPane = new GridPane();
        Scene customerLoginScene = new Scene (loginGridPane, 500, 500);
        
        loginGridPane.setAlignment(Pos.CENTER);
        loginGridPane.setHgap(1);
        loginGridPane.setVgap(20);

        Text tLogin = new Text();
        tLogin.setText("Login Form");
        loginGridPane.add (tLogin, 1, 0);

        loginGridPane.add (id2, 1, 1);
        loginGridPane.add (tfId2, 2, 1);
        loginGridPane.add (name2, 1, 2);
        loginGridPane.add (tfName2, 2, 2);
        loginGridPane.add (loginButton2, 2, 12);
        loginGridPane.add (backButton2, 1, 12);

        // Login Button Action, go to login scene
        loginButton.setOnAction(e -> primaryStage.setScene(customerLoginScene));
        
        // Instantiating VBox class
        VBox vBox2 = new VBox();
        vBox2.setSpacing(20);
        vBox2.setAlignment(Pos.CENTER);
        
        Text tCustomerMenu = new Text();
        tCustomerMenu.setText("Customer Menu Page");

        vBox2.getChildren().addAll(tCustomerMenu, searchAndBookButton, viewMatchesButton, logOutButton);

        // Creating Customer Menu Scene
        Scene customerMenuScene = new Scene(vBox2, 500, 500);

        // Login Button Action, validate account 
        loginButton2.setOnAction(event -> {
            try {
                String stringId2 = tfId2.getText();
                int id2 = Integer.parseInt(stringId2); // convert string to int
                String name2 = tfName2.getText();
 
                    try {
                        customerLogin(id2, name2);
                        if(true) {
                            loginButton2.setOnAction(e -> primaryStage.setScene(customerMenuScene));
                        }
                    }
                    catch (IOException e) { // need to fix this part
                        Stage stage = new Stage();
                        stage.setTitle("Login Failed");
                        stage.setScene(new Scene(new Button ("Incorrect password and name"),350,100));
                        stage.show();
                    }
            }
            catch (Exception e) { // need to fix this part 
                Stage stage = new Stage();
                stage.setTitle("Login Failed");
                stage.setScene(new Scene(new Button ("Incorrect password and name"),350,100));
                stage.show();
            }
        });

        GridPane loginGridPane2 = new GridPane();
        Scene staffLoginScene = new Scene (loginGridPane2, 500, 500);
        
        loginGridPane2.setAlignment(Pos.CENTER);
        loginGridPane2.setHgap(1);
        loginGridPane2.setVgap(20);

        Text tLogin2 = new Text();
        tLogin2.setText("Login Form");
        loginGridPane2.add (tLogin2, 1, 0);

        loginGridPane2.add (id3, 1, 1);
        loginGridPane2.add (tfId3, 2, 1);
        loginGridPane2.add (name3, 1, 2);
        loginGridPane2.add (tfName3, 2, 2);
        loginGridPane2.add (loginButton3, 2, 12);
        loginGridPane2.add (backButton3, 1, 12);

        // STAFF MENU PAGE
        VBox vBox3 = new VBox();
        vBox3.setSpacing(20);
        vBox3.setAlignment(Pos.CENTER);
        
        Text tStaffMenu = new Text();
        tStaffMenu.setText("Staff Menu Page");

        vBox3.getChildren().addAll(tStaffMenu, addMatchButton, viewCustomerAndSeatsButton, logOutButton2);
        
        // Creating Customer Menu Scene
        Scene staffMenuScene = new Scene(vBox3, 500, 500);

        // Login Button Action, validate account 
        loginButton3.setOnAction(event -> {
            try {
                String stringId3 = tfId3.getText();
                int id3 = Integer.parseInt(stringId3); // convert string to int
                String name3 = tfName3.getText();
 
                    try {
                        staffLogin(id3, name3);
                        if(true) {
                            loginButton3.setOnAction(e -> primaryStage.setScene(staffMenuScene));
                        }
                    }
                    catch (IOException e) { // need to fix this part
                        Stage stage = new Stage();
                        stage.setTitle("Login Failed");
                        stage.setScene(new Scene(new Button ("Incorrect password and name"),350,100));
                        stage.show();
                    }
            }
            catch (Exception e) { // need to fix this part
                Stage stage = new Stage();
                stage.setTitle("Login Failed");
                stage.setScene(new Scene(new Button ("Incorrect password and name"),350,100));
                stage.show();
            }
        });
          
        // Button Action
        customerSignUpButton.setOnAction(e -> primaryStage.setScene(signUpScene));
        customerLoginButton.setOnAction(e -> primaryStage.setScene(customerLoginScene));
        staffLoginButton.setOnAction(e -> primaryStage.setScene(staffLoginScene));
        backButton.setOnAction(e -> primaryStage.setScene(scene));
        backButton2.setOnAction(e -> primaryStage.setScene(scene));
        backButton3.setOnAction(e -> primaryStage.setScene(scene));

    }

    // Methods - Save Info
    public static void signUpInformation (int id, String name) throws IOException {
        ArrayList<Customer> customers = readCustomerFromFile();

        int newId = id;
        String newName = name;

        // Check Value Exist
        boolean idExist = false;
            for (int i = 0; i < customers.size(); i++) {
                Customer currentCustomer = customers.get(i);
                // reject id if it already exists            
                if (newId == currentCustomer.getId()) {
                    Stage stage = new Stage();
                    stage.setTitle("Error");
                    stage.setScene(new Scene(new Button ("Cannot add id " + newId + " because it already exists."),350,100));
                    stage.show();
                    idExist = true;
                }
            }

        // Save Value
        customers.add (new Customer (newId, newName));
        if (idExist == false) {
            Stage stage = new Stage();
            stage.setTitle("Success");
            stage.setScene(new Scene(new Button ("Id " + newId + " successfully created."),350,100));
            stage.show();

            saveCustomerToFile (customers);
        }
    }

    // Methods - Read Customer File
    private static ArrayList<Customer> readCustomerFromFile() throws IOException {
        ArrayList<Customer> customers = new ArrayList<>();

        // read customer.csv into a list of lines.
        List<String> lines = Files.readAllLines(Paths.get("C:/Users/User/Desktop/Java MMU/Materials/Assignment/project/Final/customer.csv"));
        
        for(int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            // items[0] is id, items[1] is name
            int id = Integer.parseInt(items[0]);
            customers.add (new Customer(id, items[1]));
        }

        return customers;
    }
    
    // Methods - Save Customer File
    private static void saveCustomerToFile(ArrayList<Customer> customers) throws IOException {
        // read .csv into list of lines.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customers.size(); i++)
            sb.append (customers.get(i).toCSVString() + "\n");
        Files.write(Paths.get("C:/Users/User/Desktop/Java MMU/Materials/Assignment/project/Final/customer.csv"), sb.toString().getBytes());
    }

    // Methods - customerLogin
    private static boolean customerLogin(int id, String name) throws IOException {
        
        ArrayList<Customer> customers = loginCustomerFromFile();
    
            int newId = id;
            String newName = name;

        do {
            for (int i = 0; i < customers.size(); i++) {
                Customer currentCustomer = customers.get(i); 
                // reject if either name or password is wrong or both are wrong  
                if (newId == currentCustomer.getId() && newName.equals (currentCustomer.getName())) {
                    Stage stage = new Stage();
                    stage.setTitle("Login Success");
                    stage.setScene(new Scene(new Button ("Correct password and name"),350,100));
                    stage.show();
                    return true;
                }
                else if (newId == currentCustomer.getId() || newName.equals (currentCustomer.getName())) {
                    Stage stage = new Stage();
                    stage.setTitle("Login Failed");
                    stage.setScene(new Scene(new Button ("Incorrect password and name"),350,100));
                    stage.show();
                    return true;
                }
            }

        } while(true);
    }

    protected static ArrayList<Customer> loginCustomerFromFile() throws IOException {
        ArrayList<Customer> customers = new ArrayList<>();

        List<String> lines = Files.readAllLines(Paths.get("C:/Users/User/Desktop/Java MMU/Materials/Assignment/project/Final/customer.csv"));
        
        for (int i = 0; i < lines.size(); i++) {
            // split a line by comma
            String[] items = lines.get(i).split(",");
            // items[0] is id, items[1] is name
            int id = Integer.parseInt(items[0]); // convert String to int
            customers.add (new Customer(id, items[1]));
        }
        return customers;
    }

    // Methods - staffLogin
    private static boolean staffLogin(int id, String name) throws IOException {
        
        int newId = id;
        String newName = name;
        int staffId = 000000;
        String staffName = "Admin";

        do {
            if (newId == staffId && newName.equals (staffName)) {
                Stage stage = new Stage();
                stage.setTitle("Login Success");
                stage.setScene(new Scene(new Button ("Correct password and name"),350,100));
                stage.show();
                return true;
            }
            else if (newId == staffId || newName.equals (staffName)) {
                Stage stage = new Stage();
                stage.setTitle("Login Failed");
                stage.setScene(new Scene(new Button ("Incorrect password and name"),350,100));
                stage.show();
                return true;
            }
        }while(true);
    }

}
