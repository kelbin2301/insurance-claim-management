package org.rmit.assignment.dao.impl;

import org.rmit.assignment.dao.ClaimDAO;
import org.rmit.assignment.dao.DatabaseInitializer;
import org.rmit.assignment.dao.entity.BankingInfo;
import org.rmit.assignment.dao.entity.Claim;
import org.rmit.assignment.dao.entity.Customer;
import org.rmit.assignment.dao.entity.InsuranceCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClaimDAOImpl implements ClaimDAO {
    @Override
    public List<Claim> getAll() {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "SELECT * FROM claim";

        List<Claim> claimList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Claim claim = new Claim();
                claim.setId(resultSet.getString("id"));
                claim.setCustomerId(resultSet.getString("customer_id"));

                String stringDate = resultSet.getString("exam_date");
                Date date = Date.valueOf(stringDate);
                claim.setExamDate(date);

                claim.setListDocuments(resultSet.getString("list_of_documents"));
                claim.setClaimAmount(resultSet.getDouble("claim_amount"));
                claim.setStatus(resultSet.getString("status"));
                claim.setBankingInfoId(resultSet.getString("receiver_bank_id"));

                claimList.add(claim);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return claimList;
    }

    @Override
    public Optional<Claim> get(String id) {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "SELECT * FROM claim WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Claim claim = new Claim();
                claim.setId(resultSet.getString("id"));
                claim.setCustomerId(resultSet.getString("customer_id"));

                String stringDate = resultSet.getString("exam_date");
                Date date = Date.valueOf(stringDate);
                claim.setExamDate(date);

                claim.setListDocuments(resultSet.getString("list_of_documents"));
                claim.setClaimAmount(resultSet.getDouble("claim_amount"));
                claim.setStatus(resultSet.getString("status"));
                claim.setBankingInfoId(resultSet.getString("receiver_bank_id"));

                return Optional.of(claim);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public void save(Claim claim) {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "INSERT INTO claim (id, customer_id, exam_date, list_of_documents, claim_amount, status, receiver_bank_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, claim.getId());
            preparedStatement.setString(2, claim.getCustomerId());

            preparedStatement.setString(3, claim.getExamDate().toString());
            preparedStatement.setString(4, claim.getListDocuments());
            preparedStatement.setDouble(5, claim.getClaimAmount());
            preparedStatement.setString(6, claim.getStatus());
            preparedStatement.setString(7, claim.getBankingInfoId());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Claim claim) {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "UPDATE claim SET exam_date = ?, list_of_documents = ?, claim_amount = ?, status = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, claim.getExamDate().toString());
            preparedStatement.setString(2, claim.getListDocuments());
            preparedStatement.setDouble(3, claim.getClaimAmount());
            preparedStatement.setString(4, claim.getStatus());
            preparedStatement.setString(5, claim.getId());

            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(Claim claim) {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "DELETE FROM claim WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, claim.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Claim> getAllWithCustomerInfoAndBankInfo(String status) {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "SELECT * FROM claim c JOIN customer cu ON c.customer_id = cu.id JOIN banking_info bi ON c.receiver_bank_id = bi.id";
        if (!status.equalsIgnoreCase("all")) {
            query += " WHERE c.status = '" + status + "'";
        }

        List<Claim> claimList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Claim claim = new Claim();
                claim.setId(resultSet.getString("id"));
                claim.setCustomerId(resultSet.getString("customer_id"));
                claim.setCustomerName(resultSet.getString("full_name"));
                claim.setCustomerType(resultSet.getString("customer_type"));

                String stringDate = resultSet.getString("exam_date");
                Date date = Date.valueOf(stringDate);
                claim.setExamDate(date);

                claim.setListDocuments(resultSet.getString("list_of_documents"));
                claim.setClaimAmount(resultSet.getDouble("claim_amount"));
                claim.setStatus(resultSet.getString("status"));
                claim.setBankingInfoId(resultSet.getString("receiver_bank_id"));

                claim.setBankingName(resultSet.getString("bank_name"));
                claim.setBankingAccountNumber(resultSet.getString("account_number"));

                claimList.add(claim);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return claimList;
    }

    @Override
    public Optional<Claim> getByClaimIdWithAllData(String claimId) {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "SELECT * FROM claim c JOIN customer cu ON c.customer_id = cu.id JOIN banking_info bi ON c.receiver_bank_id = bi.id WHERE c.id = ?";
        String queryInsuranceCard = "SELECT * FROM insurance_card WHERE customer_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, claimId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Claim claim = new Claim();
                claim.setId(resultSet.getString("id"));
                claim.setCustomerId(resultSet.getString("customer_id"));

                Customer customer = new Customer();
                customer.setId(resultSet.getString("customer_id"));
                customer.setFullName(resultSet.getString("full_name"));
                customer.setCustomerType(resultSet.getString("customer_type"));

                String stringDate = resultSet.getString("exam_date");
                Date date = Date.valueOf(stringDate);
                claim.setExamDate(date);

                claim.setListDocuments(resultSet.getString("list_of_documents"));
                claim.setClaimAmount(resultSet.getDouble("claim_amount"));
                claim.setStatus(resultSet.getString("status"));
                claim.setBankingInfoId(resultSet.getString("receiver_bank_id"));

                BankingInfo bankingInfo = new BankingInfo();
                bankingInfo.setBankName(resultSet.getString("bank_name"));
                bankingInfo.setAccountNumber(resultSet.getString("account_number"));
                bankingInfo.setAccountName(resultSet.getString("account_name"));

                String customerId = claim.getCustomerId();
                preparedStatement = connection.prepareStatement(queryInsuranceCard);
                preparedStatement.setString(1, customerId);
                ResultSet insuranceCardResultSet = preparedStatement.executeQuery();

                if (insuranceCardResultSet.next()) {
                    InsuranceCard insuranceCard = new InsuranceCard();
                    insuranceCard.setCardNumber(insuranceCardResultSet.getString("card_number"));
                    insuranceCard.setPolicyOwner(insuranceCardResultSet.getString("policy_owner"));

                    String expirationDateStr = insuranceCardResultSet.getString("expiration_date");
                    Date expirationDate = Date.valueOf(expirationDateStr);

                    insuranceCard.setExpirationDate(expirationDate);
                    customer.setInsuranceCard(insuranceCard);
                }

                claim.setBankingInfo(bankingInfo);
                claim.setCustomer(customer);

                return Optional.of(claim);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }
}
