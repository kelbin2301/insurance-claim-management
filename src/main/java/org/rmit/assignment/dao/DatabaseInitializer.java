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
        final String createCustomerTable = "CREATE TABLE IF NOT EXISTS customer (" +
                "id varchar(255) PRIMARY KEY," +
                "full_name varchar(255)," +
                "customer_type varchar(255)," +
                "dependent_of varchar(255) " +
                ");";

        final String createInsuranceCardTable = "CREATE TABLE IF NOT EXISTS insurance_card (" +
                "id varchar(255) PRIMARY KEY," +
                "card_number varchar(255)," +
                "customer_id varchar(255)," +
                "policy_owner varchar(255)," +
                "expiration_date date" +
                ");";

        final String claimTable = "CREATE TABLE IF NOT EXISTS claim (" +
                "id varchar(255) PRIMARY KEY," +
                "customer_id varchar(255)," +
                "exam_date date," +
                "list_of_documents text," +
                "claim_amount double," +
                "status varchar(255)," +
                "receiver_bank_id int" +
                ");";

        final String bankingInfoTable = "CREATE TABLE IF NOT EXISTS banking_info (" +
                "id varchar(25) PRIMARY KEY," +
                "bank_name varchar(255)," +
                "account_number varchar(255)," +
                "account_name varchar(255)" +
                ");";
        try {
            Statement statement = connection.createStatement();

            statement.execute(createCustomerTable);
            statement.execute(createInsuranceCardTable);
            statement.execute(claimTable);
            statement.execute(bankingInfoTable);

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

            //===== Insert Customer data
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000001', 'John Hank', 'policy_holder', NULL);");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000002', 'Jane Hank', 'dependent', 'c-1000001');");

            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000003', 'Alice ABC', 'policy_holder', NULL);");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000004', 'Bob ABC', 'dependent', 'c-10000003');");

            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000005', 'Charlie Doe', 'policy_holder', NULL);");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000006', 'David Doe', 'dependent', 'c-10000005');");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000007', 'Eve Doe', 'dependent', 'c-10000005');");

            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000008', 'Frank Smith', 'policy_holder', NULL);");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000009', 'Grace Smith', 'dependent', 'c-10000008');");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000010', 'Henry Smith', 'dependent', 'c-10000008');");

            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000011', 'Ivy White', 'policy_holder', NULL);");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000012', 'Jack White', 'dependent', 'c-10000011');");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000013', 'Kate White', 'dependent', 'c-10000011');");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000014', 'Liam White', 'dependent', 'c-10000011');");

            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000015', 'Mia Black', 'policy_holder', NULL);");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of)" +
                    " VALUES ('c-1000016', 'Noah Black', 'dependent', 'c-10000015');");
            statement.execute("INSERT INTO customer (id, full_name, customer_type, dependent_of) " +
                    "VALUES ('c-1000017', 'Olivia Black', 'dependent', 'c-10000015');");
            // ========================

            // ===== Insert insurance card data
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000001', '211000001', 'c-10000001', 'John Hank', '2024-12-31');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000002', '211000002', 'c-10000002', 'John Hank', '2024-12-31');");

            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000003', '211000003', 'c-10000003', 'Alice ABC', '2025-12-31');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000004', '211000004', 'c-10000004', 'Alice ABC', '2025-12-31');");

            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000005', '211000005', 'c-10000005', 'Charlie Doe', '2023-12-31');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000006', '211000006', 'c-10000006', 'Charlie Doe', '2023-12-31');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000007', '211000007', 'c-10000007', 'Charlie Doe', '2023-12-31');");

            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000008', '211000008', 'c-10000008', 'Frank Smith', '2023-12-31');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000009', '211000009', 'c-10000009', 'Frank Smith', '2023-12-31');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000010', '211000010', 'c-10000010', 'Frank Smith', '2023-12-31');");

            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000011', '211000011', 'c-10000011', 'Ivy White', '2026-10-31');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000012', '211000012', 'c-10000012', 'Ivy White', '2026-10-31');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000013', '211000013', 'c-10000013', 'Ivy White', '2026-10-31');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000014', '211000014', 'c-10000014', 'Ivy White', '2026-10-31');");

            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000015', '211000015', 'c-10000015', 'Mia Black', '2025-3-20');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000016', '211000016', 'c-10000016', 'Mia Black', '2025-3-20');");
            statement.execute("INSERT INTO insurance_card (id, card_number, customer_id, policy_owner, expiration_date) " +
                    "VALUES ('2000000017', '211000017', 'c-10000017', 'Mia Black', '2025-3-20');");
            // ========================

            // ===== Insert Banking Info data
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000001', 'Bank A', '30000001', 'John Hank');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000002', 'Bank B', '30000002', 'Jane Hank');");

            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000003', 'Bank C', '30000003', 'Alice ABC');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000004', 'Bank D', '30000004', 'Bob ABC');");

            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000005', 'Bank E', '30000005', 'Charlie Doe');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000006', 'Bank F', '30000006', 'David Doe');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000007', 'Bank G', '30000007', 'Eve Doe');");

            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000008', 'Bank H', '30000008', 'Frank Smith');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000009', 'Bank I', '30000009', 'Grace Smith');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000010', 'Bank J', '30000010', 'Henry Smith');");

            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000011', 'Bank K', '30000011', 'Ivy White');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000012', 'Bank L', '30000012', 'Jack White');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000013', 'Bank M', '30000013', 'Kate White');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000014', 'Bank N', '30000014', 'Liam White');");

            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000015', 'Bank O', '30000015', 'Mia Black');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000016', 'Bank P', '30000016', 'Noah Black');");
            statement.execute("INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES ('b-1000017', 'Bank Q', '30000017', 'Olivia Black');");
            // ========================

            // ===== Insert claim data. List of documents is separated by comma, with format 'claimId_cardNumber_documentName1.pdf,claimId_cardNumber_documentName2.pdf,...'
            // Status is new, processing or done
            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000001', 'c-10000001', '2021-10-01', '3000000001_211000001_document1.pdf', 1000.0, 'done', 'b-1000001');");
            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000002', 'c-10000001', '2022-10-01', '3000000002_211000001_document2.pdf', 10000.0, 'done', 'b-1000001');");
            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000003', 'c-10000001', '2023-10-01', '3000000003_211000001_document3.pdf', 100000.0, 'processing', 'b-1000001');");

            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000004', 'c-10000002', '2021-10-01', '3000000004_211000002_document1.pdf', 2000.0, 'done', 'b-1000002');");
            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000005', 'c-10000002', '2023-10-01', '3000000005_211000002_document2.pdf', 20000.0, 'processing', 'b-1000002');");

            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000006', 'c-10000003', '2021-10-01', '3000000006_211000003_document1.pdf', 3000.0, 'done', 'b-1000003');");
            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000007', 'c-10000002', '2023-10-01', '3000000007_211000003_document2.pdf', 30000.0, 'new', 'b-1000003');");

            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000008', 'c-10000004', '2021-10-01', '3000000007_211000004_document1.pdf', 4000.0, 'done', 'b-1000004');");

            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000009', 'c-10000005', '2021-10-01', '3000000008_211000005_document1.pdf', 5000.0, 'done', 'b-1000005');");

            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('30000000010', 'c-10000006', '2021-10-01', '3000000009_211000006_document1.pdf', 6000.0, 'done', 'b-1000006');");

            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000011', 'c-10000007', '2021-10-01', '3000000010_211000007_document1.pdf', 7000.0, 'done', 'b-1000007');");

            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000012', 'c-10000008', '2021-10-01', '3000000011_211000008_document1.pdf', 8000.0, 'done', 'b-1000008');");
            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000013', 'c-10000008', '2023-10-01', '3000000012_211000008_document2.pdf', 80000.0, 'processing', 'b-1000009');");
            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000014', 'c-10000008', '2024-10-01', '3000000013_211000008_document3.pdf', 800000.0, 'new', 'b-1000010');");

            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000015', 'c-10000009', '2021-10-01', '3000000014_211000009_document1.pdf', 9000.0, 'done', 'b-1000009');");
            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000016', 'c-10000009', '2023-10-01', '3000000015_211000009_document2.pdf', 90000.0, 'processing', 'b-1000009');");
            statement.execute("INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) " +
                    "VALUES ('3000000017', 'c-10000009', '2024-10-01', '3000000016_211000009_document3.pdf', 900000.0, 'new', 'b-1000009');");

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
