package test;

import bank.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionTest {
    Transaction transaction;

    @BeforeEach
    public void setup() {
        transaction = new Transaction(Transaction.Type.WITHDRAW, 1546905600, "6b8dd258-aba3-4b19-b238-45d15edd4b48", 624.99);
    }

    @Test
    void correctDateTest() {
        assertEquals("08-01-2019", transaction.returnDate(transaction.getTimestamp()));
    }
}