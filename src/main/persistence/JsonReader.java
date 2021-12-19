package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.Account;
import org.json.*;

// Represents a reader that reads transactions from JSON data stored in file
public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads transactions from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Account read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTrans(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses balance from JSON object and returns it
    private Account parseTrans(JSONObject jsonObject) {
        double balance = jsonObject.getDouble("Balance");
        Account acc = new Account(balance);
        addTrans(acc, jsonObject);
        addListOfCategories(acc, jsonObject);
        addBudgets(acc, jsonObject);
        return acc;
    }

    // MODIFIES: this
    // EFFECTS: parses transactions from JSON object and adds them to transactions
    private void addTrans(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Transactions");
        for (Object json : jsonArray) {
            JSONObject nextTran = (JSONObject) json;
            addTran(acc, nextTran);
        }
    }

    // MODIFIES: this
    // EFFECTS: parses categories from JSON object and adds it to categories
    private void addTran(Account acc, JSONObject jsonObject) {
        String category = jsonObject.getString("Category & Amount");
        acc.addToTransactions(category, acc);
    }

    // MODIFIES: this
    // EFFECTS: parses transactions from JSON object and adds them to transactions
    private void addListOfCategories(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Categories");
        for (Object json : jsonArray) {
            JSONObject nextCat = (JSONObject) json;
            addCategories(acc, nextCat);
        }
    }

    // MODIFIES: this
    // EFFECTS: parses categories & amounts from JSON object and adds it to transactions
    private void addCategories(Account acc, JSONObject jsonObject) {
        if (jsonObject.has("Transportation Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Transportation Transactions");
            for (Object json : jsonArray) {
                JSONObject nextTran = (JSONObject) json;
                addCategory(acc, nextTran, "transportation");
            }
        }
        if (jsonObject.has("Bills Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Bills Transactions");
            for (Object json : jsonArray) {
                JSONObject nextBill = (JSONObject) json;
                addCategory(acc, nextBill, "bills");
            }
        }
        if (jsonObject.has("Education Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Education Transactions");
            for (Object json : jsonArray) {
                JSONObject nextEdu = (JSONObject) json;
                addCategory(acc, nextEdu, "education");
            }
        }
        addCategoriesPartTwo(acc, jsonObject);
    }

    private void addCategoriesPartTwo(Account acc, JSONObject jsonObject) {
        if (jsonObject.has("Entertainment Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Entertainment Transactions");
            for (Object json : jsonArray) {
                JSONObject nextEnter = (JSONObject) json;
                addCategory(acc, nextEnter, "entertainment");
            }
        }
        if (jsonObject.has("Dining Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Dining Transactions");
            for (Object json : jsonArray) {
                JSONObject nextDin = (JSONObject) json;
                addCategory(acc, nextDin, "dining");
            }
        }
        if (jsonObject.has("Groceries Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Groceries Transactions");
            for (Object json : jsonArray) {
                JSONObject nextGroc = (JSONObject) json;
                addCategory(acc, nextGroc, "groceries");
            }
        }
        addCategoriesPartThree(acc, jsonObject);
    }

    private void addCategoriesPartThree(Account acc, JSONObject jsonObject) {
        if (jsonObject.has("Transfer Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Transfer Transactions");
            for (Object json : jsonArray) {
                JSONObject nextTrans = (JSONObject) json;
                addCategory(acc, nextTrans, "transfer");
            }
        }
        if (jsonObject.has("Shopping Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Shopping Transactions");
            for (Object json : jsonArray) {
                JSONObject nextShop = (JSONObject) json;
                addCategory(acc, nextShop, "shopping");
            }
        }
        if (jsonObject.has("Salary Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Salary Transactions");
            for (Object json : jsonArray) {
                JSONObject nextSal = (JSONObject) json;
                addCategory(acc, nextSal, "salary");
            }
        }
        addCategoriesPartFour(acc, jsonObject);
    }

    private void addCategoriesPartFour(Account acc, JSONObject jsonObject) {
        if (jsonObject.has("Returns Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Returns Transactions");
            for (Object json : jsonArray) {
                JSONObject nextRet = (JSONObject) json;
                addCategory(acc, nextRet, "returns");
            }
        }
        if (jsonObject.has("Loans Transactions")) {
            JSONArray jsonArray = jsonObject.getJSONArray("Loans Transactions");
            for (Object json : jsonArray) {
                JSONObject nextLoan = (JSONObject) json;
                addCategory(acc, nextLoan, "loans");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: parses categories & amounts from JSON object and adds it to transactions
    private void addCategory(Account acc, JSONObject jsonObject, String category) {
        double amount = jsonObject.getDouble("Expenses & Incomes");
        acc.addToCategory(amount, category);
    }

    // MODIFIES: this
    // EFFECTS: parses budgets from JSON object and adds them to budgets
    private void addBudgets(Account acc, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Budgets");
        for (Object json : jsonArray) {
            JSONObject nextBudget = (JSONObject) json;
            addBudget(acc, nextBudget);
        }
    }

    // MODIFIES: this
    // EFFECTS: parses budgets from JSON object and sets category budgets as that
    private void addBudget(Account acc, JSONObject jsonObject) {
        if (jsonObject.has("Transportation Budget")) {
            acc.setBudget("transportation", jsonObject.getDouble("Transportation Budget"));
        }
        if (jsonObject.has("Bills Budget")) {
            acc.setBudget("bills", jsonObject.getDouble("Bills Budget"));
        }
        if (jsonObject.has("Education Budget")) {
            acc.setBudget("education", jsonObject.getDouble("Education Budget"));
        }
        if (jsonObject.has("Entertainment Budget")) {
            acc.setBudget("entertainment", jsonObject.getDouble("Entertainment Budget"));
        }
        otherHalf(acc, jsonObject);
    }

    // MODIFIES: this
    // EFFECTS: parses budgets from JSON object and sets category budgets as that, fulfills checkstyle conditions
    public void otherHalf(Account acc, JSONObject jsonObject) {
        if (jsonObject.has("Dining Budget")) {
            acc.setBudget("dining", jsonObject.getDouble("Dining Budget"));
        }
        if (jsonObject.has("Groceries Budget")) {
            acc.setBudget("groceries", jsonObject.getDouble("Groceries Budget"));
        }
        if (jsonObject.has("Transfer Budget")) {
            acc.setBudget("transfer", jsonObject.getDouble("Transfer Budget"));
        }
        if (jsonObject.has("Shopping Budget")) {
            acc.setBudget("shopping", jsonObject.getDouble("Shopping Budget"));
        }
    }
}
