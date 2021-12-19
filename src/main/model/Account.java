package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Representation of bank account on the app
public class Account {
    Categories ctgs = new Categories();                         // to call methods from categories
    private double balance;                                     // initial balance
    ArrayList<String> transactions = new ArrayList<>();         // list of all transactions made

    /*
     * EFFECTS:  Constructs an account with entered balance, if entered balance
     *           is greater than or equal to 0 then balance on account is set
     *           to entered balance else balance is zero.
     */
    public Account(double balance) { // sets balance
        if (balance >= 0) {
            this.balance = balance;
        } else {
            this.balance = 0;
        }
    }

    /*
     * EFFECTS: Returns balance
     */
    public double getBalance() { // gets balance
        return balance;
    }

    /*
     * EFFECTS: Returns first transaction
     */
    public String getRecentTransactions() { // gets most recent transaction
        if (transactions.size() > 0) {
            return transactions.get(transactions.size() - 1);
        }
        return "";
    }


    /*
     * EFFECTS: Returns total amount of outflow category
     */
    public Double getOutCategoryTotal(String ctgString) {
        if (Objects.equals(ctgString, "transportation")) {
            return ctgs.total(ctgs.transportation);
        } else if (Objects.equals(ctgString, "bills")) {
            return ctgs.total(ctgs.bills);
        } else if (Objects.equals(ctgString, "education")) {
            return ctgs.total(ctgs.education);
        } else if (Objects.equals(ctgString, "entertainment")) {
            return ctgs.total(ctgs.entertainment);
        } else if (Objects.equals(ctgString, "dining")) {
            return ctgs.total(ctgs.dining);
        } else if (Objects.equals(ctgString, "groceries")) {
            return ctgs.total(ctgs.groceries);
        } else if (Objects.equals(ctgString, "transfer")) {
            return ctgs.total(ctgs.transfer);
        } else if (Objects.equals(ctgString, "shopping")) {
            return ctgs.total(ctgs.shopping);
        }
        return null;
    }

    /*
     * EFFECTS: Returns total amount of inflow category
     */
    public Double getInCategoryTotal(String ctgString) {
        if (Objects.equals(ctgString, "salary")) {
            return ctgs.total(ctgs.salary);
        } else if (Objects.equals(ctgString, "returns")) {
            return ctgs.total(ctgs.returns);
        } else if (Objects.equals(ctgString, "loans")) {
            return ctgs.total(ctgs.loans);
        }
        return null;
    }

    /*
     * REQUIRES: expense >= 0, category has a non-zero length and must be valid
     * MODIFIES: this
     * EFFECTS:  expense is subtracted from balance and updated, returns balance,
     * 			 if expense is greater than balance nothing changes
     */
    public void addExpense(double expense, String category) {
        if (balance >= expense) {
            balance = balance - expense;
            transactions.add(ctgs.chooseOutCategory(category) + ": -$" + expense);
            ctgs.addToCategory((-1 * expense), ctgs.chooseOutCategory(category));
        }
        EventLog.getInstance().logEvent(new Event("Added " + category + " Expense of $"
                + expense + " to Account"));
    }


    /*
     * REQUIRES: income >= 0, category has a non-zero length and must be valid
     * MODIFIES: this
     * EFFECTS:  income is added to balance and updated, returns balance
     */
    public void addIncome(double income, String category) {
        balance = balance + income;
        transactions.add(ctgs.chooseInCategory(category) + ": +$" + income);
        ctgs.addToCategory(income, ctgs.chooseInCategory(category));
        EventLog.getInstance().logEvent(new Event("Added " + category + " Income of $"
                + income + " to Account"));
    }

    /*
     * REQUIRES: non-empty list
     * EFFECTS:  prints all string within list
     */
    public String printList(List<String> trans) {
        StringBuilder print = new StringBuilder();
        String separator = " | ";

        for (String s : trans) {
            print.append(s).append(separator);
        }
        return print.toString();
    }

    /*
     * REQUIRES: category has a non-zero length, budgetamt >= 0,
     * MODIFIES: this
     * EFFECTS:  sets budget for selected valid category
     */
    public void setBudget(String category, double amount) {
        ctgs.setBudget(category, amount);

        EventLog.getInstance().logEvent(new Event("Set Budget for " + category + " to $" + amount));
    }

    /*
     * EFFECTS: Returns budget amount of interested category, returns 0 if invalid category
     */
    public double getBudgetAmt(String category) {
        return ctgs.getBudgetAmt(category);
    }

    /*
     * REQUIRES: amount >= 0, category has a non-zero length
     * MODIFIES: this
     * EFFECTS:  adds amount spent on each category to each corresponding category list
     */
    public void addToCategory(double amount, String category) {
        ctgs.addToCategory(amount, category);
    }

    /*
     * REQUIRES: non-empty list
     * EFFECTS:  prints overview of all expenses and income made up to current point
     */
    public String printTransactions(Account acc) {
        return printList(acc.transactions);
    }

    /*
     * EFFECTS:  prints Log
     */
    public void printLog() {
        EventLog eventLog = EventLog.getInstance();
        for (Event e : eventLog) {
            System.out.println(e.toString() + "\n");
        }
    }

    /*
     * EFFECTS:  adds all expenses and income made up to a current point to transactions
     */
    public void addToTransactions(String string, Account acc) {
        acc.transactions.add(string);
    }

    /*
     * EFFECTS: returns this as JSON object
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Balance", balance);
        json.put("Transactions", transToJson());
        json.put("Categories", transToJson2());
        json.put("Budgets", ctgs.transToJson());
        return json;
    }

    /*
     * EFFECTS: returns strings in transactions as a JSON array
     */
    private JSONArray transToJson() {
        JSONArray jsonArray = new JSONArray();

        for (String s : transactions) {
            jsonArray.put(toJsonSub(s));
        }
        return jsonArray;
    }

    /*
     * EFFECTS: returns category lists in categories as a JSON array
     */
    private JSONArray transToJson2() {
        JSONArray jsonArray = new JSONArray();

        for (ArrayList al : ctgs.listOfCategories) {
            jsonArray.put(toJsonSubAl(al));
        }
        return jsonArray;
    }

    /*
     * EFFECTS: returns string in transactions as a JSON array
     */
    public JSONObject toJsonSub(String s) {
        JSONObject json = new JSONObject();
        json.put("Category & Amount", s);
        return json;
    }

    /*
     * EFFECTS: returns category transactions in category lists as a JSON array
     */
    public JSONObject toJsonSubAl(ArrayList al) {
        JSONObject json = new JSONObject();

        for (Object cl : al) {
            searchAndMatch(cl,json);
        }
        return json;
    }

    public void searchAndMatch(Object cl, JSONObject json) {
        if (ctgs.transportation.contains(cl)) {
            json.put("Transportation Transactions", transToJsonArray(ctgs.transportation,cl));
        } else if (ctgs.bills.contains(cl)) {
            json.put("Bills Transactions", transToJsonArray(ctgs.bills,cl));
        } else if (ctgs.education.contains(cl)) {
            json.put("Education Transactions", transToJsonArray(ctgs.education,cl));
        } else if (ctgs.entertainment.contains(cl)) {
            json.put("Entertainment Transactions", transToJsonArray(ctgs.entertainment,cl));
        } else if (ctgs.dining.contains(cl)) {
            json.put("Dining Transactions", transToJsonArray(ctgs.dining,cl));
        } else if (ctgs.groceries.contains(cl)) {
            json.put("Groceries Transactions", transToJsonArray(ctgs.groceries,cl));
        } else if (ctgs.transfer.contains(cl)) {
            json.put("Transfer Transactions", transToJsonArray(ctgs.transfer,cl));
        } else if (ctgs.shopping.contains(cl)) {
            json.put("Shopping Transactions", transToJsonArray(ctgs.shopping,cl));
        } else if (ctgs.salary.contains(cl)) {
            json.put("Salary Transactions", transToJsonArray(ctgs.salary,cl));
        } else if (ctgs.returns.contains(cl)) {
            json.put("Returns Transactions", transToJsonArray(ctgs.returns,cl));
        } else {
            json.put("Loans Transactions", transToJsonArray(ctgs.loans,cl));
        }
    }

    /*
     * EFFECTS: returns multiple category expenses and incomes as JSON arrays
     */
    private JSONArray transToJsonArray(ArrayList<Double> a, Object o) {
        JSONArray jsonArray = new JSONArray();

        for (Object obj : a) {
            jsonArray.put(toJsonSubAlVal(obj));
        }
        return jsonArray;
    }

    /*
     * EFFECTS: returns category expenses and incomes as a JSON object
     */
    public JSONObject toJsonSubAlVal(Object vl) {
        JSONObject json = new JSONObject();
        json.put("Expenses & Incomes", vl);
        return json;
    }

}
