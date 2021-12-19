package persistence;

import model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/nonExistentFile.json");
        try {
            Account acc = reader.read();
            fail("IOException expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTransactions() {
        JsonReader reader = new JsonReader("./data/testJsonReaderEmptyTransactions.json");
        try {
            Account acc = reader.read();
            assertEquals(0, acc.getBalance());
            assertEquals("", acc.printTransactions(acc));
            assertEquals(0, acc.getBudgetAmt("groceries"));


        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderNormalTransactions() {
        JsonReader reader = new JsonReader("./data/testJsonReaderNormalTransactions.json");
        try {
            Account acc = reader.read();
            assertEquals(830, acc.getBalance());
            assertEquals("salary: +$90.0 | returns: +$100.0 | loans: +$1000.0 | transportation: -$10.0 |"
                            + " bills: -$20.0 | education: -$30.0 | entertainment: -$40.0 | dining: -$50.0 |"
                            + " groceries: -$60.0 | transfer: -$70.0 | shopping: -$80.0 | ",
                    acc.printTransactions(acc));
            assertEquals(120, acc.getBudgetAmt("shopping"));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


}
