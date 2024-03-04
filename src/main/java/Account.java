package src.main.java;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    int accountNumber;
    String accountHolder;
    double balance;
    String login;
    private String password;
    UserType userType;
    boolean loggedIn;
    String status;

    public Account(int accountNumber, String accountHolder, double balance, String login, String password, UserType userType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.login = login;
        this.password = password;
        this.userType = userType;
    }

    public Account(String accountHolder, double balance, String login, String password, UserType userType) {
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.login = login;
        this.password = password;
        this.userType = userType;
    }


    public Account(ResultSet rs) throws SQLException {
        this.accountHolder = rs.getString("accountHolder");
        this.balance = rs.getDouble("balance");
        this.login = rs.getString("login");
        this.password = rs.getString("password");
        this.userType = (UserType) rs.getObject("userType");
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }


    public boolean equalUserType(String check) {
        return userType.toString().equals(check);
    }

}
