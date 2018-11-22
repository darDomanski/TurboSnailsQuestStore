package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;


public class CreepyGuyDAO {

    public void createClass(String className) {

        Connection connection = null;
        PreparedStatement findUser = null;
        String statement = "INSERT INTO class_ (name) VALUES (?)";

        try {
            connection = DBConnector.getConnection();
            findUser = connection.prepareStatement(statement);
            findUser.setString(1, className);
            findUser.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                findUser.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    
}
