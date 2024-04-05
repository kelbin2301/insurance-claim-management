package org.rmit.assignment;

import org.rmit.assignment.dao.BankingInfoDAO;
import org.rmit.assignment.dao.ClaimDAO;
import org.rmit.assignment.dao.CustomerDAO;
import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.dao.entity.Customer;
import org.rmit.assignment.dao.impl.BankingInfoDAOImpl;
import org.rmit.assignment.dao.DatabaseInitializer;
import org.rmit.assignment.dao.impl.ClaimDAOImpl;
import org.rmit.assignment.dao.impl.CustomerDAOImpl;
import org.rmit.assignment.presentation.MainMenuApp;
import org.rmit.assignment.service.ClaimProcessManager;
import org.rmit.assignment.service.impl.ClaimProcessManagerImpl;

import java.util.List;


public class Application {
    private static ClaimDAO claimDAO;
    private static CustomerDAO customerDAO;
    private static BankingInfoDAO bankingInfoDAO;
    private static ClaimProcessManager claimProcessManager;
    private static MainMenuApp mainMenuApp;
    public static void main(String[] args) {
        initDatabase();

        initAppObjects();

        if (mainMenuApp == null) {
            System.out.println("App objects are not initialized!");
            return;
        }
        mainMenuApp.start();

        closeDatabase();
    }

    public static void testCustomerWithDependents(List<Customer> customers) {
        for (Customer customer : customers) {
            System.out.println(customer);
            List<Customer> dependents = customer.getDependents();
            if (dependents != null) {
                for (Customer dependent : dependents) {
                    System.out.println(dependent);
                }
            }
        }
    }

    public static void initAppObjects() {
        bankingInfoDAO = new BankingInfoDAOImpl();
        customerDAO = new CustomerDAOImpl();
        claimDAO = new ClaimDAOImpl();

        claimProcessManager = new ClaimProcessManagerImpl(claimDAO, bankingInfoDAO, customerDAO);

        mainMenuApp = new MainMenuApp(claimProcessManager);
    }


    public static void initDatabase() {
        String dbPath = "src/main/resources/database.db";
        DatabaseInitializer.initDatabase(dbPath);
    }

    public static void closeDatabase() {
        DatabaseInitializer.getInstance().closeConnection();
    }

}
