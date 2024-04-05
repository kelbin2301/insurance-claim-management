package org.rmit.assignment.dao;

import org.rmit.assignment.dao.entity.Customer;

import java.util.List;

public interface CustomerDAO extends BaseDAO<Customer> {

    List<Customer> getPolicyOwnerWithDependents();

    List<Customer> getCustomerWithInsuranceCard();
}
