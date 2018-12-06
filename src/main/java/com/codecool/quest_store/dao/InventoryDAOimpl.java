package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Artifact;
import com.codecool.quest_store.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAOimpl implements InventoryDAO {
    private DBConnector connectionPool;

    public InventoryDAOimpl(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Item> getListOfItemsByUserId(int userId) {
        List<Item> inventory = new ArrayList<>();
        String query = "SELECT * FROM artifact INNER JOIN student_artifact ON" +
                " artifact.id = student_artifact.artifact_id WHERE student_artifact.student_id = ?";
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            resultSet = preparedStatement.executeQuery();
            inventory = getInventoryItems(resultSet);

            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return inventory;
    }

    private List<Item> getInventoryItems(ResultSet resultSet) {
        List<Item> inventory = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer access_level = resultSet.getInt("access_level");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Integer artifact_price = resultSet.getInt("artifact_price");
                String artifact_type = resultSet.getString("artifact_type");
                Item artifact = new Artifact(id, access_level, title, description, artifact_price, artifact_type);
                inventory.add(artifact);
            }
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return inventory;
    }
}
