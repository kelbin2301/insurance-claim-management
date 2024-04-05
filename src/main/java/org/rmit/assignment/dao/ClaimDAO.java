package org.rmit.assignment.dao;

import org.rmit.assignment.dao.entity.Claim;

import java.util.List;

public interface ClaimDAO extends BaseDAO<Claim>{

    List<Claim> getAllWithCustomerInfoAndBankInfo();
}
