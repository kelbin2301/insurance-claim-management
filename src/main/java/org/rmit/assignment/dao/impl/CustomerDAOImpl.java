package org.rmit.assignment.dao.impl;

import org.rmit.assignment.dao.CustomerDAO;
import org.rmit.assignment.dao.DatabaseInitializer;
import org.rmit.assignment.dao.entity.Customer;
import org.rmit.assignment.dao.entity.InsuranceCard;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public List<Customer> getAll() {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "SELECT * FROM customer";

        List<Customer> customerList = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getString("id"));
                customer.setFullName(resultSet.getString("full_name"));
                customer.setCustomerType(resultSet.getString("customer_type"));

                customerList.add(customer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return customerList;
    }

    @Override
    public Optional<Customer> get(String id) {
        String query = "SELECT * FROM customer WHERE id = ?";
        Connection connection = DatabaseInitializer.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getString("id"));
                customer.setFullName(resultSet.getString("full_name"));
                customer.setCustomerType(resultSet.getString("customer_type"));

                return Optional.of(customer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public void save(Customer customer) {
        Connection connection = DatabaseInitializer.getInstance().getConnection();

        String insertCustomer;
        if (customer.getDependentOf() == null) {
            insertCustomer = "INSERT INTO customer (id, full_name, customer_type) VALUES (?, ?, ?)";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertCustomer);
                preparedStatement.setString(1, customer.getId());
                preparedStatement.setString(2, customer.getFullName());
                preparedStatement.setString(3, customer.getCustomerType());

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            insertCustomer = "INSERT INTO customer (id, full_name, customer_type, dependent_of) VALUES (?, ?, ?, ?)";

            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertCustomer);
                preparedStatement.setString(1, customer.getId());
                preparedStatement.setString(2, customer.getFullName());
                preparedStatement.setString(3, customer.getCustomerType());
                preparedStatement.setString(4, customer.getDependentOf());

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        InsuranceCard insuranceCard = customer.getInsuranceCard();
        if (insuranceCard != null) {
            String insertInsuranceCard = "INSERT INTO insurance_card (card_number, customer_id, policy_owner, expiration_date) VALUES (?, ?, ?, ?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(insertInsuranceCard);
                preparedStatement.setString(1, insuranceCard.getCardNumber());
                preparedStatement.setString(2, customer.getId());
                preparedStatement.setString(3, insuranceCard.getPolicyOwner());
                preparedStatement.setString(4, insuranceCard.getExpirationDate().toString());

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }


    @Override
    public List<Customer> getCustomersWithInsuranceCardAndClaimCount() {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "SELECT c.id, c.full_name, c.customer_type, ic.card_number, ic.policy_owner, ic.expiration_date, COUNT(cl.id) AS claim_count, SUM(cl.claim_amount) as total_claim_amount FROM customer c " +
                "LEFT JOIN insurance_card ic ON c.id = ic.customer_id " +
                "LEFT JOIN claim cl ON c.id = cl.customer_id " +
                "GROUP BY c.id";

        List<Customer> customerList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getString("id"));
                customer.setFullName(resultSet.getString("full_name"));
                customer.setCustomerType(resultSet.getString("customer_type"));

                InsuranceCard insuranceCard = new InsuranceCard();
                insuranceCard.setCardNumber(resultSet.getString("card_number"));
                insuranceCard.setPolicyOwner(resultSet.getString("policy_owner"));

                String expirationDateString = resultSet.getString("expiration_date");
                Date expirationDate = Date.valueOf(expirationDateString);
                insuranceCard.setExpirationDate(expirationDate);

                customer.setInsuranceCard(insuranceCard);
                customer.setClaimCount(resultSet.getInt("claim_count"));
                customer.setTotalClaimAmount(resultSet.getDouble("total_claim_amount"));

                customerList.add(customer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return customerList;
    }

    @Override
    public Optional<Customer> getWithInsuranceCard(String id) {
        String query = "SELECT * FROM customer c INNER JOIN insurance_card ic ON c.id = ic.customer_id WHERE c.id = ?";
        Connection connection = DatabaseInitializer.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getString("id"));
                customer.setFullName(resultSet.getString("full_name"));
                customer.setCustomerType(resultSet.getString("customer_type"));

                InsuranceCard insuranceCard = new InsuranceCard();
                insuranceCard.setCardNumber(resultSet.getString("card_number"));

                String stringDate = resultSet.getString("expiration_date");
                Date date = Date.valueOf(stringDate);

                insuranceCard.setExpirationDate(date);
                insuranceCard.setPolicyOwner(resultSet.getString("policy_owner"));

                customer.setInsuranceCard(insuranceCard);
                return Optional.of(customer);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
