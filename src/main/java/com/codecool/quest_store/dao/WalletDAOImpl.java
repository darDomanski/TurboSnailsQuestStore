package com.codecool.quest_store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletDAOImpl implements WalletDAO {

    private Connection connection;

    public WalletDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int getStudentsCoolcoinsAmount(int studentId, String columnName) {
        int coolcoinsAmount = 0;
        String query = "SELECT * FROM wallet WHERE student_id = ?;";

        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                coolcoinsAmount = resultSet.getInt(columnName);
            }

            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.err.println("Can't get students coolcoins amount!");
            e.printStackTrace();
        }
        return coolcoinsAmount;
    }

    @Override
    public void changeCoolcoinsAmount(int coolcoinsAmount, String columnName, int studentId) {
        String query = String.format("UPDATE wallet SET %s=? WHERE student_id=?;", columnName);

        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, coolcoinsAmount);
            preparedStatement.setInt(2, studentId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("Can't change students coolcoin amount!");
            e.printStackTrace();
        }
    }

}
