package bank.account;

public class Chequing extends Account implements Taxable {
    private static final int OVERDRAFT_LIMIT = 200;
    private static final double OVERDRAFT_CHARGE = 5.5;
    private static final double TAXABLE_INCOME = 3000;
    private static final double TAX = 0.15;

    public Chequing(String id, String name, double balance) {
        super(id, name, balance);
    }

    public Chequing(Account source) {
        super(source);
    }

    @Override
    public void deposit(double amount) {
        if (amount < 0) {
            System.out.println("You cant deposit negative amount of money");
        } else {
            super.setBalance(super.round(super.getBalance() + amount));
        }
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount >= super.getBalance() + OVERDRAFT_LIMIT - OVERDRAFT_CHARGE) {
            System.out.println("Withdrawal exceed ovedraft limit");
            return false;
        } else if (amount > super.getBalance()) {
            super.setBalance(super.round(super.getBalance() - amount - OVERDRAFT_CHARGE));
        } else {
            super.setBalance(super.round(super.getBalance() - amount));
        }
        return true;
    }

    @Override
    public void tax(double income) {
        double tax = Math.max(0, income - TAXABLE_INCOME) * TAX;
        super.setBalance(super.round(super.getBalance() - tax));
    }

    @Override
    public Account clone() {
        return new Chequing(this);
    }
}
