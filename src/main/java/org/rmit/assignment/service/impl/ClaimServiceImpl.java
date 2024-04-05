package org.rmit.assignment.service.impl;

import org.rmit.assignment.dao.ClaimDAO;
import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.service.ClaimService;

import java.util.List;

public class ClaimServiceImpl implements ClaimService {

    private final ClaimDAO claimDAO;

    public ClaimServiceImpl(ClaimDAO claimDAO) {
        this.claimDAO = claimDAO;
    }

    @Override
    public void add(Claim claim) {

    }

    @Override
    public void update(Claim claim) {

    }

    @Override
    public void delete(String claimId) {

    }

    @Override
    public Claim getOne(String claimId) {
        return null;
    }

    @Override
    public List<Claim> getAll() {
        return claimDAO.getAll();
    }

}
