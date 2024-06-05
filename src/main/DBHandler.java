package main;

import java.sql.*;
import java.util.*;

public class DBHandler {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/pune_metro";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Durvesh@11";

    public static Connection dbConnect() throws SQLException {
        // Create and return a database connection
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    public static void dbClose(Connection conn, Statement statement, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void dbExecuteUpdate(String query) {
        Connection conn = null;
        Statement statement = null;
        try {
            conn = dbConnect();
            statement = conn.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose(conn, statement, null);
        }
    }

    public static ResultSet dbExecuteQuery(String query) {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        try {
            conn = dbConnect();
            statement = conn.createStatement();
            rs = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Note: Do not close ResultSet here, it will be closed by the caller
            //dbClose(conn, statement, null);
        }
        return rs;
    }

    public static boolean validateLogin(String username, String password) {
        Connection conn = null;
        Statement statement = null;
        ResultSet rs = null;
        boolean isValidLogin = false;

        try {
            conn = dbConnect();
            statement = conn.createStatement();
            rs = statement.executeQuery("SELECT * FROM users WHERE username='" + username + "'");

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                isValidLogin = password.equals(storedPassword);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbClose(conn, statement, rs);
        }

        return isValidLogin;
    }

 
    
    public static void main(String[] args) {
        // You can test your methods here if needed
    }
}