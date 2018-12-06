package com.codecool.quest_store.dao;


import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public final class DBConnector {

    private static DBConnector instance = new DBConnector();
    private static BasicDataSource dataSource;

    private DBConnector(){
        this.dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/quest_store");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(100);
        dataSource.setMaxOpenPreparedStatements(100);
    }

    public static DBConnector getInstance() {
        if (instance == null) {
            instance = new DBConnector();
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}