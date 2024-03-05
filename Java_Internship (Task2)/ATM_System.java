import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM_System {
    private static Map<String, Account> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
    private static Account currentUser = null;

    public static void main(String[] args) {
        initializeAccounts();

        System.out.println("Welcome to the ATM System!");

        while (true) {
            System.out.print("Enter user id: ");
            String userId = scanner.nextLine();
            System.out.print("Enter user pin: ");
            String pin = scanner.nextLine();

            if (authenticateUser(userId, pin)) {
                System.out.println("Login successful.");
                break;
            } else {
                System.out.println("Invalid user id or pin. Please try again.");
            }
        }

        while (true) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    displayTransactionHistory();
                    break;
                case 2:
                    withdraw();
                    break;
                case 3:
                    deposit();
                    break;
                case 4:
                    transfer();
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
    }

    private static void initializeAccounts() {
        accounts.put("user1", new Account("user1", "1234", 1000));
        accounts.put("user2", new Account("user2", "5678", 2000));
    }

    private static boolean authenticateUser(String userId, String pin) {
        Account account = accounts.get(userId);
        if (account != null && account.getPin().equals(pin)) {
            currentUser = account;
            return true;
        }
        return false;
    }

    private static void displayMenu() {
        System.out.println("\nATM Menu:");
        System.out.println("1. View Transaction History");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.println("4. Transfer");
        System.out.println("5. Quit");
        System.out.print("Enter your choice: ");
    }

    private static void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        for (String transaction : currentUser.getTransactionHistory()) {
            System.out.println(transaction);
        }
    }

    private static void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character
        if (currentUser.withdraw(amount)) {
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    private static void deposit() {
        System.out.print("Enter the amount to deposit: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character
        currentUser.deposit(amount);
        System.out.println("Deposit successful.");
    }

    private static void transfer() {
        System.out.print("Enter recipient's user id: ");
        String recipientId = scanner.nextLine();
        Account recipient = accounts.get(recipientId);
        if (recipient == null) {
            System.out.println("Recipient account not found.");
            return;
        }

        System.out.print("Enter the amount to transfer: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline character

        if (currentUser.transfer(recipient, amount)) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Insufficient funds.");
        }
    }
}

class Account {
    private String userId;
    private String pin;
    private double balance;
    private StringBuilder transactionHistory;

    public Account(String userId, String pin, double initialBalance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new StringBuilder();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        transactionHistory.append("Withdrawal of $").append(amount).append("\n");
        return true;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.append("Deposit of $").append(amount).append("\n");
    }

    public boolean transfer(Account recipient, double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        recipient.balance += amount;
        transactionHistory.append("Transfer of $").append(amount).append(" to user ").append(recipient.getUserId()).append("\n");
        recipient.transactionHistory.append("Transfer of $").append(amount).append(" from user ").append(userId).append("\n");
        return true;
    }

    public String[] getTransactionHistory() {
        return transactionHistory.toString().split("\n");
    }
}
