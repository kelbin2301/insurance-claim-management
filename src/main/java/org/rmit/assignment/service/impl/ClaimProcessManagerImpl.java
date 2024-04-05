package org.rmit.assignment.service.impl;

import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.service.ClaimProcessManager;
import org.rmit.assignment.service.ClaimService;

import java.util.List;

public class ClaimProcessManagerImpl implements ClaimProcessManager {

    private final ClaimService claimService;

    public ClaimProcessManagerImpl(ClaimService claimService) {
        this.claimService = claimService;
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
        return claimService.getAll();
    }

}
