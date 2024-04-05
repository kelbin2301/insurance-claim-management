package org.rmit.assignment.service;

import org.rmit.assignment.dao.entity.Claim;

import java.util.List;

public interface ClaimService {
    void add(Claim claim);
    void update(Claim claim);
    void delete(String claimId);
    Claim getOne(String claimId);
    List<Claim> getAll();
}