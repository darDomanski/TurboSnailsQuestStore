package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Person;
import com.codecool.quest_store.model.QSUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QSUserDAO implements PersonDAO {
    private DBConnector connectionPool;

    public QSUserDAO(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Person> getAll(String userTypeToGet) {
        String query = "SELECT qs_user.id, first_name, last_name, email, class_.name AS class_name, " +
                "user_type.user_type_name AS user_type, " +
                "user_status.user_status_name AS STATUS " +
                "FROM qs_user " +
                "INNER JOIN class_ ON qs_user.class_id = class_.class_id " +
                "INNER JOIN user_type ON qs_user.user_type = user_type.user_type_id " +
                "INNER JOIN user_status ON qs_user.STATUS = user_status.user_status_id " +
                "WHERE user_type.user_type_name = '" + userTypeToGet + "'";
        List<Person> users = new ArrayList<Person>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String className = resultSet.getString("class_name");
                String userType = resultSet.getString("user_type");
                String userStatus = resultSet.getString("status");
                Person mentor = new QSUser(id, firstName, lastName, email, className, userType, userStatus);
                users.add(mentor);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println("Error, cant get all objects from database!");
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void insert(Person person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String email = person.getEmail();
        String classId = person.getClassName();
        String userType = person.getUserType();
        String userStatus = person.getStatus();
        String query = "INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status) " +
                "VALUES (?, ?, ?, (SELECT class_id FROM class_ where name=?), " +
                "(SELECT user_type_id FROM user_type WHERE user_type_name = ?), " +
                "(SELECT user_status_id FROM user_status WHERE user_status_name = ?));";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, classId);
            preparedStatement.setString(5, userType);
            preparedStatement.setString(6, userStatus);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println("Can't add object to database!");
            e.printStackTrace();
        }
    }

    @Override
    public void update(int id, String column, String newValue) {
        String query = String.format("UPDATE qs_user SET %s=? WHERE id=?;", column);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, newValue);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Can't update database!");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String query = "DELETE FROM qs_user WHERE id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Can't delete record from database!");
            e.printStackTrace();
        }
    }

    @Override
    public List<Person> getStudentsByClass(String className) {
        String query = "SELECT qs_user.id, first_name, last_name, email, class_.name AS class_name, " +
                "user_type.user_type_name AS user_type, " +
                "user_status.user_status_name AS STATUS " +
                "FROM qs_user " +
                "INNER JOIN class_ ON qs_user.class_id = class_.class_id " +
                "INNER JOIN user_type ON qs_user.user_type = user_type.user_type_id " +
                "INNER JOIN user_status ON qs_user.STATUS = user_status.user_status_id " +
                "WHERE user_type.user_type_name = 'student' AND class_.name = ?";
        List<Person> users = new ArrayList<Person>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, className);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String class_ = resultSet.getString("class_name");
                String userType = resultSet.getString("user_type");
                String userStatus = resultSet.getString("status");
                Person student = new QSUser(id, firstName, lastName, email, class_, userType, userStatus);
                users.add(student);
            }
            preparedStatement.close();
            resultSet.close();
            connection. close();

        } catch (SQLException e) {
            System.err.println("Error, cant get all objects from database!");
            e.printStackTrace();
        }
        return users;
    }
}
