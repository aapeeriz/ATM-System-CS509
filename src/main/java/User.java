package src.main.java;

public class User implements UserType {
    Account account;
    String type;

    public User(Account account, String type) {
        this.account = account;
        this.type = type;
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

    public void withdraw(double amount) {
        if (account.loggedIn) {
            double balance = account.getBalance();
            balance -= amount;
            account.setBalance(balance);
            String[] columns = {"balance"};
            String[] values = {String.valueOf(balance)};
            DB.updateRow("accounts", columns, values, "id =" + account.accountNumber);
        }
    }

    public void deposit(double amount) {
        if (account.loggedIn) {
            double balance = account.getBalance();
            balance += amount;
            account.setBalance(balance);
            String[] columns = {"balance"};
            String[] values = {String.valueOf(balance)};
            DB.updateRow("accounts", columns, values, "id =" + account.accountNumber);
        }
    }




}
