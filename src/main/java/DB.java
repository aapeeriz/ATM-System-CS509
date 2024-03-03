package src.main.java;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class DB {
    public static Connection c = null;
    private static final String url = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String username = "root";
    private static final String password = "Chelmsford77&";

    /**
     * Connects to the database
     *
     * @throws SQLException if the connection fails
     */
    public static void connectToDB() {
        try {
            if (c == null) {
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection(url, username, password);
            } else {
                c.close();
                Class.forName("org.postgresql.Driver");
                c = DriverManager.getConnection(url, username, password);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Closes the database connection
     *
     * @throws SQLException if the connection fails
     */
    public static void closeDBconnection() throws SQLException {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Allows the user to enter an SQL query to be executed
     *
     * @param query is the query to be executed
     * @throws SQLException if the query fails
     */
    public static void freeQuery(String query) throws SQLException {
        try {
            connectToDB();
            Statement stmt = c.createStatement();
            stmt.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("ERROR Query Failed: " + e.getMessage());
        } finally {
            if (c != null) {
                closeDBconnection();
            }
        }
    }

    /**
     * Gets the specified colums from a row that matches
     *
     * @param table   the able in the db to search in
     * @param columns the cols to get; format like: col1,col2, ...
     * @param cond    a condition that selects rows that meet that condition
     * @return Returns a ResultSet that contains the information queried. returns
     *         null if an error occurs
     */
    public static ResultSet getRowCond(String table, String columns, String cond) {
        ResultSet rs = null;
        try {
            connectToDB();
            Statement stmt = c.createStatement();
            String query = "SELECT " + columns + " FROM " + table + " WHERE " + cond;
            rs = stmt.executeQuery(query);
            if (c != null) { closeDBconnection();}
            if (rs != null) {
                return rs;
            } else throw new SQLException("No rows found");
        } catch (SQLException e) {
            System.err.println("ERROR Query Failed: " + e.getMessage());
            return null;
        }
    }

    /**
     * Gets the specified columns given a column name
     *
     * @param table   the able in the db to search in
     * @param column the column being searched for
     * @return Returns a ResultSet that contains the information queried. returns
     *         null if an error occurs
     */
    public static ResultSet getCol(String table, String column) throws SQLException {
        try {
            connectToDB();
            Statement stmt = c.createStatement();
            String query = "SELECT " + column + " FROM " + table;
            ResultSet rs = stmt.executeQuery(query);
            if (c != null) { closeDBconnection();}
            return rs;
        } catch (SQLException e) {
            System.err.println("ERROR Query Failed: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<String> colRStoStringArray(ResultSet rs) throws SQLException {
        int listSize = 0;
        connectToDB();
        ArrayList<String> stringArray = new ArrayList<>();
        while (rs.next()) {
            try {
                stringArray.add(rs.getString(1));
            } catch (SQLException e) {
                System.err.println("ERROR Query Failed: " + e.getMessage());
                return null;
            }
        }
        if (c != null) {closeDBconnection();}
        return stringArray;
    }

    /**
     * In a given table,updates the value of the columns of that match the condition
     * and error occurs if the number of columns and values doesn't match
     *
     * @param table   the table to update
     * @param columns the columns to change
     * @param value   the values to change the corresponding column to
     * @param cond    the condition that determines which rows to update
     */
    public static void updateRow(String table, String[] columns, String[] value, String cond) {
        try {
            connectToDB();
            Statement stmt = c.createStatement();
            String query = "UPDATE " + table + " SET " + strArray2UpdateFormat(columns, value) + " WHERE " + cond;
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            System.err.println("ERROR Query Failed: " + e.getMessage());
        } finally {
            if (c != null) {
                try {
                    closeDBconnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Formats the cols and values to work with updating the database
     *
     * @param cols  cols that match up with corresponding value
     * @param value values to match with corresponding cols
     * @return string
     * @throws RuntimeException When length of col array and value array are not
     *                          equal
     */
    private static String strArray2UpdateFormat(String[] cols, String[] value) throws RuntimeException, SQLException {
        connectToDB();
        int length = cols.length;
        if (length != value.length) {
            throw new RuntimeException("Length of cols and value must match");
        }
        String ret = "";
        for (int i = 0; i < length - 1; i++) {
            ret += cols[i] + " = " + value[i] + ",";
        }
        ret += cols[length - 1] + " = " + value[length - 1];
        if (c != null) {
            try {
                closeDBconnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Inserts a row into the table with the given values
     *
     * @param table   the table to insert into
     * @param columns the columns to insert into
     * @param value   the values to insert into the corresponding columns
     */
    public static void insertRow(String table, String[] columns, String[] value) {
        try {
            connectToDB();
            Statement stmt = c.createStatement();
            String query = "INSERT INTO "  + table + " (" + strArray2InsertFormatCol(columns) + ") VALUES ("
                    + strArray2InsertFormat(value) + ")";
            stmt.executeUpdate(query);
            if (c != null) { closeDBconnection();}
        } catch (SQLException e) {
            System.err.println("ERROR Query Failed: " + e.getMessage());
        }
    }
    public static int insertRowAccounts(String table, String[] columns, String[] value) {
        int id = 0;
        ResultSet rs;
        PreparedStatement stmt = null;
        Statement currvalStatement = null;
        ResultSet currvalResultSet = null;
        try {
            connectToDB();
            c.setAutoCommit(false);
            String insert = "INSERT INTO ATMSystem.accounts(holder, balance, username, password, loggedIn, userType) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = c.prepareStatement(insert);
            stmt.setString(1, value[0]);
            stmt.setString(2, value[1]);
            stmt.setString(3, value[2]);
            stmt.setString(4, value[3]);
            stmt.setString(5, value[4]);
            stmt.setString(6, value[5]);
            stmt.executeUpdate();
            c.commit();
            if (c != null) {
                closeDBconnection();
            }
        } catch (SQLException e) {
            System.err.println("ERROR Query Failed: " + e.getMessage());
        }
        return id;
    }

    public static String strArray2InsertFormatCol(String[] arr) {
        String formattedStr = "";
        for (int i = 0; i < arr.length; i++) {
            formattedStr += arr[i];
            if (i != arr.length - 1) {
                formattedStr += ",";
            }
        }
        return formattedStr;
    }

    public static String strArray2InsertFormat(String[] arr) {
        String formattedStr = "'";
        for (int i = 0; i < arr.length; i++) {
            formattedStr += arr[i];
            if (i != arr.length - 1) {
                formattedStr += "','";
            } else {
                formattedStr += "'";
            }
        }
        return formattedStr;
    }

    /**
     * Deletes row(s) from the table that match the condition
     *
     * @param table Table to delete from
     * @param cond  condition to decide which rows get deleted
     */
    public static void deleteRow(String table, String cond) {
        try {
            connectToDB();
            Statement stmt = c.createStatement();
            String query = "DELETE FROM " + table + " WHERE " + cond;
            stmt.executeUpdate(query);
            if (c != null) { closeDBconnection();}
        } catch (SQLException e) {
            System.err.println("ERROR Query Failed: " + e.getMessage());
        }
    }



}
