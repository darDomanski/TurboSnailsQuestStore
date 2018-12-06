package com.codecool.quest_store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LevelsDAOImpl implements LevelsDAO {
    private DBConnector connectionPool;


    public LevelsDAOImpl(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public int getStudentLevel(int studentId) {
        int level = 0;
        String query = "SELECT level_id FROM access_level " +
                "INNER JOIN wallet " +
                "ON wallet.lifetime_coins <= access_level.max_lifetime_coins " +
                "AND wallet.lifetime_coins >= access_level.min_lifetime_coins " +
                "WHERE wallet.student_id = ?;";

        Connection connection = null;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, studentId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                level = resultSet.getInt("level_id");
            }

            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Can't get student's level!");
            e.printStackTrace();
        }
        return level;
    }

    @Override
    public void updateLevel(int levelId, int minCoolcoinsAmount, int maxCoolcoinsAmoutudent/questnt) {
        String query = "UPDATE access_level " +
                "SET min_lifetime_coins = ?, " +
                "max_lifetime_coins = ? " +
                "WHERE level_id =?;";
        Connection connection = null;
        PreparedStatement preparedStatement;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, minCoolcoinsAmount);
            preparedStatement.setInt(2, maxCoolcoinsAmount);
            preparedStatement.setInt(3, levelId);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Can't update access level!");
            e.printStackTrace();

        }
    }
}
