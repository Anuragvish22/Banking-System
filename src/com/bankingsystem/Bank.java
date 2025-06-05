package com.bankingsystem;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Bank {
    private List<Account> accounts = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();

    public void createAccount(int id, String name, double initialBalance) {
        Account acc = new Account(id, name, initialBalance);
        accounts.add(acc);
        transactions.add(new Transaction(Transaction.Type.DEPOSIT, initialBalance, id));
    }

    public Account getAccountById(int id) {
        return accounts.stream().filter(a -> a.getId() == id).findFirst().orElse(null);
    }

    public boolean deposit(int accountId, double amount) {
        Account acc = getAccountById(accountId);
        if (acc != null && amount > 0) {
            acc.deposit(amount);
            transactions.add(new Transaction(Transaction.Type.DEPOSIT, amount, accountId));
            return true;
        }
        return false;
    }

    public boolean withdraw(int accountId, double amount) {
        Account acc = getAccountById(accountId);
        if (acc != null && acc.withdraw(amount)) {
            transactions.add(new Transaction(Transaction.Type.WITHDRAW, amount, accountId));
            return true;
        }
        return false;
    }

    public boolean transferFunds(int fromId, int toId, double amount) {
        Account fromAcc = getAccountById(fromId);
        Account toAcc = getAccountById(toId);

        if (fromAcc != null && toAcc != null && fromAcc.withdraw(amount)) {
            toAcc.deposit(amount);
            transactions.add(new Transaction(Transaction.Type.TRANSFER, amount, fromId));
            transactions.add(new Transaction(Transaction.Type.DEPOSIT, amount, toId));
            return true;
        }
        return false;
    }

    public List<Transaction> getTransactionsForAccount(int accountId) {
        List<Transaction> accTransactions = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getAccountId() == accountId) accTransactions.add(t);
        }
        return accTransactions;
    }

    public void exportAccountsToCSV(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("AccountId,Name,Balance\n");
            for (Account acc : accounts) {
                writer.write("AccountId : " + acc.getId() + ", Name : " + acc.getName() + ", Balance : " + acc.getBalance() + "\n");
            }
            System.out.println("Accounts exported to CSV: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing accounts to CSV: " + e.getMessage());
        }
    }

    public void exportTransactionsToCSV(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Transaction t : transactions) {
                writer.write("AccountId : " + t.getAccountId() + ", Type : " + t.getType() + ", Amount : " + t.getAmount() + ", Date : " + t.getDateTime() + "\n");
            }
            System.out.println("Transactions exported to CSV: " + filename);
        } catch (IOException e) {
            System.out.println("Error writing to CSV: " + e.getMessage());
        }
    }


    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bank_data.dat"))) {
            oos.writeObject(accounts);
            oos.writeObject(transactions);
            System.out.println("Data saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank_data.dat"))) {
            accounts = (List<Account>) ois.readObject();
            transactions = (List<Transaction>) ois.readObject();
            System.out.println("Data loaded successfully.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved data found. Starting fresh.");
        }
    }

}
