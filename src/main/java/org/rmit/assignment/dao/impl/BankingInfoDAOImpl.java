package org.rmit.assignment.dao.impl;

import org.rmit.assignment.dao.BankingInfoDAO;
import org.rmit.assignment.dao.DatabaseInitializer;
import org.rmit.assignment.dao.entity.BankingInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BankingInfoDAOImpl implements BankingInfoDAO {
    @Override
    public List<BankingInfo> getAll() {
        Connection connection = DatabaseInitializer.getInstance().getConnection();
        String query = "SELECT * FROM banking_info";

        List<BankingInfo> bankingListInfo = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                BankingInfo bankingInfo = new BankingInfo();
                bankingInfo.setId(resultSet.getString("id"));
                bankingInfo.setBankName(resultSet.getString("bank_name"));
                bankingInfo.setAccountNumber(resultSet.getString("account_number"));
                bankingInfo.setAccountName(resultSet.getString("account_name"));

                bankingListInfo.add(bankingInfo);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bankingListInfo;
    }

    @Override
    public Optional<BankingInfo> get(String id) {
        String query = "SELECT * FROM banking_info WHERE id = ?";
        Connection connection = DatabaseInitializer.getInstance().getConnection();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                BankingInfo bankingInfo = new BankingInfo();
                bankingInfo.setId(resultSet.getString("id"));
                bankingInfo.setBankName(resultSet.getString("bank_name"));
                bankingInfo.setAccountNumber(resultSet.getString("account_number"));
                bankingInfo.setAccountName(resultSet.getString("account_name"));

                return Optional.of(bankingInfo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    @Override
    public void save(BankingInfo bankingInfo) {
        String query = "INSERT INTO banking_info (id, bank_name, account_number, account_name) VALUES (?, ?, ?, ?)";
        Connection connection = DatabaseInitializer.getInstance().getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bankingInfo.getId());
            preparedStatement.setString(2, bankingInfo.getBankName());
            preparedStatement.setString(3, bankingInfo.getAccountNumber());
            preparedStatement.setString(4, bankingInfo.getAccountName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(BankingInfo bankingInfo) {

    }

    @Override
    public void delete(BankingInfo bankingInfo) {

    }
}
