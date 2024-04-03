package org.rmit.assignment.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static DatabaseInitializer instance = null;

    public Connection connection = null;

    private DatabaseInitializer(String dbPath) {
        boolean isNewDatabase = false;

        try {
            Class.forName("org.sqlite.JDBC");

            File dbFile = new File(dbPath);
            if (!dbFile.exists()) {
                isNewDatabase = dbFile.createNewFile();
                if (!isNewDatabase) {
                    throw new RuntimeException("Failed to create database file");
                }
            }

            String databasePath = "jdbc:sqlite:" + dbPath;
            connection = DriverManager.getConnection(databasePath);

            if (isNewDatabase) {
                createTableIfNotExists();
                createSampleDataWhenFirstInit();
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Database driver not found");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Failed to create database file");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.err.println("Failed to connect to database");
            throw new RuntimeException(e);
        }
    }

    public static synchronized DatabaseInitializer getInstance() {
        if (instance == null) {
            throw new RuntimeException("Database not initialized");
        }
        return instance;
    }

    public static synchronized void initDatabase(String dbPath) {
        if (instance == null) {
            instance = new DatabaseInitializer(dbPath);
        }
    }

    private void createTableIfNotExists() {
        try {
            Statement statement = connection.createStatement();

            //Todo: Create tables if not exists

            System.out.println("Init database tables!");
            statement.close();
        } catch (SQLException e) {
            System.err.println("Failed to create tables");
            throw new RuntimeException(e);
        }
    }

    private void createSampleDataWhenFirstInit() {
        try {
            Statement statement = connection.createStatement();

            //Todo: Insert sample data when first init

            System.out.println("Init sample data!");
            statement.close();
        } catch (SQLException e) {
            System.err.println("Failed to create sample data");
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Failed to close connection");
            throw new RuntimeException(e);
        }
    }
}
