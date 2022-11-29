package bank;

import bank.account.Account;
import bank.account.Chequing;
import bank.account.Taxable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Bank {
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;

    public Bank() {
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public void addAccount(Account account) {
        if (account != null) {
            accounts.add(account.clone());
        }
    }

    private void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public Transaction[] getTransactions(String accountId) {
        List<Transaction> list = transactions.stream()
                .filter((transaction) -> transaction.getId().equals(accountId))
                .toList();

        return list.toArray(new Transaction[list.size()]);
    }

    public Account getAccount(String transactionId) {
        return accounts.stream()
                .filter(account -> account.getId().equals(transactionId))
                .findFirst()
                .orElse(null);
    }

    private void withdrawTransaction(Transaction transaction) {
        if (getAccount(transaction.getId()) != null
                && getAccount(transaction.getId()).withdraw(transaction.getAmount())) {
            addTransaction(transaction);
        }
    }

    private void depositTransaction(Transaction transaction) {
        if (getAccount(transaction.getId()) != null) {
            getAccount(transaction.getId()).deposit(transaction.getAmount());
            addTransaction(transaction);
        }
    }

    public void executeTransaction(Transaction transaction) {
        if (transaction.getType() == Transaction.Type.WITHDRAW) {
            withdrawTransaction(transaction);
        } else if (transaction.getType() == Transaction.Type.DEPOSIT) {
            depositTransaction(transaction);
        }
    }

    private double getIncome(Taxable account) {
        Transaction[] transactions = getTransactions(((Chequing) account).getId());
        return Arrays.stream(transactions)
                   .mapToDouble(transaction -> switch (transaction.getType()) {
                       case WITHDRAW -> -transaction.getAmount();
                       case DEPOSIT -> transaction.getAmount();
                   })
                .sum();
    }

    public void deductTaxes() {
        accounts.forEach(account -> {
            if (Taxable.class.isAssignableFrom(account.getClass())) {
                ((Taxable) account).tax(getIncome((Taxable) account));
            }
        });
    }

}
