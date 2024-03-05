package src.main.java;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    int accountNumber;
    private String accountHolder;
    private double balance;
    private String login;
    private String password;
    UserType userType;
    boolean loggedIn;
    private String status;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



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
        this.accountNumber = rs.getInt("id");
        this.accountHolder = rs.getString("holder");
        this.balance = rs.getDouble("balance");
        this.login = rs.getString("username");
        this.password = rs.getString("password");
        if (rs.getString("userType").equals("user")) {
            this.userType = new User(this, "user");
        } else if (rs.getString("userType").equals("admin")) {
            this.userType = new Administrator(this, "admin");
        }
        this.status = rs.getString("status");
    }

    public int getAccountNumber() {
        return accountNumber;
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



}
