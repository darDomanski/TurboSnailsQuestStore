package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Person;
import com.codecool.quest_store.model.Mentor;
import com.codecool.quest_store.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAOImpl implements LoginDAO {

//    // for testing purposes
//    public static void main(String[] args) {
//        LoginDAO loginDao = new LoginDAOImpl();
//        loginDao.getPersonByLoginPassword("dario", "darek123");
//    }

    public Person getPersonByLoginPassword(String login, String password) {

        Person person = null;
        String id = checkIfIdIsEmpty(login, password);

        if (id == null) {
            // System.out.println("kripigaj");
            // person = new CreepyGuy();
        } else {
            person = getPersonOtherThanCreepyGuy(login, password);
        }
        return person;
    }

    private String checkIfIdIsEmpty(String login, String password) {

        Connection connection = null;
        PreparedStatement findUser = null;
        ResultSet resultSet = null;
        String id = null;
        String statement = "SELECT id FROM login_data WHERE login = ? AND password = ?";

        try {
            connection = DBConnector.getConnection();
            findUser = connection.prepareStatement(statement);
            findUser.setString(1, login);
            findUser.setString(2, password);
            resultSet = findUser.executeQuery();

            resultSet.next();
            id = resultSet.getString("id");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                findUser.close();
                resultSet.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return id;
    }

    private Person getPersonOtherThanCreepyGuy(String login, String password) {

        Connection connection = null;
        PreparedStatement findUser = null;
        ResultSet resultSet = null;
        Person person = null;
        String statement = "SELECT qs_user.id, first_name, last_name, email, class_.name AS class, " +
                "user_type.user_type_name AS user_type, user_status.user_status_name AS status " +
                "FROM qs_user " +
                "INNER JOIN login_data ON qs_user.id = login_data.id " +
                "INNER JOIN class_ ON qs_user.class_id = class_.class_id " +
                "INNER JOIN user_type ON qs_user.user_type = user_type.user_type_id " +
                "INNER JOIN user_status ON qs_user.status = user_status.user_status_id " +
                "WHERE login=? AND password=?";

        try {
            connection = DBConnector.getConnection();
            findUser = connection.prepareStatement(statement);
            findUser.setString(1, login);
            findUser.setString(2, password);
            resultSet = findUser.executeQuery();

            resultSet.next();
            int user_id = Integer.parseInt(resultSet.getString("id"));
            String firstName = resultSet.getString("first_name");
            String lastName = resultSet.getString("last_name");
            String eMail = resultSet.getString("email");
            String classId = resultSet.getString("class");
            String userType = resultSet.getString("user_type");
            String status = resultSet.getString("status");

            if (userType.equals("mentor")) {
                // System.out.println("mentor");
                // person = new Mentor(user_id, firstName, lastName, eMail, classId, userType, status);
            } else if (userType.equals("student")) {
                // System.out.println("student");
                // person = new Student(user_id, firstName, lastName, eMail, classId, userType, status);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
                findUser.close();
                resultSet.close();
            }
            catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return person;
    }

    public void addPerson(int id, String login, String password) {

        Connection connection = null;
        PreparedStatement findUser = null;
        String statement = "INSERT INTO login_data VALUES (?, ?, ?)";

        try {
            connection = DBConnector.getConnection();
            findUser = connection.prepareStatement(statement);
            findUser.setInt(1, id);
            findUser.setString(2, login);
            findUser.setString(3, password);
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

    public void updatePerson(int id, String value, String valueType) {

        Connection connection = null;
        PreparedStatement findUser = null;
        String statement = "UPDATE login_data SET %s = ? WHERE id = ?";

        try {
            connection = DBConnector.getConnection();
            // below statement should be done only when valueType is first checked in mainController
            findUser = connection.prepareStatement(String.format(statement, valueType));
            findUser.setString(1, value);
            findUser.setInt(2, id);
            findUser.executeUpdate();

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

    public void deletePerson(int id) {

        Connection connection = null;
        PreparedStatement findUser = null;
        String statement = "DELETE FROM login_data WHERE id = ?";

        try {
            connection = DBConnector.getConnection();
            findUser = connection.prepareStatement(statement);
            findUser.setInt(1, id);
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
