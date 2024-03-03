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
}
