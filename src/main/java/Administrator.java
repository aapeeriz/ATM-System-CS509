package src.main.java;

import java.util.Scanner;


public class Administrator implements UserType {
    Account account;
    String type;

    public Administrator(Account account, String type) {
        this.account = account;
        this.type = type;
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

    public int createAccount(Account account, Scanner scanner) {
        int id = 0;
        if (account.loggedIn) {
            System.out.println("Enter Login: ");
            scanner.nextLine();
            String login = scanner.nextLine();
            System.out.println("Enter Pin Code: ");
            String password = scanner.nextLine();
            while(password.length() != 5) {
                System.out.println("Invalid pin code. Try again.");
                password = scanner.nextLine();
            }
            System.out.println("Enter Holders Name: ");
            String accountHolder = scanner.nextLine();
            System.out.println("Enter Starting Balance: ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.println("Enter User Type (user/admin): ");
            String userType = scanner.nextLine();
            UserType userType1 = null;
            if (userType.equals("user")) {
                userType1 = new User(account, "user");
            } else if (userType.equals("admin")) {
                userType1 = new Administrator(account, "admin");
            }
            System.out.println("Enter Status (Active/Inactive/Disabled): ");
            String status = scanner.nextLine();
            Account account1 = new Account(accountHolder, balance, login, password, userType1);
            userType1.setAccount(account1);
            String[] col = {"holder", "balance", "username", "password", "userType", "status"};
            String[] val = {accountHolder, String.valueOf(balance), login, password, userType, status};
            id = DB.insertRowAccounts(col, val);

            return id;
        } return id;
    }

    public void deleteAccount(int accountNumber) {
        if (account.loggedIn) {
            DB.deleteRow("accounts", "id = " + accountNumber);
        }
    }
    public void displayAcc(Account account) {
            int accNumber = account.getAccountNumber();
            String holder = account.getAccountHolder();
            double balance = account.getBalance();
            String login = account.getLogin();
            String password = account.getPassword();
            String status = account.getStatus();
            System.out.println("Account # " + accNumber);
            System.out.println("Holder: " + holder);
            System.out.println("Balance: " + balance);
            System.out.println("Status: " + status);
            System.out.println("Login: " + login);
            System.out.println("Pin Code: " + password);

    }


    public void updateAccount(Account account, Scanner scanner) {
        boolean update = true;
        while (update) {
            displayAcc(account);
            System.out.println("What would you like to update?");
            System.out.println("1. Account Holder");
            System.out.println("2. Status");
            System.out.println("3. Login");
            System.out.println("4. Pin Code");
            int option = scanner.nextInt();
            while (option < 1 || option > 4) {
                System.out.println("Invalid option. Try again.");
                option = scanner.nextInt();
            } switch (option) {
                case 1:
                    System.out.println("Enter new account holder: ");
                    scanner.nextLine();
                    String accountHolder = scanner.nextLine();
                    String[] col = {"holder"};
                    String[] val = {accountHolder};
                    DB.updateRow("accounts", col, val, "id = " + account.getAccountNumber());
                    account.setAccountHolder(accountHolder);
                    System.out.println("Account holder updated.");

                case 2:
                    System.out.println("Enter new status: ");
                    scanner.nextLine();
                    String newStatus = scanner.nextLine();
                    while (!newStatus.equals("Active") && !newStatus.equals("Inactive") && !newStatus.equals("Disabled")) {
                        System.out.println("Invalid status. Try again.");
                        newStatus = scanner.nextLine();
                    }
                    col = new String[]{"status"};
                    val = new String[]{newStatus};
                    DB.updateRow("accounts", col, val, "id = " + account.getAccountNumber());
                    account.setStatus(newStatus);
                    System.out.println("Status updated.");
                case 3:
                    System.out.println("Enter new login: ");
                    scanner.nextLine();
                    String newLogin = scanner.nextLine();
                    col = new String[]{"username"};
                    val = new String[]{newLogin};
                    DB.updateRow("accounts", col, val, "id = " + account.getAccountNumber());
                    account.setLogin(newLogin);
                    System.out.println("Login updated.");
                case 4:
                    System.out.println("Enter new pin code: ");
                    scanner.nextLine();
                    String pinCode = scanner.nextLine();
                    while (String.valueOf(pinCode).length() != 5) {
                        System.out.println("Invalid pin code. Try again.");
                        pinCode = scanner.nextLine();
                    }
                    col = new String[]{"password"};
                    val = new String[]{String.valueOf(pinCode)};
                    DB.updateRow("accounts", col, val, "id = " + account.getAccountNumber());
                    account.setPassword(pinCode);
                    System.out.println("Pin code updated.");
            }
        }
    }
}
