package org.rmit.assignment.dao.impl;

import org.rmit.assignment.dao.CustomerDAO;
import org.rmit.assignment.dao.DatabaseInitializer;
import org.rmit.assignment.dao.entity.Customer;

import java.sql.Connection;
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
    public Optional<Customer> get(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Customer customer) {

    }

    @Override
    public void update(Customer customer) {

    }

    @Override
    public void delete(Customer customer) {

    }

    @Override
    public List<Customer> getPolicyOwnerWithDependents() {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String getPolicyOwnerQuery = "SELECT * FROM customer WHERE customer_type = 'policy_holder'";
        String getDependentsQuery = "SELECT * FROM customer WHERE dependent_of IS NOT NULL";


        List<Customer> customerList = new ArrayList<>();
        Map<String, List<Customer>> mapDependents = new HashMap<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet policyOwnerResultSet = statement.executeQuery(getPolicyOwnerQuery);

            while (policyOwnerResultSet.next()) {
                Customer customer = new Customer();
                customer.setId(policyOwnerResultSet.getString("id"));
                customer.setFullName(policyOwnerResultSet.getString("full_name"));
                customer.setCustomerType(policyOwnerResultSet.getString("customer_type"));

                customerList.add(customer);
            }

            ResultSet dependentsResultSet = statement.executeQuery(getDependentsQuery);
            while (dependentsResultSet.next()) {
                Customer dependent = new Customer();
                dependent.setId(dependentsResultSet.getString("id"));
                dependent.setFullName(dependentsResultSet.getString("full_name"));
                dependent.setCustomerType(dependentsResultSet.getString("customer_type"));
                String dependentOf = dependentsResultSet.getString("dependent_of");

                if (mapDependents.containsKey(dependentOf)) {
                    mapDependents.get(dependentOf).add(dependent);
                } else {
                    List<Customer> dependents = new ArrayList<>();
                    dependents.add(dependent);
                    mapDependents.put(dependentOf, dependents);
                }
            }

            for (Customer customer : customerList) {
                if (mapDependents.containsKey(customer.getId())) {
                    List<Customer> dependents = mapDependents.get(customer.getId());
                    customer.setDependents(dependents);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return customerList;
    }

    @Override
    public List<Customer> getCustomerWithInsuranceCard() {
        return null;
    }
}
