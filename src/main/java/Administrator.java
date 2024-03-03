package src.main.java;

public class Administrator implements UserType {
    Account account;

    public Administrator(Account account) {
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

    public void createAccount(int accountNumber, String accountHolder, double balance, String login, String password, UserType userType) {
        if (account.loggedIn) {
            Account newAccount = new Account(accountNumber, accountHolder, balance, login, password, userType);
        }
    }

    public void deleteAccount(int accountNumber) {
        if (account.loggedIn) {
            account = null;
        }
    }

    public void updateAccount(int accountNumber, String accountHolder, double balance, String login, String password, UserType userType) {
        if (account.loggedIn) {
            account.setAccountNumber(accountNumber);
            account.setAccountHolder(accountHolder);
            account.setBalance(balance);
            account.setLogin(login);
            account.setPassword(password);
            account.setUserType(userType);
        }
    }
}
