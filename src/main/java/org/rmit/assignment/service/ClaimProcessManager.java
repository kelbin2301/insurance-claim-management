package org.rmit.assignment.service;

import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.dao.entity.Customer;

import java.util.List;

public interface ClaimProcessManager {
    void add(Claim claim);
    void update(Claim claim);
    void delete(Claim claim);
    Claim getOne(String claimId);

    Claim getOneWithAllData(String claimId);

    List<Claim> getAllClaims(String status);

    List<Customer> getAllCustomersInformation();
}
