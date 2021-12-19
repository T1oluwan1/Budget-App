package oldui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.Account;
import model.Categories;
import persistence.JsonReader;
import persistence.JsonWriter;

//  BudgetApp/ExpenseTracker
public class OldBudgetAppUI {
    private Account account;
    private Categories categories;

    private static final String JSON_STORE = "./data/account.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Scanner input;

    /* EFFECTS: Runs Budget App */
    public OldBudgetAppUI() {
        runApplication();
    }


    /* MODIFIES: this
     * EFFECTS:  process user input */
    //
    private void runApplication() {
        boolean running = true;
        String directive;

        initialization();

        while (running) {
            mainMenu();
            directive = input.next();
            directive = directive.toLowerCase();

            if (directive.equals("esc")) {
                running = false;
            } else {
                processDirective(directive);
            }
        }
        System.out.println("Thank you for your service! :) See you again!");
    }


    /* MODIFIES: this
     * EFFECTS:  initialize account */
    //
    private void initialization() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        System.out.println("Enter Initial Balance Amount $ ");
        account = new Account(input.nextDouble());
        categories = new Categories();
    }


    /* EFFECTS:  displays main menu of available options */
    //
    private void mainMenu() {
        System.out.println("\nSelect an option:");
        System.out.println("\tEnter [b] to set a budget");
        System.out.println("\tEnter [e] to add an expense");
        System.out.println("\tEnter [?] to get info on a category");
        System.out.println("\tEnter [i] to add a source of income");
        System.out.println("\tEnter [p] to print all current transactions");
        System.out.println("\tEnter [l] to print list of all valid categories");
        System.out.println("\tEnter [+] to save current state of App to file");
        System.out.println("\tEnter [=] to load previous state of App from file");
        System.out.println("\tEnter [esc] to end this experience");
    }


    /* MODIFIES: this
     * EFFECTS:  process user commands */
    //
    private void processDirective(String directive) {
        if (directive.equals("b")) {
            setABudget();
        } else if (directive.equals("e")) {
            addAnExpense();
        } else if (directive.equals("?")) {
            categoryInfo();
        } else if (directive.equals("i")) {
            addAnIncome();
        } else if (directive.equals("p")) {
            printCurrentTransactions();
        } else if (directive.equals("l")) {
            validCategories();
        } else if (directive.equals("+")) {
            saveAccountTransactions();
        } else if (directive.equals("=")) {
            loadAccountTransactions();
        } else {
            System.out.println("Invalid Selection :/");
        }
    }


    /* MODIFIES: this
     * EFFECTS:  sets a budget */
    //
    private void setABudget() {
        System.out.println("Enter budget category");
        String category = input.next().toLowerCase();
        System.out.println("Enter budget amount $");
        double amount = input.nextDouble();

        if (categories.checkNotValidOutCategory(category)) {
            System.out.println("Not a valid category, try again.");
        } else if (amount < 0.0) {
            System.out.println("Negative number!!! Budgets can't be negative silly XD?");
        } else {
            account.setBudget(category, amount);
        }
        System.out.println("Budget for " + category + " is now set to $" + amount);
        checkBudget(category);
    }


    /* EFFECTS:  checks if selected category budget is being adhered to */
    //
    private void checkBudget(String category) {
        if (Math.abs(account.getOutCategoryTotal(category)) < account.getBudgetAmt(category)) {
            System.out.println("Congrats, you are within your " + category + " budget :)");

        } else if (Math.abs(account.getOutCategoryTotal(category)) == account.getBudgetAmt(category)) {
            System.out.println("You are right on the border of your " + category
                    + " budget, no more expenses recommended :/");

        } else {
            System.out.println("You are over your " + category + " budget :C");
        }
    }


    /* MODIFIES: this
     * EFFECTS:  adds an expense */
    //
    private void addAnExpense() {
        System.out.println("Enter expense amount $");
        double amount = input.nextDouble();
        System.out.println("Enter expense category");
        String category = input.next().toLowerCase();

        if (amount < 0.0) {
            System.out.println("Negative number!!! Did you type that by accident?");
        } else if (account.getBalance() < amount) {
            System.out.println("Error!!! Expense exceeds Current Balance");
        } else if (categories.checkNotValidOutCategory(category)) {
            System.out.println("Not a valid category, try again.");
        } else {
            account.addExpense(amount, category);
        }
        System.out.println("Your account balance is currently $" + account.getBalance());
        checkBudget(category);
    }


    /* MODIFIES: this
     * EFFECTS:  adds a source of income  */
    //
    private void addAnIncome() {
        System.out.println("Enter income amount $");
        double amount = input.nextDouble();

        System.out.println("Enter income category");
        String category = input.next().toLowerCase();

        if (amount < 0.0) {
            System.out.println("Negative number!!! Did you type that by accident?");
        } else if (categories.checkNotValidInCategory(category)) {
            System.out.println("Not a valid category, try again.");
        } else {
            account.addIncome(amount, category);
        }
        System.out.println("Your current balance is $" + account.getBalance());
    }


    /* EFFECTS:  provides total amount spent and budget amount
     *           (budget only applies to outflow) of a category */
    //
    private void categoryInfo() {
        System.out.println("\nincome or expense categories");
        System.out.println("\tEnter [i] for income categories");
        System.out.println("\tEnter [e] for expense categories");

        String choice;
        choice = input.next();
        choice = choice.toLowerCase();

        if (choice.equals("i")) {
            categoryInfoIncome();

        } else if (choice.equals("e")) {
            categoryInfoExpense();

        } else {
            System.out.println("Invalid Selection :/");
        }
    }


    /* EFFECTS:  provides total amount spent of category */
    //
    private void categoryInfoIncome() {
        System.out.println("Enter income category");
        String category = input.next().toLowerCase();

        if (categories.checkNotValidInCategory(category)) {
            System.out.println("Not a valid category, try again.");
        } else {
            System.out.println("Total amount earned by " + category + " is : $" + account.getInCategoryTotal(category));
        }
    }


    /* EFFECTS:  provides total amount spent and budget amount of category */
    //
    private void categoryInfoExpense() {
        System.out.println("Enter expense category");
        String category = input.next().toLowerCase();

        if (categories.checkNotValidOutCategory(category)) {
            System.out.println("Not a valid category, try again.");
        } else {
            System.out.println("Total amount lost by " + category + " is : $" + account.getOutCategoryTotal(category));
            System.out.println("Budget = $" + account.getBudgetAmt(category));
            checkBudget(category);
        }
    }


    /* EFFECTS:  prints all current transactions */
    //
    private void printCurrentTransactions() {
        System.out.println("\nTransaction:");
        System.out.println(account.printTransactions(account));
    }


    /* EFFECTS:  produces list of all valid income categories if 'i' entered
     *           produces list of all valid income categories if 'e' entered
     *           invalid selection else. */
    //
    private void validCategories() {
        System.out.println("\nincome or expense categories");
        System.out.println("\tEnter [i] for income categories");
        System.out.println("\tEnter [e] for expense categories");

        String choice;
        choice = input.next();
        choice = choice.toLowerCase();

        if (choice.equals("i")) {
            System.out.println("salary, returns, loans");
        } else if (choice.equals("e")) {
            System.out.println("transportations, bills, education, entertainment, "
                    + "dining, groceries, transfer, shopping");
        } else {
            System.out.println("Invalid Selection :/");
        }
    }

    // EFFECTS: saves account transactions to file
    private void saveAccountTransactions() {
        try {
            jsonWriter.open();
            jsonWriter.write(account, categories);
            jsonWriter.close();
            System.out.println("Saved Account to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account transactions from file
    private void loadAccountTransactions() {
        try {
            account = jsonReader.read();
            System.out.println("Loaded Account from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
    // FUTURE PLANS:

    // implement colours (red for over balance, green for under, yellow for on)

    // add persistence for budget and category info


}

