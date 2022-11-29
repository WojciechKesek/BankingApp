package bank.account;

public class Loan extends Account {

    private static final double INTREST_RATE = 0.02;
    private static final int DEBT_LIMIT = 10000;

    public Loan(String id, String name, double balance) {
        super(id, name, balance);
    }

    public Loan(Account source) {
        super(source);
    }

    @Override
    public void deposit(double amount) {
        super.setBalance(super.round(super.getBalance() - amount));
    }

    @Override
    public boolean withdraw(double amount) {
        amount += amount * INTREST_RATE;
        if (amount < 0) {
            System.out.println("You cant withdraw negative amount");
            return false;
        } else if (amount + super.getBalance() > DEBT_LIMIT) {
            System.out.println("You cant exceed debt limit");
            return false;
        } else {
            super.setBalance(round(super.getBalance() + amount));
            return true;
        }
    }

    @Override
    public Account clone() {
        return new Loan(this);
    }
}
