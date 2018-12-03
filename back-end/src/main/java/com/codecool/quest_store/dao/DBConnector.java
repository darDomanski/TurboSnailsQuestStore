package com.codecool.quest_store.dao;


import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnector {

    private static BasicDataSource dataSource;

    public DBConnector(){
        this.dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/quest_store");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        dataSource.setMinIdle(5);
        dataSource.setMaxIdle(10);
        dataSource.setMaxOpenPreparedStatements(100);
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