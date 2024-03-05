package src.main.java;

public interface UserType {
    String type = "";
    public boolean login(String login, String password);
    public void logout(boolean sure);
    public void setAccount(Account account);

}