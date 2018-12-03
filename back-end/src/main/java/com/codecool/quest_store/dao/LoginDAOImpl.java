package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.CreepyGuy;
import com.codecool.quest_store.model.Person;
import com.codecool.quest_store.model.QSUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAOImpl implements LoginDAO {
    private Connection connection;

    public LoginDAOImpl(Connection connection) {
        this.connection = connection;
    }

    public Person getPersonByLoginPassword(String login, String password) {
        Person person = null;
        String id = checkIfIdIsEmpty(login, password);

        if (id == null) {
            person = new CreepyGuy();
        } else {
            person = getPersonOtherThanCreepyGuy(login, password);
        }
        return person;
    }

    private String checkIfIdIsEmpty(String login, String password) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String id = null;
        String query = "SELECT id FROM login_data WHERE login = ? AND password = ?";

        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            id = resultSet.getString("id");

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private Person getPersonOtherThanCreepyGuy(String login, String password) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Person person = null;
        String query = "SELECT qs_user.id, first_name, last_name, email, class_.name AS class, " +
                "user_type.user_type_name AS user_type, user_status.user_status_name AS STATUS " +
                "FROM qs_user " +
                "INNER JOIN login_data ON qs_user.id = login_data.id " +
                "INNER JOIN class_ ON qs_user.class_id = class_.class_id " +
                "INNER JOIN user_type ON qs_user.user_type = user_type.user_type_id " +
                "INNER JOIN user_status ON qs_user.STATUS = user_status.user_status_id " +
                "WHERE login=? AND password=?";

        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            resultSet.next();
            int user_id = Integer.parseInt(resultSet.getString("id"));
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String eMail = resultSet.getString("email");
            String classId = resultSet.getString("class");
            String userType = resultSet.getString("user_type");
            String status = resultSet.getString("status");

            person = new QSUser(user_id, firstName, lastName, eMail, classId, userType, status);

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    public void addPerson(int id, String login, String password) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO login_data VALUES (?, ?, ?)";

        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePerson(int id, String value, String valueType) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = "UPDATE login_data SET %s = ? WHERE id = ?";

        try {
            // below statement should be done only when valueType is first checked in mainController
            preparedStatement = this.connection.prepareStatement(String.format(query, valueType));
            preparedStatement.setString(1, value);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletePerson(int id) {
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM login_data WHERE id = ?";

        try {
            preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}