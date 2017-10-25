package no.birkett.quietshare;

/**
 * Created by Selva on 10/25/2017.
 */

public class TransactionObject {
    private String balance;
    private String transaction_number;
    private String amount;
    private String transaction_time;

    public TransactionObject(String balance, String transaction_number, String amount, String transaction_time) {
        this.balance = balance;
        this.transaction_number = transaction_number;
        this.amount = amount;
        this.transaction_time = transaction_time;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getTransaction_number() {
        return transaction_number;
    }

    public void setTransaction_number(String transaction_number) {
        this.transaction_number = transaction_number;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }
}
