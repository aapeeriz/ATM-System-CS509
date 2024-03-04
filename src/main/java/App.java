package src.main.java;

import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATMSystem atmSystem = new ATMSystem();
        Account account = atmSystem.login(scanner);
        while (account == null) {
            System.out.println("Account not found. Try again.");
            account = atmSystem.login(scanner);
        }
        System.out.println("Enter PIN code: ");
        String password = scanner.nextLine();
        while (!account.userType.login(account.getLogin(), password)) {
            System.out.println("Incorrect password. Try again.");
            password = scanner.nextLine();
        }
        System.out.println("Welcome, " + account.getAccountHolder() + "!");

        atmSystem.chooseOptions(account);
        int option = scanner.nextInt();
        while (atmSystem.checkOptions(account, option)) {
            System.out.println("Invalid option. Try again.");
            option = scanner.nextInt();
        }
        atmSystem.handleOptions(account, option, scanner);
    }
}
