package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CrowdfundingDAOImpl implements CrowdfundingDAO {
    private DBConnector connectionPool;
    private ItemDAO artifactDAO;

    public CrowdfundingDAOImpl(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
        this.artifactDAO = new ArtifactDAO(connectionPool);
    }

    @Override
    public int getCollectedMoneyByArtifactId(int artifactId) {
        int moneyCollected = 0;
        String query = "SELECT money_collected FROM crowdfunding WHERE artifact_id=?;";

        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, artifactId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                moneyCollected = resultSet.getInt("money_collected");
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return moneyCollected;
    }

    @Override
    public void addCollectedMoney(int amountOfMoney, int artifactId) {
        String query = String.format("UPDATE crowdfunding SET money_collected = money_collected + %d WHERE artifact_id = ?;", amountOfMoney);

        Connection connection;
        PreparedStatement preparedStatement;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, artifactId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Item> getAllItems() {
        List<Item> allItems = artifactDAO.getAll();
        List<Item> crowdfundingItems = new ArrayList<>();
        Set<Integer> crowdfundingItemsId = new HashSet<>();

        String query = "SELECT artifact_id FROM crowdfunding";

        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet resultSet;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int itemId = resultSet.getInt("artifact_id");
                crowdfundingItemsId.add(itemId);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (Item item : allItems) {
            if (crowdfundingItemsId.contains(item.getId())) {
                crowdfundingItems.add(item);
            }
        }
        return crowdfundingItems;
    }
}
