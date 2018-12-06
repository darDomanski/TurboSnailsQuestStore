package com.codecool.quest_store.dao;

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
    public List<Item> getListOfItemsByUserId(String userId) {
        List<Item> inventory = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM artifact INNER JOIN student_artifact");
            resultSet = preparedStatement.executeQuery();
            // inventory = null; TODO

            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return inventory;
    }
}
