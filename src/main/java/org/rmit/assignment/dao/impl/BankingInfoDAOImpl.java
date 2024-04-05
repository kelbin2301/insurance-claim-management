package org.rmit.assignment.dao.impl;

import org.rmit.assignment.dao.BankingInfoDAO;
import org.rmit.assignment.dao.BaseDAO;
import org.rmit.assignment.dao.DatabaseInitializer;
import org.rmit.assignment.dao.entity.BankingInfo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public Optional<BankingInfo> get(int id) {
        return Optional.empty();
    }

    @Override
    public void save(BankingInfo bankingInfo) {

    }

    @Override
    public void update(BankingInfo bankingInfo) {

    }

    @Override
    public void delete(BankingInfo bankingInfo) {

    }
}
