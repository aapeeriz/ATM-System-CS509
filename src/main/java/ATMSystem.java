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
        if (account.userType instanceof User && option != 1 && option != 3 && option != 4 && option != 5) {
            return false;
        } else if (account.userType instanceof Administrator && option != 1 && option != 2 && option != 3 && option != 4 && option != 6) {
            return false;
        } return true;
    }

    public void chooseOptions(Account account) {
        System.out.println("Choose an option: ");
        if (account.userType instanceof User) {
            System.out.println("1. Withdraw Cash");
            System.out.println("3. Deposit Cash");
            System.out.println("4. Display Balance");
            System.out.println("5. Exit");
        } else if (account.userType instanceof Administrator) {
            System.out.println("1. Create New Account");
            System.out.println("2. Delete Existing Account");
            System.out.println("3. Update Account Information");
            System.out.println("4. Search for Account");
            System.out.println("6. Exit");
        }
    }

    public void handleOptions(Account account, int option, Scanner scanner) {
        if (account.userType instanceof User) {
            User user = (User) account.userType;
            switch (option) {
                case 1:
                    System.out.println("Enter the withdrawal amount: ");
                    double amount = scanner.nextDouble();
                    user.withdraw(amount);
                    System.out.println("Cash Successfully Withdrawn");
                    System.out.println("Account #: " + account.getAccountNumber());
                    System.out.println("Date: " + java.time.LocalDate.now());
                    System.out.println("Withdrawn: " + amount);
                    System.out.println("Balance: " + account.getBalance());
                    accounts.set(accounts.indexOf(account), account);
                    break;
                case 3:
                    System.out.println("Enter cash amount to deposit: ");
                    amount = scanner.nextDouble();
                    user.deposit(amount);
                    System.out.println("Cash Deposited Successfully.");
                    System.out.println("Account #: " + account.getAccountNumber());
                    System.out.println("Date: " + java.time.LocalDate.now());
                    System.out.println("Deposited: " + amount);
                    System.out.println("Balance: " + account.getBalance());
                    accounts.set(accounts.indexOf(account), account);
                    break;
                case 4:
                    System.out.println("Account #: " + account.getAccountNumber());
                    System.out.println("Date: " + java.time.LocalDate.now());
                    System.out.println("Balance: " + account.getBalance());
                    break;
                case 5:
                    user.logout(true);
                    break;
            }
        } else if (account.userType instanceof Administrator) {
            switch (option) {
                case 1:
                    int id = ((Administrator) account.userType).createAccount(account, scanner);
                    System.out.println("Account Successfully Created - the account number assigned is: " + id);
                    break;
                case 2:
                    System.out.println("Enter the account number to which you want to delete: ");
                    int accountNumber = scanner.nextInt();
                    Account account2 = getAccount(accountNumber);
                    System.out.println("You wish to delete the account held by " + account2.getAccountHolder() + ". If this information is correct, please re-enter the account number: ");
                    accountNumber = scanner.nextInt();
                    if (accountNumber == account2.getAccountNumber()) {
                        ((Administrator) account.userType).deleteAccount(accountNumber);
                        accounts.remove(account2);
                        System.out.println("Account Deleted Successfully");
                    } else {
                        System.out.println("Invalid account number. Try again.");
                    }
                    break;
                case 3:
                    System.out.println("Enter the Account Number: ");
                    accountNumber = scanner.nextInt();
                    Account account3 = getAccount(accountNumber);
                    ((Administrator) account.userType).updateAccount(account3, scanner);
                    break;
                case 4:
                    System.out.println("Enter account number: ");
                    accountNumber = scanner.nextInt();
                    Account account4 = getAccount(accountNumber);
                    ((Administrator) account.userType).displayAcc(account4);
                    break;
                case 6:
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
