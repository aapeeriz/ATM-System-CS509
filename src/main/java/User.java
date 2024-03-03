package src.main.java;

public class User implements UserType {
    Account account;

    public User(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void login(String login, String password) {
        if (account.getLogin().equals(login) && account.getPassword().equals(password)) {
            account.loggedIn = true;
        }
    }

    public void logout(boolean sure) {
        if (sure) {
            account.loggedIn = false;
        }
    }

    public void withdraw(double amount) {
        if (account.loggedIn) {
            account.balance -= amount;
        }
    }

    public void deposit(double amount) {
        if (account.loggedIn) {
            account.balance += amount;
        }
    }

    public double getBalance() {
        return account.balance;
    }

}
