package org.rmit.assignment.dao.impl;

import org.rmit.assignment.dao.ClaimDAO;
import org.rmit.assignment.dao.DatabaseInitializer;
import org.rmit.assignment.dao.entity.Claim;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
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
    public Optional<Claim> get(int id) {
        return Optional.empty();
    }

    @Override
    public void save(Claim claim) {

    }

    @Override
    public void update(Claim claim) {

    }

    @Override
    public void delete(Claim claim) {

    }
}
