package org.rmit.assignment.dao;

import org.rmit.assignment.dao.entity.Claim;

import java.util.List;
import java.util.Optional;

public interface ClaimDAO extends BaseDAO<Claim>{

    List<Claim> getAllWithCustomerInfoAndBankInfo(String status);

    Optional<Claim> getByClaimIdWithAllData(String claimId);
}
