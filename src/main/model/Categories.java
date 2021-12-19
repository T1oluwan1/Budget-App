package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

// Representation of Spending and Earning Categories
public class Categories {
    ArrayList<ArrayList> listOfCategories = new ArrayList<>();                // list of category expenses
    ArrayList<String> listOfInCategories = new ArrayList<>();                 // list of inflow categories
    ArrayList<String> listOfOutCategories = new ArrayList<>();                // list of outflow categories
    ArrayList<String> listOfAllCategories = new ArrayList<>();                // list of all categories
    ArrayList<Double> transportation = new ArrayList<>();                     // list of all transportation expenses
    ArrayList<Double> bills = new ArrayList<>();                              // list of all bill expenses
    ArrayList<Double> education = new ArrayList<>();                          // list of all education expenses
    ArrayList<Double> entertainment = new ArrayList<>();                      // list of all entertainment expenses
    ArrayList<Double> dining = new ArrayList<>();                             // list of all dining expenses
    ArrayList<Double> groceries = new ArrayList<>();                          // list of all grocery expenses
    ArrayList<Double> transfer = new ArrayList<>();                           // list of all transfer expenses
    ArrayList<Double> shopping = new ArrayList<>();                           // list of all shopping expenses
    ArrayList<Double> salary = new ArrayList<>();                             // list of all salary incomes
    ArrayList<Double> returns = new ArrayList<>();                            // list of all return incomes
    ArrayList<Double> loans = new ArrayList<>();                              // list of all loan incomes

    ArrayList<Double> budgets = new ArrayList<>();                            // list of all current budgets
    private double transportationBgt;                                         // current transportation budget
    private double billsBgt;                                                  // current bills budget
    private double educationBgt;                                              // current education budget
    private double entertainmentBgt;                                          // current entertainment budget
    private double diningBgt;                                                 // current dining budget
    private double groceriesBgt;                                              // current groceries budget
    private double transferBgt;                                               // current transfer budget
    private double shoppingBgt;                                               // current shopping budget


    /*
     * MODIFIES: this
     * EFFECTS:  Represents categories, adds inflow & outflow categories to their corresponding lists
     */
    public Categories() {
        listOfOutCategories.add("transportation");
        listOfOutCategories.add("bills");
        listOfOutCategories.add("education");
        listOfOutCategories.add("entertainment");
        listOfOutCategories.add("dining");
        listOfOutCategories.add("groceries");
        listOfOutCategories.add("transfer");
        listOfOutCategories.add("shopping");

        listOfInCategories.add("salary");
        listOfInCategories.add("returns");
        listOfInCategories.add("loans");

        listOfCategories.add(transportation);
        listOfCategories.add(bills);
        listOfCategories.add(education);
        listOfCategories.add(entertainment);
        listOfCategories.add(dining);
        listOfCategories.add(groceries);
        listOfCategories.add(transfer);
        listOfCategories.add(shopping);
        listOfCategories.add(salary);
        listOfCategories.add(returns);
        listOfCategories.add(loans);
    }

    /*
     * REQUIRES: non-empty list
     * EFFECTS:  produces total sum of values in the list
     */
    public Double total(ArrayList<Double> ctg) {
        double sum = 0;
        for (Double d : ctg) {
            sum += d;
        }
        return sum;
    } //Modeled after: https://stackoverflow.com/questions/16242733/sum-all-the-elements-java-arraylist


    /*
     * REQUIRES: category has a non-zero length
     * EFFECTS:  If outflow category is valid, returns category else labels "other"
     */
    public String chooseOutCategory(String category) { // Determines if category(outflows) is valid else labelled other
        if (listOfOutCategories.contains(category)) {
            return category;
        }
        //System.out.println("Invalid category selected, does not exist");
        return "other";
    }

    /*
     * REQUIRES: category has a non-zero length
     * EFFECTS:  If Inflow category is valid, returns category else labels "other"
     */
    public String chooseInCategory(String category) { // Determines if category(inflows) is valid else labelled other
        if (listOfInCategories.contains(category)) {
            return category;
        }
        //System.out.println("Invalid category selected, does not exist");
        return "other";
    }

    /*
     * REQUIRES: amount >= 0, category has a non-zero length
     * MODIFIES: this
     * EFFECTS:  adds amount spent on each category to each corresponding category list
     */
    public void addToCategory(double amount, String category) {
        addToCategories(amount, category);
    }

    /*
     * REQUIRES: amount >= 0, category has a non-zero length
     * MODIFIES: this
     * EFFECTS:  adds amount spent on each category to each corresponding category list
     */
    public void addToCategories(double amount, String category) {
        if (Objects.equals(category, "transportation")) {
            transportation.add(amount);
        } else if (Objects.equals(category, "bills")) {
            bills.add(amount);
        } else if (Objects.equals(category, "education")) {
            education.add(amount);
        } else if (Objects.equals(category, "entertainment")) {
            entertainment.add(amount);
        } else if (Objects.equals(category, "dining")) {
            dining.add(amount);
        } else if (Objects.equals(category, "groceries")) {
            groceries.add(amount);
        } else if (Objects.equals(category, "transfer")) {
            transfer.add(amount);
        } else if (Objects.equals(category, "shopping")) {
            shopping.add(amount);
        } else if (Objects.equals(category, "salary")) {
            salary.add(amount);
        } else if (Objects.equals(category, "returns")) {
            returns.add(amount);
        } else if (Objects.equals(category, "loans")) {
            loans.add(amount);
        }
    }

    /*
     * REQUIRES: category has a non-zero length, budgetamt >= 0,
     * MODIFIES: this
     * EFFECTS:  sets budget for selected valid category
     */
    public void setBudget(String category, double budgetamt) {
        if (Objects.equals(chooseOutCategory(category), "transportation")) {
            transportationBgt = budgetamt;
        } else if (Objects.equals(chooseOutCategory(category), "bills")) {
            billsBgt = budgetamt;
        } else if (Objects.equals(chooseOutCategory(category), "education")) {
            educationBgt = budgetamt;
        } else if (Objects.equals(chooseOutCategory(category), "entertainment")) {
            entertainmentBgt = budgetamt;
        } else if (Objects.equals(chooseOutCategory(category), "dining")) {
            diningBgt = budgetamt;
        } else if (Objects.equals(chooseOutCategory(category), "groceries")) {
            groceriesBgt = budgetamt;
        } else if (Objects.equals(chooseOutCategory(category), "transfer")) {
            transferBgt = budgetamt;
        } else if (Objects.equals(chooseOutCategory(category), "shopping")) {
            shoppingBgt = budgetamt;
        }
    }

    /*
     * EFFECTS: Returns budget amount of interested category, returns 0 if invalid category
     */
    public double getBudgetAmt(String budget) {
        if (Objects.equals(budget, "transportation")) {
            return transportationBgt;
        } else if (Objects.equals(budget, "bills")) {
            return billsBgt;
        } else if (Objects.equals(budget, "education")) {
            return educationBgt;
        } else if (Objects.equals(budget, "entertainment")) {
            return entertainmentBgt;
        } else if (Objects.equals(budget, "dining")) {
            return diningBgt;
        } else if (Objects.equals(budget, "groceries")) {
            return groceriesBgt;
        } else if (Objects.equals(budget, "transfer")) {
            return transferBgt;
        } else if (Objects.equals(budget, "shopping")) {
            return shoppingBgt;
        }
        return 0;
    }

    /*
     * EFFECTS: if set budget is less than total amount spent
     * for any corresponding category turns budgetUpheld false, else
     * leaves true, returns budgetUpheld
     */
    public boolean checkBudget() {
        boolean budgetUpheld = true;
        if (transportationBgt < total(transportation)) {
            budgetUpheld = false;
//            System.out.println("WARNING!!! Transportation budget");
        } else if (billsBgt < total(bills)) {
            budgetUpheld = false;
//            System.out.println("WARNING!!! Bill budget");
        } else if (educationBgt < total(education)) {
            budgetUpheld = false;
//            System.out.println("WARNING!!! Education budget");
        } else if (entertainmentBgt < total(entertainment)) {
            budgetUpheld = false;
//            System.out.println("WARNING!!! Entertainment budget");
        } else if (diningBgt < total(dining)) {
            budgetUpheld = false;
//            System.out.println("WARNING!!! Dining budget");
        } else if (groceriesBgt < total(groceries)) {
            budgetUpheld = false;
//            System.out.println("WARNING!!! Groceries budget");
        } else if (transferBgt < total(transfer)) {
            budgetUpheld = false;
//            System.out.println("WARNING!!! Transfer budget");
        } else if (shoppingBgt < total(shopping)) {
            budgetUpheld = false;
//            System.out.println("WARNING!!! Shopping budget");
        }
        return budgetUpheld;
    }


    /*
     * EFFECTS: checks if category exists within list of valid outflow categories
     */
    public boolean checkNotValidOutCategory(String category) {
        return !listOfOutCategories.contains(category);
    }

    /*
     * EFFECTS: checks if category exists within list of valid outflow categories
     */
    public boolean checkNotValidInCategory(String category) {
        return !listOfInCategories.contains(category);
    }

    /*
     * EFFECTS: checks if category exists within list of valid categories
     */
    public boolean checkNotValidCategory(String category) {
        listOfAllCategories.addAll(listOfInCategories);
        listOfAllCategories.addAll(listOfOutCategories);

        return !listOfAllCategories.contains(category);
    }

    /*
     * EFFECTS: returns budgets in budgets as a JSON array
     */
    public JSONArray transToJson() {
        budgets.add(transportationBgt);
        budgets.add(billsBgt);
        budgets.add(educationBgt);
        budgets.add(entertainmentBgt);
        budgets.add(diningBgt);
        budgets.add(groceriesBgt);
        budgets.add(transferBgt);
        budgets.add(shoppingBgt);

        JSONArray jsonArray = new JSONArray();

        for (double d : budgets) {
            searchAndMatch(d, jsonArray);
        }
        return jsonArray;
    }

    public void searchAndMatch(double d, JSONArray jsonArray) {
        if (d == 0) {
            jsonArray.put(toJsonSub("--- Budget", d));
        } else if (d == transportationBgt) {
            jsonArray.put(toJsonSub("Transportation Budget", d));
        } else if (d == billsBgt) {
            jsonArray.put(toJsonSub("Bills Budget", d));
        } else if (d == educationBgt) {
            jsonArray.put(toJsonSub("Education Budget", d));
        } else if (d == entertainmentBgt) {
            jsonArray.put(toJsonSub("Entertainment Budget", d));
        } else if (d == diningBgt) {
            jsonArray.put(toJsonSub("Dining Budget", d));
        } else if (d == groceriesBgt) {
            jsonArray.put(toJsonSub("Groceries Budget", d));
        } else if (d == transferBgt) {
            jsonArray.put(toJsonSub("Transfer Budget", d));
        } else {
            jsonArray.put(toJsonSub("Shopping Budget", d));
        }
    }

    public JSONObject toJsonSub(String s, double d) {
        JSONObject json = new JSONObject();
        json.put(s, d);
        return json;
    }

}



