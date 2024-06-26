package org.rmit.assignment.service.impl;

import org.rmit.assignment.dao.BankingInfoDAO;
import org.rmit.assignment.dao.ClaimDAO;
import org.rmit.assignment.dao.CustomerDAO;
import org.rmit.assignment.dao.entity.BankingInfo;
import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.dao.entity.Customer;
import org.rmit.assignment.dao.entity.InsuranceCard;
import org.rmit.assignment.enumeration.ClaimStatus;
import org.rmit.assignment.service.ClaimProcessManager;
import org.rmit.assignment.utils.IdGeneratorUtils;

import java.util.*;

public class ClaimProcessManagerImpl implements ClaimProcessManager {

    private final ClaimDAO claimDAO;

    private final BankingInfoDAO bankingInfoDAO;

    private final CustomerDAO customerDAO;

    public ClaimProcessManagerImpl(ClaimDAO claimDAO, BankingInfoDAO bankingInfoDAO, CustomerDAO customerDAO) {
        this.claimDAO = claimDAO;
        this.bankingInfoDAO = bankingInfoDAO;
        this.customerDAO = customerDAO;
    }


    @Override
    public void add(Claim claim) {
        claim.setId(IdGeneratorUtils.generateClaimId());
        claim.setStatus(ClaimStatus.NEW.getValue());

        Optional<Customer> customerOptional = customerDAO.getWithInsuranceCard(claim.getCustomerId());
        if (customerOptional.isEmpty()) {
            System.err.println("Customer does not exist");
            return;
        }


        boolean existsBankingInfo = bankingInfoDAO.get(claim.getBankingInfoId()).isPresent();
        if (!existsBankingInfo) {
            BankingInfo bankingInfo = claim.getBankingInfo();

            String bankingInfoId = IdGeneratorUtils.generateBankingInfoId();
            bankingInfo.setId(bankingInfoId);
            bankingInfoDAO.save(bankingInfo);

            claim.setBankingInfoId(bankingInfoId);
        }

        InsuranceCard insuranceCard = customerOptional.get().getInsuranceCard();
        String formattedDocumentList = formatListDocuments(claim.getListDocuments(), claim.getId(), insuranceCard.getCardNumber());

        claim.setListDocuments(formattedDocumentList);
        claimDAO.save(claim);
    }

    @Override
    public void update(Claim claim) {
        String formattedDocuments = formatListDocuments(claim.getListDocuments(), claim.getId(), claim.getCustomerId());
        claim.setListDocuments(formattedDocuments);

        claimDAO.update(claim);
    }

    @Override
    public void delete(Claim claim) {
        claimDAO.delete(claim);
    }

    @Override
    public Claim getOne(String claimId) {
        Optional<Claim> claim = claimDAO.get(claimId);
        if (claim.isPresent()) {
            return claim.get();
        }
        return null;
    }

    @Override
    public Claim getOneWithAllData(String claimId) {
        Optional<Claim> claimOptional = claimDAO.getByClaimIdWithAllData(claimId);
        return claimOptional.orElse(null);
    }

    @Override
    public List<Claim> getAllClaims(String status) {
        if (!status.equalsIgnoreCase("all")) {
            ClaimStatus claimStatus = ClaimStatus.fromValue(status);
            if (claimStatus == null) {
                System.out.println("Invalid query status");
                return Collections.emptyList();
            }
        }

        List<Claim> allWithCustomerInfoAndBankInfo = claimDAO.getAllWithCustomerInfoAndBankInfo(status);
        allWithCustomerInfoAndBankInfo.sort(Comparator.comparing(Claim::getExamDate).reversed());
        return allWithCustomerInfoAndBankInfo;
    }

    @Override
    public List<Customer> getAllCustomersInformation() {
        return customerDAO.getCustomersWithInsuranceCardAndClaimCount();
    }

    @Override
    public void addCustomer(Customer customer) {
        customer.setId(IdGeneratorUtils.generateCustomerId());

        customer.getInsuranceCard().setCardNumber(IdGeneratorUtils.generateInsuranceCardNumber());

        customerDAO.save(customer);
    }

    private String formatListDocuments(String rawDocumentList, String claimId, String insuranceCardNumber) {
        String[] claimsDocuments = rawDocumentList.split(",");

        Optional<String> formattedDocuments = Arrays.stream(claimsDocuments)
                .map(document -> String.format("%s_%s_%s", claimId, insuranceCardNumber, document))
                .reduce((document1, document2) -> document1 + "," + document2);

        if (formattedDocuments.isEmpty()) {
            throw new RuntimeException("Error while formatting documents");
        }

        return formattedDocuments.get();
    }

}
