package bank.account;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public abstract class Account {
    private final String id;
    private final String name;
    private double balance;

    public Account(String id, String name, double balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public Account(Account source) {
        this.id = source.id;
        this.name = source.name;
        this.balance = source.balance;
    }

    public String getId() {
        return id;
    }

    public double getBalance() {
        return balance;
    }

    protected void setBalance(double balance) {
        this.balance = balance;
    }

    public abstract void deposit(double amount);

    public abstract boolean withdraw(double amount);

    public abstract Account clone();

    @Override
    public String toString() {
        return this.getClass().getSimpleName() +
                "\t" + id + "" +
                "\t" + name + "" +
                "\t$" + balance + "";
    }

    protected double round(double amount) {
        DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.ENGLISH));
        return Double.parseDouble(formatter.format(amount));
    }
}
