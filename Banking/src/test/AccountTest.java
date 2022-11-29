package test;

import bank.account.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class AccountTest {
    Account[] accounts;

    @BeforeEach
    public void setup() {
        accounts = new Account[]{
                new Chequing("f84c43f4-a634-4c57-a644-7602f8840870", "Michael Scott", 1524.51),
                new Savings("ce07d7b3-9038-43db-83ae-77fd9c0450c9", "Saul Goodman", 2241.60),
                new Loan("4991bf71-ae8f-4df9-81c1-9c79cff280a5", "Phoebe Buffay", 2537.31)
        };
    }


    @org.junit.jupiter.api.Test
    void withdrawal() {
        accounts[0].withdraw(1440);
        assertEquals(84.51, accounts[0].getBalance());
    }

    @Test
    void overDraft() {
        accounts[0].withdraw(1534.43);
        assertEquals(-15.42, accounts[0].getBalance());
    }

    @Test
    void overdraftLimit() {
        accounts[0].withdraw(1726);
        assertEquals(1524.51, accounts[0].getBalance());
    }

    @Test
    void withdravalFee() {
        accounts[1].withdraw(100);
        assertEquals(2136.60, accounts[1].getBalance());
    }

    @Test
    void withdrawalInterest() {
        accounts[2].withdraw(2434.31);
        assertEquals(5020.31, accounts[2].getBalance());
    }

    @Test
    void withdrawalLimit() {
        assertFalse(accounts[2].withdraw(7463.69), "Debt can't exceed $10 000");
    }

    @Test
    void deposit() {
        accounts[0].deposit(5000);
        assertEquals(6524.51, accounts[0].getBalance());
    }

    @Test
    void loanDeposit() {
        accounts[2].deposit(1000);
        assertEquals(1537.31, accounts[2].getBalance());
    }

    @Test
    void incomeTax() {
        double income = 4000;
        accounts[0].deposit(income);
        ((Taxable) accounts[0]).tax(income);
        assertEquals(5374.51, accounts[0].getBalance());
    }
}