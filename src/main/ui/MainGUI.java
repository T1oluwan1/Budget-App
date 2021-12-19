package ui;

import model.Account;
import model.Categories;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

//  BudgetApp/ExpenseTracker Graphical Interface
public class MainGUI {

    JLabel label;
    JLabel userBalance;
    JLabel userBudget;
    JLabel systemVoice;
    JLabel amount;
    JTextField userAmountInput;
    JLabel category;
    JTextField userCategoryInput;
    JFrame frame;

    private Account account;                                                             // to call methods from account
    private Categories categories;                                                       // to call methods from ctgs

    private static final String JSON_STORE = "./data/account.json";                      // location data is sent

    private JsonWriter jsonWriter;                                                       // to save data
    private JsonReader jsonReader;                                                       // to load data

    JButton budgetButton;                                                                // initializes budget button
    JButton categoriesButton;                                                            // initializes category button
    JButton expenseButton;                                                               // initializes expense button
    JButton incomeButton;                                                                // initializes income button
    JButton infoButton;                                                                  // initializes info button
    JButton printButton;                                                                 // initializes print button
    JButton saveButton;                                                                  // initializes save button
    JButton loadButton;                                                                  // initializes load button
    JButton exitButton;                                                                  // initializes exit button


    // Constructs main window
    public MainGUI() {

        ImageIcon image = new ImageIcon("src/main/images/istockphoto-1135787086-612x612.jpg");
        Border border = BorderFactory.createLineBorder(new Color(20, 71, 161), 5);

        label = new JLabel("Budget App");
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(new Color(14, 52, 245));
        label.setFont(new Font("Helvetica", Font.PLAIN, 50));
        label.setIconTextGap(-125);
        label.setBorder(border);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(500, 0, 500, 500);

        initialization();
        setUpUserMechanisms();
        setUpButtons();
        setUpFiles();
        setUpSystem();
        setUpFrames();
        frame.getContentPane().setBackground(new Color(20, 80, 69));
    }


    /* MODIFIES: this
     * EFFECTS:  sets a budget */
    //
    private void setABudget() {
        double amount = Integer.parseInt(userAmountInput.getText());
        String category = userCategoryInput.getText().toLowerCase();

        if (categories.checkNotValidOutCategory(category)) {
            JOptionPane.showMessageDialog(null,"Not a valid category, try again.","Error",
                    JOptionPane.INFORMATION_MESSAGE);
            systemVoice.setText("Not a valid category, try again.");
        } else if (amount < 0.0) {
            JOptionPane.showMessageDialog(null,
                    "Negative number!!! Budgets can't be negative silly XD?","Error",
                    JOptionPane.INFORMATION_MESSAGE);
            systemVoice.setText("Negative number!!! Budgets can't be negative silly XD?");
        } else {
            account.setBudget(category, amount);
            systemVoice.setText("Budget for " + category + " is now set to $" + amount);
            checkBudget(category);
        }
    }


    /* REQUIRES: non-empty string + valid category
    // EFFECTS : checks if selected category budget is being adhered to */
    //
    private void checkBudget(String category) {
        if (Math.abs(account.getOutCategoryTotal(category)) < account.getBudgetAmt(category)) {
            userBudget.setText("Congrats, you are within your " + category + " budget :)");

        } else if (Math.abs(account.getOutCategoryTotal(category)) == account.getBudgetAmt(category)) {
            userBudget.setText("You are right on the border of your " + category
                    + " budget, no more expenses recommended :/");

        } else {
            userBudget.setText("You are over your " + category + " budget :C");
        }
    }


    /* MODIFIES: this
     * EFFECTS:  adds an expense */
    //
    private void addAnExpense() {
        double amount = Integer.parseInt(userAmountInput.getText());
        String category = userCategoryInput.getText().toLowerCase();

        if (amount < 0.0) {
            JOptionPane.showMessageDialog(null,
                    "Negative number!!! Did you type that by accident?","Error",
                    JOptionPane.INFORMATION_MESSAGE);
            systemVoice.setText("Negative number!!! Did you type that by accident?");
        } else if (account.getBalance() < amount) {
            JOptionPane.showMessageDialog(null,
                    "Error!!! Expense exceeds Current Balance","Error",
                    JOptionPane.INFORMATION_MESSAGE);
            systemVoice.setText("Error!!! Expense exceeds Current Balance");
        } else if (categories.checkNotValidOutCategory(category)) {
            JOptionPane.showMessageDialog(null,
                    "Not a valid category, try again.","Error",
                    JOptionPane.INFORMATION_MESSAGE);
            systemVoice.setText("Not a valid category, try again.");
        } else {
            account.addExpense(amount, category);
            userBalance.setText("Your current balance is $" + account.getBalance());
            checkBudget(category);
        }
    }


    /* MODIFIES: this
     * EFFECTS:  adds a source of income  */
    //
    private void addAnIncome() {
        double amount = Integer.parseInt(userAmountInput.getText());
        String category = userCategoryInput.getText().toLowerCase();

        if (amount < 0.0) {
            JOptionPane.showMessageDialog(null,
                    "Negative number!!! Did you type that by accident?","Error",
                    JOptionPane.INFORMATION_MESSAGE);
            systemVoice.setText("Negative number!!! Did you type that by accident?");
        } else if (categories.checkNotValidInCategory(category)) {
            JOptionPane.showMessageDialog(null,
                    "Not a valid category, try again.","Error",
                    JOptionPane.INFORMATION_MESSAGE);
            systemVoice.setText("Not a valid category, try again.");
        } else {
            account.addIncome(amount, category);
            userBalance.setText("Your current balance is $" + account.getBalance());
        }
    }


    /* EFFECTS:  prints all current transactions */
    //
    private void printCurrentTransactions() {
        systemVoice.setText("\nTransaction: " + account.printTransactions(account));
    }


    /* EFFECTS: provides list of valid categories */
    //
    private void validCategories() {
        systemVoice.setText("\nIncomes: salary, returns, loans"
                + "  \nExpenses: transportations, bills, education, entertainment,"
                + "dining, groceries, transfer, shopping");
    }


    /* EFFECTS: saves account transactions to file */
    //
    private void saveAccountTransactions() {
        try {
            jsonWriter.open();
            jsonWriter.write(account, categories);
            jsonWriter.close();
            systemVoice.setText("Saved Account to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to write to file: ","Error",
                    JOptionPane.INFORMATION_MESSAGE);
            systemVoice.setText("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account transactions from file
    private void loadAccountTransactions() {
        try {
            account = jsonReader.read();
            systemVoice.setText("Loaded Account from " + JSON_STORE);
            userBalance.setText("Your current balance is $" + account.getBalance());
            userBudget.setText("Set Budgets have been restored");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,
                    "Unable to read to file: ","Error",
                    JOptionPane.INFORMATION_MESSAGE);
            systemVoice.setText("Unable to write to file: " + JSON_STORE);
        }
    }

//    private void categoryInfo() {
//        String category = userCategoryInput.getText().toLowerCase();
//
//        if (categories.checkNotValidCategory(category)) {
//            systemVoice.setText("Not a valid category, try again.");
//        } else if (categories.checkNotValidOutCategory(category)) {
//            systemVoice.setText("Total amount lost by "
//                    + category + " is : $" + account.getOutCategoryTotal(category));
//            System.out.println("Budget = $" + account.getBudgetAmt(category));
//            checkBudget(category);
//        } else {
//            systemVoice.setText("Total amount earned by "
//                    + category + " is : $" + account.getInCategoryTotal(category));
//        }
//    }


    /* MODIFIES: this
     * EFFECTS:  initialize state of the app */
    //
    private void initialization() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        account = new Account(0);
        categories = new Categories();
    }

    /* MODIFIES: this
     * EFFECTS:  initializes user interaction mechanisms */
    //
    public void setUpUserMechanisms() {
        userBalance = new JLabel("Your Initial Balance is $" + account.getBalance());
        userBalance.setBounds(40, 25, 1000, 25);
        userBalance.setForeground(new Color(255, 255, 255));
        userBalance.setFont(new Font("Helvetica", Font.PLAIN, 20));

        amount = new JLabel("Amount");
        amount.setBounds(215, 90, 125, 25);
        amount.setForeground(new Color(255, 255, 255));
        amount.setFont(new Font("Helvetica", Font.PLAIN, 18));

        userAmountInput = new JTextField();
        userAmountInput.setBounds(160, 115, 175, 30);
        userAmountInput.setFont(new Font("Helvetica", Font.PLAIN, 18));

        category = new JLabel("Category");
        category.setBounds(210, 145, 125, 25);
        category.setForeground(new Color(255, 255, 255));
        category.setFont(new Font("Helvetica", Font.PLAIN, 18));

        userCategoryInput = new JTextField();
        userCategoryInput.setBounds(160, 170, 175, 30);
        userCategoryInput.setFont(new Font("Helvetica", Font.PLAIN, 18));
    }


    /* MODIFIES: this
     * EFFECTS:  initializes main buttons for app */
    //
    public void setUpButtons() {
        budgetButton = new JButton("Set Budget");
        budgetButton.addActionListener(e -> setABudget());
        budgetButton.setBounds(40, 210, 200, 50);
        budgetButton.setFocusable(false);
        budgetButton.setFont(new Font("Helvetica", Font.PLAIN, 20));

        categoriesButton = new JButton("Valid Categories");
        categoriesButton.addActionListener(e -> validCategories());
        categoriesButton.setBounds(260, 210, 200, 50);
        categoriesButton.setFocusable(false);
        categoriesButton.setFont(new Font("Helvetica", Font.PLAIN, 20));

        expenseButton = new JButton("Add Expense");
        expenseButton.addActionListener(e -> addAnExpense());
        expenseButton.setBounds(40, 270, 200, 50);
        expenseButton.setFocusable(false);
        expenseButton.setFont(new Font("Helvetica", Font.PLAIN, 20));

        incomeButton = new JButton("Add Income");
        incomeButton.addActionListener(e -> addAnIncome());
        incomeButton.setBounds(260, 270, 200, 50);
        incomeButton.setFocusable(false);
        incomeButton.setFont(new Font("Helvetica", Font.PLAIN, 20));

    }


    /* MODIFIES: this
     * EFFECTS:  initializes files used in persistance */
    //
    public void setUpFiles() {
        infoButton = new JButton("Category Info");
        infoButton.addActionListener(e -> System.out.println("INFO NOT YET IMPLEMENTED"));
        infoButton.setBounds(40, 330, 200, 50);
        infoButton.setFocusable(false);
        infoButton.setFont(new Font("Helvetica", Font.PLAIN, 20));

        printButton = new JButton("Print Transactions");
        printButton.addActionListener(e -> printCurrentTransactions());
        printButton.setBounds(260, 330, 200, 50);
        printButton.setFocusable(false);
        printButton.setFont(new Font("Helvetica", Font.PLAIN, 20));

        saveButton = new JButton("Save File");
        saveButton.addActionListener(e -> saveAccountTransactions());
        saveButton.setBounds(40, 390, 200, 50);
        saveButton.setFocusable(false);
        saveButton.setFont(new Font("Helvetica", Font.PLAIN, 20));

        loadButton = new JButton("Load File");
        loadButton.addActionListener(e -> loadAccountTransactions());
        loadButton.setBounds(260, 390, 200, 50);
        loadButton.setFocusable(false);
        loadButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
    }


    /* MODIFIES: this
     * EFFECTS:  initializes system response and react mechanisms */
    //
    public void setUpSystem() {
        userBudget = new JLabel("All current Budgets set to $ 0.0");
        userBudget.setBounds(75, 525, 2000, 50);
        userBudget.setForeground(new Color(255, 255, 255));
        userBudget.setFont(new Font("Helvetica", Font.PLAIN, 24));

        systemVoice = new JLabel("...");
        systemVoice.setBounds(75, 550, 5000, 100);
        systemVoice.setForeground(new Color(175, 206, 39));
        systemVoice.setFont(new Font("Helvetica", Font.PLAIN, 25));

        exitButton = new JButton("End Experience");
        exitButton.addActionListener(e -> exit());
        exitButton.setBounds(150, 450, 200, 50);
        exitButton.setFocusable(false);
        exitButton.setFont(new Font("Helvetica", Font.PLAIN, 20));
    }

    public void exit() {
        account.printLog();

        System.exit(0);
    }


    /* MODIFIES: this
     * EFFECTS:  initializes frames for the graphical interface */
    //
    public void setUpFrames() {
        frame = new JFrame();
        frame.setTitle("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(1000, 750);
        frame.setVisible(true);
        frame.add(label);
        frame.add(userBalance);
        frame.add(userBudget);
        frame.add(userAmountInput);
        frame.add(userCategoryInput);
        frame.add(budgetButton);
        frame.add(categoriesButton);
        frame.add(expenseButton);
        frame.add(incomeButton);
        frame.add(infoButton);
        frame.add(printButton);
        frame.add(saveButton);
        frame.add(loadButton);
        frame.add(exitButton);
        frame.add(amount);
        frame.add(category);
        frame.add(systemVoice);
    }

    // Runs the BudgetApp
    public static void main(String[] args) {
        new MainGUI();
    }
}
