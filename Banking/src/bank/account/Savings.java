package bank.account;

public class Savings extends Account {
    private static final int CHARGE = 5;

    public Savings(String id, String name, double balance) {
        super(id, name, balance);
    }

    public Savings(Account source) {
        super(source);
    }

    @Override
    public void deposit(double amount) {
        super.setBalance(round(super.getBalance() + amount));
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount < 0) {
            System.out.println("You cant withdraw negative amount of money");
            return false;
        } else if (amount + CHARGE <= super.getBalance()) {
            super.setBalance(round(super.getBalance() - amount - CHARGE));
            return true;
        } else {
            System.out.println("You cant withdraw: " + amount + "because your limit is " + getBalance());
            return false;
        }
    }

    @Override
    public Account clone() {
        return new Savings(this);
    }
}
