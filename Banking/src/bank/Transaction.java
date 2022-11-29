package bank;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class Transaction implements Comparable<Transaction> {

    public enum Type {
        WITHDRAW, DEPOSIT
    }
    private final Type type;
    private final long timestamp;
    private final String id;
    private final double amount;
    public Transaction(Type type, long timestamp, String id, double amount) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Id cant be null or blank");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cant be less than 0");
        }
        this.type = type;
        this.timestamp = timestamp;
        this.id = id;
        this.amount = amount;
    }

    public Transaction(Transaction source) {
        this.type = source.type;
        this.timestamp = source.timestamp;
        this.id = source.id;
        this.amount = source.amount;
    }

    public Type getType() {
        return type;
    }
    public long getTimestamp() {
        return timestamp;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return timestamp == that.timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp);
    }

    @Override
    public String toString() {
        return type + "  \t"
                + returnDate(timestamp) +
                "\t" + id +
                "\t$" + amount;
    }

    public String returnDate(long timestamp) {
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat sdate = new SimpleDateFormat(pattern);
        return sdate.format(new Date(timestamp * 1000));

    }

    @Override
    public int compareTo(Transaction o) {
        return Long.compare(o.timestamp, timestamp);
    }


}
