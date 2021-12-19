package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {
    private Account testAccount;

    @BeforeEach
    void runBefore() {
        testAccount = new Account(500);
    }

    @Test
    void testConstructor() {
        assertEquals(500, testAccount.getBalance());
    }

    @Test
    void testConstructorNegBalance() {
        testAccount = new Account(-200);
        assertEquals(0, testAccount.getBalance());
    }

    @Test
    void testAddExpense() {
        testAccount.addExpense(50, "shopping");
        assertEquals(-50, testAccount.getOutCategoryTotal("shopping"));
        assertEquals(450, testAccount.getBalance());
        assertEquals("shopping: -$50.0", testAccount.getRecentTransactions());
    }

    @Test
    void testAddExpenseInvalidCategory() {
        testAccount.addExpense(60, "vacation");
        assertNull(testAccount.getOutCategoryTotal("vacation"));
        assertEquals(440, testAccount.getBalance());
        assertEquals("other: -$60.0", testAccount.getRecentTransactions());
    }

    @Test
    void testAddManyExpense() {
        testAccount.addExpense(20, "groceries");
        testAccount.addExpense(60, "shopping");
        testAccount.addExpense(25, "transportation");
        testAccount.addExpense(30, "shopping");
        assertEquals(-20, testAccount.getOutCategoryTotal("groceries"));
        assertEquals(-90, testAccount.getOutCategoryTotal("shopping"));
        assertEquals(-25, testAccount.getOutCategoryTotal("transportation"));
        assertEquals(365, testAccount.getBalance());
        assertEquals("shopping: -$30.0", testAccount.getRecentTransactions());
        assertEquals("groceries: -$20.0 | shopping: -$60.0 | transportation: -$25.0 | shopping: -$30.0 | ",
                testAccount.printList(testAccount.transactions));
    }

    @Test
    void testAddExpenseLargerThanBalance() {
        testAccount.addExpense(600, "education");
        assertEquals(0, testAccount.getOutCategoryTotal("shopping"));
        assertEquals(500, testAccount.getBalance());
        assertEquals("", testAccount.getRecentTransactions());
    }

    @Test
    void testAddIncome() {
        testAccount.addIncome(1000, "salary");
        assertEquals(1000, testAccount.getInCategoryTotal("salary"));
        assertEquals(1500, testAccount.getBalance());
        assertEquals("salary: +$1000.0", testAccount.getRecentTransactions());
    }

    @Test
    void testAddManyIncome() {
        testAccount.addIncome(700, "salary");
        testAccount.addIncome(6000, "loans");
        testAccount.addIncome(800, "salary");
        testAccount.addIncome(30, "returns");
        assertEquals(1500, testAccount.getInCategoryTotal("salary"));
        assertEquals(6000, testAccount.getInCategoryTotal("loans"));
        assertEquals(30, testAccount.getInCategoryTotal("returns"));
        assertEquals(8030, testAccount.getBalance());
        assertEquals("salary: +$700.0 | loans: +$6000.0 | salary: +$800.0 | returns: +$30.0 | ",
                testAccount.printList(testAccount.transactions));
    }

    @Test
    void testAddManyExpenseAndIncome() {
        testAccount.addIncome(1000, "salary");
        testAccount.addExpense(1250, "bills");
        testAccount.addExpense(123.6, "transfer");
        testAccount.addIncome(123.6, "returns");
        assertEquals(1000, testAccount.getInCategoryTotal("salary"));
        assertEquals(-1250, testAccount.getOutCategoryTotal("bills"));
        assertEquals(-123.6, testAccount.getOutCategoryTotal("transfer"));
        assertEquals(123.6, testAccount.getInCategoryTotal("returns"));
        assertEquals(250, testAccount.getBalance());
        assertEquals("salary: +$1000.0 | bills: -$1250.0 | transfer: -$123.6 | returns: +$123.6 | ",
                testAccount.printList(testAccount.transactions));
    }

    @Test
    void testMisCategorizeExpenseAndIncome() {
        testAccount.addExpense(20, "returns");
        testAccount.addIncome(60, "shopping");
        assertNull(testAccount.getOutCategoryTotal("returns"));
        assertEquals(0, testAccount.getInCategoryTotal("returns"));
        assertNull(testAccount.getInCategoryTotal("shopping"));
        assertEquals(0, testAccount.getOutCategoryTotal("shopping"));
        assertEquals(540, testAccount.getBalance());
        assertEquals("other: -$20.0 | other: +$60.0 | ",
                testAccount.printList(testAccount.transactions));
    }

    @Test
    void testPrintTransactions() {
        testAccount.addIncome(1000, "salary");
        assertEquals("salary: +$1000.0 | ", testAccount.printTransactions(testAccount));
    }

    @Test
    void testPrintTransactionsMany() {
        testAccount.addExpense(202, "groceries");
        testAccount.addIncome(130, "returns");
        testAccount.addExpense(375,"shopping");
        assertEquals("groceries: -$202.0 | returns: +$130.0 | shopping: -$375.0 | ",
                testAccount.printTransactions(testAccount));
    }

    @Test
    void testGetOutCategory() {
        testAccount.addIncome(700, "salary");
        testAccount.addExpense(700, "dining");
        assertEquals(-700, testAccount.getOutCategoryTotal("dining"));
    }

    @Test
    void testGetOutCategoryNull() {
        assertNull( testAccount.getOutCategoryTotal("cruise"));
    }

    @Test
    void testGetOutCategoryZero() {
        assertEquals(0, testAccount.getOutCategoryTotal("entertainment"));
    }

    @Test
    void testGetOutCategorySeveral() {
        testAccount.addExpense(200, "education");
        testAccount.addExpense(120, "education");
        testAccount.addExpense(100, "education");
        assertEquals(-420, testAccount.getOutCategoryTotal("education"));
    }

    @Test
    void testSetBudget() {
        testAccount.setBudget("groceries", 175);
        assertEquals(175, testAccount.getBudgetAmt("groceries"));
    }

    @Test
    void testSetManyBudget() {
        testAccount.setBudget("bills", 125);
        testAccount.setBudget("education", 500);
        testAccount.setBudget("transfer", 75);
        assertEquals(125, testAccount.getBudgetAmt("bills"));
        assertEquals(500, testAccount.getBudgetAmt("education"));
        assertEquals(75, testAccount.getBudgetAmt("transfer"));
    }

    @Test
    void testGetBudget() {
        testAccount.setBudget("shopping", 210);
        testAccount.setBudget("entertainment", 51);
        testAccount.setBudget("transportation", 20);
        assertEquals(210, testAccount.getBudgetAmt("shopping"));
        assertEquals(51, testAccount.getBudgetAmt("entertainment"));
        assertEquals(20, testAccount.getBudgetAmt("transportation"));
    }

    @Test
    void testGetBudgetInvalid() {
        testAccount.setBudget("busfare", 3.50);
        assertEquals(0, testAccount.getBudgetAmt("busfare"));
    }

    @Test
    void testGetBudgetEmpty() {
        assertEquals(0, testAccount.getBudgetAmt("shopping"));
    }

    @Test
    void testReSetBudget() {
        testAccount.setBudget("dining", 23);
        testAccount.setBudget("dining", 46);
        assertEquals(46, testAccount.getBudgetAmt("dining"));
    }

    @Test
    void testToJson() {
        testAccount.addIncome(150, "loans");
        testAccount.addIncome(145, "returns");
        testAccount.addIncome(140, "salary");
        testAccount.addExpense(35, "shopping");
        testAccount.addExpense(30, "transfer");
        testAccount.addExpense(25, "groceries");
        testAccount.addExpense(20, "dining");
        testAccount.addExpense(15, "entertainment");
        testAccount.addExpense(10, "education");
        testAccount.addExpense(5, "bills");
        testAccount.addExpense(1, "transportation");
        testAccount.toJson();
        assertEquals(150, testAccount.getInCategoryTotal("loans"));
        assertEquals(145, testAccount.getInCategoryTotal("returns"));
        assertEquals(140, testAccount.getInCategoryTotal("salary"));
        assertEquals(-35, testAccount.getOutCategoryTotal("shopping"));
        assertEquals(-30, testAccount.getOutCategoryTotal("transfer"));
        assertEquals(-25, testAccount.getOutCategoryTotal("groceries"));
        assertEquals(-20, testAccount.getOutCategoryTotal("dining"));
        assertEquals(-15, testAccount.getOutCategoryTotal("entertainment"));
        assertEquals(-10, testAccount.getOutCategoryTotal("education"));
        assertEquals(-5, testAccount.getOutCategoryTotal("bills"));
        assertEquals(-1, testAccount.getOutCategoryTotal("transportation"));
    }

    @Test
    void testPrintLog() {
        testAccount.addIncome(100, "salary");
        testAccount.addExpense(50, "shopping");
        testAccount.printLog();
    }

}