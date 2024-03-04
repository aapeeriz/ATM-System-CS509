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

    public void createAccount(Account account, Scanner scanner) {
        if (account.loggedIn) {
            System.out.println("Enter login: ");
            String login = scanner.nextLine();
            System.out.println("Enter Pin Code: ");
            int password = scanner.nextInt();
            while(String.valueOf(password).length() != 4) {
                System.out.println("Invalid pin code. Try again.");
                password = scanner.nextInt();
            }
            System.out.println("Enter account holder: ");
            String accountHolder = scanner.nextLine();
            System.out.println("Enter balance: ");
            double balance = scanner.nextDouble();
            System.out.println("Enter user type: ");
            String userType = scanner.nextLine();
            UserType userType1 = null;
            if (userType.equals("user")) {
                userType1 = new User(account, "user");
            } else if (userType.equals("admin")) {
                userType1 = new Administrator(account, "admin");
            }
            String[] col = {"holder", "balance", "username", "password", "userType"};
            String[] val = {accountHolder, String.valueOf(balance), login, String.valueOf(password), userType};
            DB.insertRow("accounts", col, val);
        }
    }

    public void deleteAccount(int accountNumber) {
        if (account.loggedIn) {
            account = null;
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
                    String accountHolder = scanner.nextLine();
                    String[] col = {"holder"};
                    String[] val = {accountHolder};
                    DB.updateRow("accounts", col, val, "accountNumber = " + String.valueOf(account.getAccountNumber()));
                    account.setAccountHolder(accountHolder);
                    System.out.println("Account holder updated.");
                    System.out.println("Would you like to update anything else? (y/n)");
                    String response = scanner.nextLine();
                    if (response.equals("y") || response.equals("Y")) {
                        updateAccount(account, scanner);
                    } else if (response.equals("n") || response.equals("N")) {
                        update = false;
                    }

                case 2:
                    System.out.println("Enter new status: ");
                    String newStatus = scanner.nextLine();
                    while (!newStatus.equals("Active") && !newStatus.equals("Inactive") && !newStatus.equals("Disabled")) {
                        System.out.println("Invalid status. Try again.");
                        newStatus = scanner.nextLine();
                    }
                    col = new String[]{"status"};
                    val = new String[]{newStatus};
                    DB.updateRow("accounts", col, val, "accountNumber = " + String.valueOf(account.getAccountNumber()));
                    account.setStatus(newStatus);
                    System.out.println("Status updated.");
                    System.out.println("Would you like to update anything else? (y/n)");
                    response = scanner.nextLine();
                    if (response.equals("y") || response.equals("Y")) {
                        updateAccount(account, scanner);
                    } else if (response.equals("n") || response.equals("N")) {
                        update = false;
                    }
                case 3:
                    System.out.println("Enter new login: ");
                    String newLogin = scanner.nextLine();
                    col = new String[]{"username"};
                    val = new String[]{newLogin};
                    DB.updateRow("accounts", col, val, "accountNumber = " + String.valueOf(account.getAccountNumber()));
                    account.setLogin(newLogin);
                    System.out.println("Login updated.");
                    System.out.println("Would you like to update anything else? (y/n)");
                    response = scanner.nextLine();
                    if (response.equals("y") || response.equals("Y")) {
                        updateAccount(account, scanner);
                    } else if (response.equals("n") || response.equals("N")) {
                        update = false;
                    }
                case 4:
                    System.out.println("Enter new pin code: ");
                    int pinCode = scanner.nextInt();
                    while (String.valueOf(pinCode).length() != 4) {
                        System.out.println("Invalid pin code. Try again.");
                        pinCode = scanner.nextInt();
                    }
                    col = new String[]{"password"};
                    val = new String[]{String.valueOf(pinCode)};
                    DB.updateRow("accounts", col, val, "accountNumber = " + String.valueOf(account.getAccountNumber()));
                    account.setPassword(String.valueOf(pinCode));
                    System.out.println("Pin code updated.");
                    System.out.println("Would you like to update anything else? (y/n)");
                    response = scanner.nextLine();
                    if (response.equals("y") || response.equals("Y")) {
                        updateAccount(account, scanner);
                    } else if (response.equals("n") || response.equals("N")) {
                        update = false;
                    }
            }
        }
        }





}
