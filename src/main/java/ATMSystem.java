package src.main.java;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ATMSystem {
    List<Account> accounts;

    public ATMSystem() {
        accounts = getAllAccounts();
    }

    public Account getAccount(String login) {
        for (Account account : accounts) {
            if (account.getLogin().equals(login)) {
                return account;
            }
        }
        return null;
    }

    public Account login(Scanner scanner) {
        System.out.println("Enter login: ");
        String login = scanner.nextLine();
        return getAccount(login);
    }

    public boolean checkOptions(Account account, int option) {
        if (account.equalUserType("user") && option != 1 && option != 3 && option != 4 && option != 5) {
            return false;
        } else if (account.equalUserType("admin") && option != 1 && option != 2 && option != 3 && option != 4 && option != 6) {
            return false;
        } return true;
    }

    public void chooseOptions(Account account) {
        System.out.println("Choose an option: ");
        if (account.equalUserType("user")) {
            System.out.println("1. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Display Balance");
            System.out.println("5. Exit");
        } else if (account.equalUserType("admin")) {
            System.out.println("1. Create New Account");
            System.out.println("2. Delete Existing Account");
            System.out.println("3. Update Account Information");
            System.out.println("4. Search for Account");
            System.out.println("6. Exit");
        }
    }

    public void handleOptions(Account account, int option, Scanner scanner) {
        if (account.equalUserType("user")) {
            User user = (User) account.userType;
            switch (option) {
                case 1:
                    System.out.println("Enter amount to withdraw: ");
                    double amount = scanner.nextDouble();
                    user.withdraw(amount);
                    chooseOptions(account);
                case 3:
                    System.out.println("Enter amount to deposit: ");
                    amount = scanner.nextDouble();
                    user.deposit(amount);
                    chooseOptions(account);
                case 4:
                    System.out.println("Your balance is: " + account.getBalance());
                    chooseOptions(account);
                case 5:
                    System.out.println("Goodbye " + account.getAccountHolder() + "!");
                    user.logout(true);
                    break;
            }
        } else if (account.equalUserType("admin")) {
            switch (option) {
                case 1:
                    System.out.println("Enter account holder: ");
                    String accountHolder = scanner.nextLine();
                    System.out.println("Enter balance: ");
                    double balance = scanner.nextDouble();
                    System.out.println("Enter login: ");
                    String login = scanner.nextLine();
                    System.out.println("Enter password: ");
                    String password = scanner.nextLine();
                    System.out.println("Enter user type: ");
                    String userType = scanner.nextLine();
                    UserType userType1 = null;
                    if (userType.equals("user")) {
                        userType1 = new User(account, "user");
                    } else if (userType.equals("admin")) {
                        userType1 = new Administrator(account, "admin");
                    }
                    ((Administrator) account.userType).createAccount(account, scanner);
                    chooseOptions(account);
                case 2:
                    System.out.println("Enter account number: ");
                    int accountNumber = scanner.nextInt();
                    ((Administrator) account.userType).deleteAccount(accountNumber);
                    chooseOptions(account);
                case 3:
                    System.out.println("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    Account account1 = getAccount(accountNumber);
                    ((Administrator) account.userType).updateAccount(account1, scanner);
                    chooseOptions(account);
                case 4:
                    System.out.println("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    Account account2 = getAccount(accountNumber);
                    ((Administrator) account.userType).displayAcc(account2);
                    chooseOptions(account);
                case 6:
                    System.out.println("Goodbye " + account.getAccountHolder() + "!");
                    ((Administrator) account.userType).logout(true);
                    break;
            }
        }
    }

    private List<Account> getAllAccounts() {
        ArrayList<Account> acc = new ArrayList<Account>();
        try {
            ResultSet rs = DB.getCol("accounts", "*");
            while (true) {
                assert rs != null;
                if (!rs.next()) break;
                acc.add(new Account(rs));
            }
            DB.closeDBconnection();
            return acc;
        } catch (SQLException e) {
            e.printStackTrace();
        } return null;
    }

    public Account getAccount(int accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        return null;
    }


}
