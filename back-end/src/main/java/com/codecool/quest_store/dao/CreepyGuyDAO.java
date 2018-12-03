package com.codecool.quest_store.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CreepyGuyDAO {

//    // for testing purposes
//    public static void main(String[] args) {
//        CreepyGuyDAO guyDao = new CreepyGuyDAO();
//        int[] update = new int[] {60, 61, 160, 161, 260, 261, 360, 361};
//        guyDao.updateAccessLevels(update);
//        int[] result = guyDao.getAccessLevels();
//        for (int i : result) {
//            System.out.println(i);
//        }
//    }

    public void createClass(String className) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO class_ (name) VALUES (?)";

        try {
            connection = DBConnector.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, className);
            preparedStatement.execute();

            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int[] getAccessLevels() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM access_level";
        List<Integer> accessLevelsList = new ArrayList<>();
        int[] accessLevels = new int[8];

        try {
            connection = DBConnector.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            int resultCounter = 0;

            while (resultSet.next()) {
                if (resultCounter > 0) {
                    accessLevelsList.add(Integer.parseInt(resultSet.getString("min_lifetime_coins")));
                }
                if (resultCounter < 4) {
                    accessLevelsList.add(Integer.parseInt(resultSet.getString("max_lifetime_coins")));
                }
                resultCounter += 1;
            }
            connection.close();
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (int index = 0; index < accessLevelsList.size(); index++) {
            accessLevels[index] = accessLevelsList.get(index).intValue();
        }
        return accessLevels;
    }

    public void updateAccessLevels(int[] update) {
        final int NUMBER_OF_ACCESS_RANGES = 8;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = "UPDATE access_level SET max_lifetime_coins = ? WHERE level_id = 1; " +
                "UPDATE access_level SET min_lifetime_coins = ?, max_lifetime_coins = ? WHERE level_id = 2; " +
                "UPDATE access_level SET min_lifetime_coins = ?, max_lifetime_coins = ? WHERE level_id = 3; " +
                "UPDATE access_level SET min_lifetime_coins = ?, max_lifetime_coins = ? WHERE level_id = 4; " +
                "UPDATE access_level SET min_lifetime_coins = ? WHERE level_id = 5;";

        try {
            connection = DBConnector.getConnection();
            preparedStatement = connection.prepareStatement(query);

            for (int i = 0; i < NUMBER_OF_ACCESS_RANGES; i++) {
                int index = i + 1;
                preparedStatement.setInt(index, update[i]);
            }
            preparedStatement.executeUpdate();

            connection.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
