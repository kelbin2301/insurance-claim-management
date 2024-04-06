package org.rmit.assignment.dao;

import org.rmit.assignment.dao.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO extends BaseDAO<Customer> {

    List<Customer> getCustomersWithInsuranceCardAndClaimCount();

    Optional<Customer> getWithInsuranceCard(String id);
}
