import bank.account.Account;
import bank.Bank;
import bank.Transaction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {
    static Bank bank = new Bank();
    static String ACCOUNTS_FILE = "Banking/src/data/accounts.txt";
    static String TRANSACTIONS_FILE = "Banking/src/data/transactions.txt";

    public static void main(String[] args) {
        try {
            ArrayList<Account> accounts = returnAccounts();
            loadAccounts(accounts);
            ArrayList<Transaction> transactions = returnTransactions();
            runTransactions(transactions);
            bank.deductTaxes();
            for (Account account : accounts) {
                System.out.println("\n\t\t\t\t\t ACCOUNT\n\n\t" + account + "\n\n");
                transactionHistory(account.getId());
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static Account createObject(String[] values) {
        try {
            return (Account) Class.forName("bank.account." + values[0])
                    .getConstructor(String.class, String.class, double.class)
                    .newInstance(values[1], values[2], Double.parseDouble(values[3]));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<Account> returnAccounts() throws FileNotFoundException {
        FileInputStream file = new FileInputStream(ACCOUNTS_FILE);
        Scanner scanner = new Scanner(file);
        ArrayList<Account> accounts = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split(",");
            accounts.add(createObject(values));
        }
        scanner.close();
        return accounts;
    }

    public static ArrayList<Transaction> returnTransactions() throws FileNotFoundException {
        FileInputStream file = new FileInputStream(TRANSACTIONS_FILE);
        Scanner scanner = new Scanner(file);
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String[] values = scanner.nextLine().split(",");
            transactions.add(new Transaction(Transaction.Type.valueOf(values[1]), Long.parseLong(values[0]), values[2], Double.parseDouble(values[3])));
        }
        transactions.sort(Collections.reverseOrder());
        scanner.close();
        return transactions;
    }

    public static void loadAccounts(ArrayList<Account> accounts) {
        accounts.forEach(account -> bank.addAccount(account));
    }

    public static void runTransactions(ArrayList<Transaction> transactions) {
        transactions.forEach(transaction -> bank.executeTransaction(transaction));
    }

    public static void transactionHistory(String id) {
        Transaction[] transactions = bank.getTransactions(id);
        System.out.println("\t\t\t\tTRANSACTION HISTORY\n\t");
        for(Transaction transaction: transactions) {
            wait(300);
            System.out.println("\t"+transaction+"\n");
        }
        System.out.println("\n\t\t\t\t\tAFTER TAX\n");
        System.out.println("\t" + bank.getAccount(id) +"\n\n\n\n");
    }

    public static void wait(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}