package persistence;

import model.Account;
import model.Categories;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Account acc = new Account(0);
            Categories ctg = new Categories();
            JsonWriter writer = new JsonWriter("./data/\0invalidFile.json");
            writer.open();
            fail("IOException was expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTransactions() {
        try {
            Account acc = new Account(0);
            Categories ctg = new Categories();
            JsonWriter writer = new JsonWriter("./data/testJsonWriterEmptyTransactions.json");
            writer.open();
            writer.write(acc, ctg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterEmptyTransactions.json");
            acc = reader.read();
            assertEquals(0, acc.getBalance());
            assertEquals("", acc.printTransactions(acc));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNormalTransactions() {
        try {
            Account acc = new Account(100);
            Categories ctg = new Categories();
            acc.addExpense(100, "shopping");
            acc.addIncome(1000, "salary");

            JsonWriter writer = new JsonWriter("./data/testJsonWriterNormalTransactions.json");
            writer.open();
            writer.write(acc, ctg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterNormalTransactions.json");
            acc = reader.read();
            assertEquals(1000, acc.getBalance());
            assertEquals("shopping: -$100.0 | salary: +$1000.0 | ", acc.printTransactions(acc));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterNormalTransactions2() {
        try {
            Account acc = new Account(250);
            Categories ctg = new Categories();
            acc.addToTransactions("dining: -$125.0 | groceries: -$35.0 | loans: +$4000.0", acc);
            JsonWriter writer = new JsonWriter("./data/testJsonWriterNormalTransactionsV2.json");
            writer.open();
            writer.write(acc, ctg);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterNormalTransactionsV2.json");
            acc = reader.read();
            assertEquals(250, acc.getBalance());
            assertEquals("dining: -$125.0 | groceries: -$35.0 | loans: +$4000.0 | ", acc.printTransactions(acc));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
