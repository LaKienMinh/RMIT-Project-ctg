package com.example;
import java.sql.*;
import java.util.logging.*;


import io.javalin.Javalin;

public class ServerConnect {
    public static Connection connectJDBC() {
        String url = "jdbc:sqlite:your_database_file.db"; // Can be absolute or relative path
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void connectJavalin(){
        Javalin app = Javalin.create().start(7000);

        app.get("/", ctx -> ctx.result("Hello from Javalin!"));
        System.out.println("Connection to Javalin successfully.");
    }

    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                   + "    id INTEGER PRIMARY KEY,\n"
                   + "    name TEXT NOT NULL\n"
                   + ");";

        try (Connection conn = connectJDBC();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Table 'users' created (if it didn't already exist).");
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        connectJavalin();
        connectJDBC();
        createNewTable();
    }
}
