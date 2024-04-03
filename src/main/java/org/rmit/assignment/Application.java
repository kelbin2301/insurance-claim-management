package org.rmit.assignment;

import org.rmit.assignment.dao.DatabaseInitializer;


public class Application {
    public static void main(String[] args) {
        initDatabase();
        System.out.println("Hello World!");


        closeDatabase();
    }

    public static void initDatabase() {
        String dbPath = "src/main/resources/database.db";
        DatabaseInitializer.initDatabase(dbPath);
    }

    public static void closeDatabase() {
        DatabaseInitializer.getInstance().closeConnection();
    }

}
