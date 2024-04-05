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

import java.util.List;


public class Application {
    public static void main(String[] args) {
        initDatabase();

        BankingInfoDAO bankingInfoDAO = new BankingInfoDAOImpl();
        CustomerDAO customerDAO = new CustomerDAOImpl();
        ClaimDAO claimDAO = new ClaimDAOImpl();
        List<Claim> all = claimDAO.getAll();
        for (Claim claim : all) {
            System.out.println(claim);
        }

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

    public static void initDatabase() {
        String dbPath = "src/main/resources/database.db";
        DatabaseInitializer.initDatabase(dbPath);
    }

    public static void closeDatabase() {
        DatabaseInitializer.getInstance().closeConnection();
    }

}
