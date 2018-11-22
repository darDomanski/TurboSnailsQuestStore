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
//        //int[] update = new int[] {50, 51, 150, 151, 250, 251, 350, 351};
//        //guyDao.updateAccessLevels(update);
//        int[] result = guyDao.getAccessLevels();
//        for (int i : result) {
//            System.out.println(i);
//        }
//    }

    public void createClass(String className) {

        Connection connection = null;
        PreparedStatement query = null;
        String statement = "INSERT INTO class_ (name) VALUES (?)";

        try {
            connection = DBConnector.getConnection();
            query = connection.prepareStatement(statement);
            query.setString(1, className);
            query.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                query.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public int[] getAccessLevels() {

        Connection connection = null;
        PreparedStatement query = null;
        ResultSet resultSet = null;
        String statement = "SELECT * FROM access_level";
        List<Integer> accessLevelsList = new ArrayList<>();
        int[] accessLevels = new int[8];

        try {
            connection = DBConnector.getConnection();
            query = connection.prepareStatement(statement);
            resultSet = query.executeQuery();
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

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                query.close();
                resultSet.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        for (int index = 0; index < accessLevelsList.size(); index++) {
            accessLevels[index] = accessLevelsList.get(index).intValue();
        }
        return accessLevels;

    }

    public void updateAccessLevels(int[] update) {

        Connection connection = null;
        PreparedStatement query = null;
        String statement = "UPDATE access_level SET max_lifetime_coins = ? WHERE level_id = 1; " +
                "UPDATE access_level SET min_lifetime_coins = ?, max_lifetime_coins = ? WHERE level_id = 2; " +
                "UPDATE access_level SET min_lifetime_coins = ?, max_lifetime_coins = ? WHERE level_id = 3; " +
                "UPDATE access_level SET min_lifetime_coins = ?, max_lifetime_coins = ? WHERE level_id = 4; " +
                "UPDATE access_level SET min_lifetime_coins = ? WHERE level_id = 5;";

        try {
            connection = DBConnector.getConnection();
            query = connection.prepareStatement(statement);
            query.setInt(1, update[0]);
            query.setInt(2, update[1]);
            query.setInt(3, update[2]);
            query.setInt(4, update[3]);
            query.setInt(5, update[4]);
            query.setInt(6, update[5]);
            query.setInt(7, update[6]);
            query.setInt(8, update[7]);
            query.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                query.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
