package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Mentor;
import com.codecool.quest_store.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MentorDAO implements PersonDAO {

    @Override
    public List<Person> getAll() {
        String sql = "SELECT qs_user.id, first_name, last_name, email, class_.name AS class_name, " +
                "user_type.user_type_name AS user_type, " +
                "user_status.user_status_name AS STATUS " +
                "FROM qs_user " +
                "INNER JOIN class_ ON qs_user.class_id = class_.class_id " +
                "INNER JOIN user_type ON qs_user.user_type = user_type.user_type_id " +
                "INNER JOIN user_status ON qs_user.STATUS = user_status.user_status_id " +
                "WHERE user_type.user_type_name = 'mentor'";
        List<Person> mentors = new ArrayList<Person>();

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = DBConnector.getConnection();
            ps = connection.prepareStatement(sql);
            resultSet = ps.executeQuery();
            while (resultSet.next()) {
                int ID = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String email = resultSet.getString("email");
                String className = resultSet.getString("class_name");
                String userType = resultSet.getString("user_type");
                String userStatus = resultSet.getString("status");
                Person mentor = new Mentor(ID, firstName, lastName, email, className, userType, userStatus);
                mentors.add(mentor);
            }
            ps.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            System.err.println("Error, cant get all objects from database!");
            e.printStackTrace();
        }
        return mentors;
    }

    @Override
    public void insert(Person person) {
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        String email = person.getEmail();
        String classId = person.getClassName();
        String userType = person.getUserType();
        String userStatus = person.getStatus();
        String sql = "INSERT INTO qs_user (first_name, last_name, email, class_id, user_type, status) " +
                "VALUES (?, ?, ?, (SELECT class_id FROM class_ where name=?), " +
                "(SELECT user_type_id FROM user_type WHERE user_type_name = ?), " +
                "(SELECT user_status_id FROM user_status WHERE user_status_name = ?));";

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DBConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
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
        String sql = String.format("UPDATE qs_user SET %s=? WHERE id=?;", column);

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
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
        String sql = "DELETE FROM qs_user WHERE id=?";

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnector.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Can't delete record from database!");
            e.printStackTrace();
        }
    }
}
