package com.bankingsystem;

import java.util.List;
import java.util.Scanner;

public class Main {
    private final static Bank bank = new Bank();
    private final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        bank.loadData();
        boolean running = true;

        while (running) {
            printMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> createAccount();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> transfer();
                case 5 -> viewBalance();
                case 6 -> viewTransactions();
                case 7 -> bank.exportAccountsToCSV("accounts.csv");
                case 8 -> bank.exportTransactionsToCSV("transactions.csv");
                case 0 -> {
                    bank.saveData();
                    running = false;
                    System.out.println("Exiting... Goodbye!");
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n=== Banking System Menu ===");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Transfer Funds");
        System.out.println("5. View Balance");
        System.out.println("6. View Transaction History");
        System.out.println("7. Export Accounts to CSV");
        System.out.println("8. Export Transaction To CSV");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    private static void createAccount() {
        System.out.print("Enter account ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter account holder name: ");
        String name = scanner.nextLine();
        System.out.print("Enter initial balance: ");
        double balance = scanner.nextDouble();
        scanner.nextLine();

        bank.createAccount(id, name, balance);
        System.out.println("Account created successfully!");
    }

    private static void deposit() {
        System.out.print("Enter account ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter amount to deposit: ");
        double amount = scanner.nextDouble();

        if (bank.deposit(id, amount))
            System.out.println("Deposit successful!");
        else
            System.out.println("Deposit failed!");
    }

    private static void withdraw() {
        System.out.print("Enter account ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter amount to withdraw: ");
        double amount = scanner.nextDouble();

        if (bank.withdraw(id, amount))
            System.out.println("Withdrawal successful!");
        else
            System.out.println("Withdrawal failed! Insufficient funds or invalid account.");
    }

    private static void transfer() {
        System.out.print("Enter sender account ID: ");
        int fromId = scanner.nextInt();
        System.out.print("Enter receiver account ID: ");
        int toId = scanner.nextInt();
        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        if (bank.transferFunds(fromId, toId, amount))
            System.out.println("Transfer successful!");
        else
            System.out.println("Transfer failed! Check accounts and balance.");
    }

    private static void viewBalance() {
        System.out.print("Enter account ID: ");
        int id = scanner.nextInt();
        Account acc = bank.getAccountById(id);
        if (acc != null)
            System.out.println("Account Balance: " + acc.getBalance());
        else
            System.out.println("Account not found.");
    }

    private static void viewTransactions() {
        System.out.print("Enter account ID: ");
        int id = scanner.nextInt();
        List<Transaction> transactions = bank.getTransactionsForAccount(id);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (Transaction t : transactions)
                System.out.println(t);
        }
    }
}
