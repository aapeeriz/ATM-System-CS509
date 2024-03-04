package src.main.java;

import java.util.Scanner;

public class Administrator implements UserType {
    Account account;
    ATMSystem atmSystem;

    public Administrator(Account account) {
        this.account = account;
        this.atmSystem = new ATMSystem();
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public boolean login(String login, String password) {
        if (account.getLogin().equals(login) && account.getPassword().equals(password)) {
            account.loggedIn = true;
        } return account.loggedIn;
    }

    public void logout(boolean sure) {
        if (sure) {
            account.loggedIn = false;
        }
    }

    public void createAccount(Account account, Scanner scanner) {
        if (account.loggedIn) {
            System.out.println("Enter login: ");
            String login = scanner.nextLine();
            System.out.println("Enter password: ");
            String password = scanner.nextLine();
            System.out.println("Enter account holder: ");
            String accountHolder = scanner.nextLine();
            System.out.println("Enter balance: ");
            double balance = scanner.nextDouble();
            System.out.println("Enter user type: ");
            String userType = scanner.nextLine();
            UserType userType1 = null;
            if (userType.equals("user")) {
                userType1 = new User(account);
            } else if (userType.equals("admin")) {
                userType1 = new Administrator(account);
            }
            String[] col = {"holder", "balance", "login", "password", "userType"};
            String[] val = {accountHolder, String.valueOf(balance), login, password, userType};
            DB.insertRow("accounts", col, val);
        }
    }

    public void deleteAccount(int accountNumber) {
        if (account.loggedIn) {
            account = null;
        }
    }

    public void updateAccount(Account account, Scanner scanner) {
        if (account.loggedIn) {
            System.out.println("Enter account number: ");
            int accountNumber = scanner.nextInt();
            Account account1 = atmSystem.getAccount(accountNumber);
            while (account1 == null) {
                System.out.println("Account not found. Try again.");
                accountNumber = scanner.nextInt();
                account1 = atmSystem.getAccount(accountNumber);
            }
            System.out.println("Holder: " + account1.getAccountHolder());
            System.out.println("Balance: " + account1.getBalance());
            System.out.println("Status: " + account1.getLogin());
            System.out.println("Login: " + account1.getLogin());
            System.out.println("Pin Code: " + account1.getPassword());

        }
            String[] col = {"holder", "balance", "login", "password", "userType"};
            String[] val = {accountHolder, String.valueOf(balance), login, password, userType};
            DB.updateRow("accounts", col, val, "accountNumber", String.valueOf(accountNumber));
        }

    }
}
