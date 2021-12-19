package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoriesTest {
    private Categories testCategory;


    @BeforeEach
    void runBefore() {
        testCategory = new Categories();
    }

    @Test
    void testTotalOut() {
        testCategory.addToCategory(512, "bills");
        assertEquals(512, testCategory.total(testCategory.bills));
    }

    @Test
    void testTotalIn() {
        testCategory.addToCategory(10000, "loans");
        assertEquals(10000, testCategory.total(testCategory.loans));
    }

    @Test
    void testEmptyListTotal() {
        assertEquals(0, testCategory.total(testCategory.entertainment));
    }

    @Test
    void testManyTotal() {
        testCategory.addToCategory(561.7, "education");
        testCategory.addToCategory(748.9, "education");
        testCategory.addToCategory(561.7, "education");
        testCategory.addToCategory(173.4, "education");
        assertEquals(2045.70, testCategory.total(testCategory.education));
    }

    @Test
    void testChooseValidOutCategory() {
        assertEquals("transfer", testCategory.chooseOutCategory("transfer"));
    }

    @Test
    void testChooseInvalidOutCategory() {
        assertEquals("other", testCategory.chooseOutCategory("pets"));
        assertEquals("other", testCategory.chooseOutCategory("salary"));
    }

    @Test
    void testChooseValidInCategory() {
        assertEquals("loans", testCategory.chooseInCategory("loans"));
    }

    @Test
    void testChooseInvalidInCategory() {
        assertEquals("other", testCategory.chooseInCategory("scholarship"));
        assertEquals("other", testCategory.chooseInCategory("shopping"));
    }

    @Test
    void testAddToCategory() {
        testCategory.addToCategory(27, "dining");
        testCategory.addToCategory(23, "transfer");
        assertEquals(27, testCategory.total(testCategory.dining));
        assertEquals(23, testCategory.total(testCategory.transfer));
    }

    @Test
    void testAddManyToCategory() {
        testCategory.addToCategory(13, "dining");
        testCategory.addToCategory(23, "dining");
        testCategory.addToCategory(33, "dining");
        assertEquals(69, testCategory.total(testCategory.dining));
    }

    @Test
    void testAddToCategories() {
        testCategory.addToCategory(27, "dining");
        testCategory.addToCategory(23, "transfer");
        assertEquals(27, testCategory.total(testCategory.dining));
        assertEquals(23, testCategory.total(testCategory.transfer));
    }

    @Test
    void testAddManyToCategories() {
        testCategory.addToCategory(13, "dining");
        testCategory.addToCategory(23, "dining");
        testCategory.addToCategory(33, "dining");
        assertEquals(69, testCategory.total(testCategory.dining));
    }


    @Test
    void testSetBudget() {
        testCategory.setBudget("groceries", 175);
        assertEquals(175, testCategory.getBudgetAmt("groceries"));
    }

    @Test
    void testSetManyBudget() {
        testCategory.setBudget("bills", 125);
        testCategory.setBudget("education", 500);
        testCategory.setBudget("transfer", 75);
        assertEquals(125, testCategory.getBudgetAmt("bills"));
        assertEquals(500, testCategory.getBudgetAmt("education"));
        assertEquals(75, testCategory.getBudgetAmt("transfer"));
    }

    @Test
    void testGetBudget() {
        testCategory.setBudget("shopping", 210);
        testCategory.setBudget("entertainment", 51);
        testCategory.setBudget("transportation", 20);
        assertEquals(210, testCategory.getBudgetAmt("shopping"));
        assertEquals(51, testCategory.getBudgetAmt("entertainment"));
        assertEquals(20, testCategory.getBudgetAmt("transportation"));
    }

    @Test
    void testGetBudgetInvalid() {
        testCategory.setBudget("busfare", 3.50);
        assertEquals(0, testCategory.getBudgetAmt("busfare"));
    }

    @Test
    void testGetBudgetEmpty() {
        assertEquals(0, testCategory.getBudgetAmt("shopping"));
    }

    @Test
    void testReSetBudget() {
        testCategory.setBudget("dining", 23);
        testCategory.setBudget("dining", 46);
        assertEquals(46, testCategory.getBudgetAmt("dining"));
    }

    @Test
    void testBudgetUpheldFalse() {
        testCategory.setBudget("dining", 50);
        testCategory.addToCategory(51, "dining");
        assertFalse(testCategory.checkBudget());
    }

    @Test
    void testBudgetUpheldTrue() {
        testCategory.setBudget("entertainment", 100);
        testCategory.addToCategory(91, "entertainment");
        assertTrue(testCategory.checkBudget());
    }

    @Test
    void testBudgetUpheldBorderCase() {
        testCategory.setBudget("entertainment", 75);
        testCategory.addToCategory(75, "entertainment");
        assertTrue(testCategory.checkBudget());
    }

    @Test
    void testBudgetUpheldManyTrue() {
        testCategory.setBudget("bills", 250);
        testCategory.setBudget("transfer", 500);
        testCategory.setBudget("education", 1000);
        testCategory.addToCategory(200, "bills");
        testCategory.addToCategory(450, "transfer");
        testCategory.addToCategory(950, "education");
        assertTrue(testCategory.checkBudget());
    }

    @Test
    void testBudgetUpheldManyFalse() {
        testCategory.setBudget("dining", 25);
        testCategory.setBudget("shopping", 50);
        testCategory.setBudget("groceries", 100);
        testCategory.addToCategory(20, "dining");
        testCategory.addToCategory(45, "shopping");
        testCategory.addToCategory(101, "groceries");
        assertFalse(testCategory.checkBudget());
    }

    @Test
    void testBudgetUpheldManyExpenseFalse() {
        testCategory.setBudget("transportation", 20);
        testCategory.addToCategory(19.75, "transportation");
        testCategory.addToCategory(19.75, "transportation");
        assertFalse(testCategory.checkBudget());
    }

    @Test
    void testCheckBudget() {
        testCategory.setBudget("transfer", 200);
        testCategory.addToCategory(225, "transfer");
        assertFalse(testCategory.checkBudget());
    }

    @Test
    void testCheckBudgetMany() {
        testCategory.setBudget("bills", 100);
        testCategory.setBudget("education", 1000);
        testCategory.addToCategory(73, "bills");
        testCategory.addToCategory(641, "education");
        assertTrue(testCategory.checkBudget());
    }

    @Test
    void testCheckBudgetOneWrong() {
        testCategory.setBudget("dining", 50);
        testCategory.setBudget("transfer", 50);
        testCategory.setBudget("entertainment", 50);
        testCategory.addToCategory(49, "dining");
        testCategory.addToCategory(50, "transfer");
        testCategory.addToCategory(51, "entertainment");
        assertFalse(testCategory.checkBudget());
    }

    @Test
    void testCheckBudgetTrueThenReSetFalse() {
        testCategory.setBudget("bills", 200);
        testCategory.addToCategory(150, "bills");
        assertTrue(testCategory.checkBudget());
        testCategory.setBudget("bills", 100);
        assertFalse(testCategory.checkBudget());
    }

    @Test
    void testCheckBudgetFalseThenReSetTrue() {
        testCategory.setBudget("education", 500);
        testCategory.addToCategory(750, "education");
        assertFalse(testCategory.checkBudget());
        testCategory.setBudget("education", 1000);
        assertTrue(testCategory.checkBudget());
    }

    @Test
    void testCheckBudgetTrueThenSpendMoreFalse() {
        testCategory.setBudget("shopping", 100);
        testCategory.addToCategory(75, "shopping");
        assertTrue(testCategory.checkBudget());
        testCategory.addToCategory(50, "shopping");
        assertFalse(testCategory.checkBudget());
    }

    @Test
    void testNotContainInTrue() {
        assertTrue(testCategory.checkNotValidInCategory("paycheck"));
    }

    @Test
    void testNotContainInFalse() {
        assertFalse(testCategory.checkNotValidInCategory("salary"));
    }

    @Test
    void testNotContainOutTrue() {
        assertTrue(testCategory.checkNotValidOutCategory("restaurants"));
    }

    @Test
    void testNotContainOutFalse() {
        assertFalse(testCategory.checkNotValidOutCategory("dining"));
    }

    @Test
    void testNotContainTrue() {
        assertTrue(testCategory.checkNotValidCategory("holiday"));
    }

    @Test
    void testNotContainFalse() {
        assertFalse(testCategory.checkNotValidCategory("loans"));
    }

    @Test
    void testToJson() {
        testCategory.setBudget("transportation", 1);
        testCategory.setBudget("bills", 2);
        testCategory.setBudget("education", 3);
        testCategory.setBudget("entertainment", 4);
        testCategory.setBudget("dining", 5);
        testCategory.setBudget("groceries", 6);
        testCategory.setBudget("transfer", 7);
        testCategory.setBudget("shopping", 8);
        testCategory.transToJson();
        assertEquals(1, testCategory.getBudgetAmt("transportation"));
        assertEquals(2, testCategory.getBudgetAmt("bills"));
        assertEquals(3, testCategory.getBudgetAmt("education"));
        assertEquals(4, testCategory.getBudgetAmt("entertainment"));
        assertEquals(5, testCategory.getBudgetAmt("dining"));
        assertEquals(6, testCategory.getBudgetAmt("groceries"));
        assertEquals(7, testCategory.getBudgetAmt("transfer"));
        assertEquals(8, testCategory.getBudgetAmt("shopping"));
    }


}
