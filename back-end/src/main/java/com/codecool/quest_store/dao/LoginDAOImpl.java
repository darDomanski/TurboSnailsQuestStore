package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Person;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAOImpl implements LoginDAO {

    public static void main(String[] args) {

        LoginDAOImpl loginDAO = new LoginDAOImpl();
        loginDAO.getPersonByLoginPassword("dario", "darek123");
        loginDAO.addPerson(5, "John", "Rambo");
        loginDAO.updatePerson(5, "Jasiek", "login");
    }

    public Person getPersonByLoginPassword(String login, String password) {

        Connection connection = null;
        PreparedStatement findUser = null;
        ResultSet resultSet = null;
        Person person = null;

        try {
            connection = DBConnector.getConnection();
            findUser = connection.prepareStatement("SELECT qs_user.id, first_name, last_name, email, class_.name AS class, " +
                    "user_type.user_type_name AS user_type, user_status.user_status_name AS status " +
                    "FROM qs_user " +
                    "INNER JOIN login_data ON qs_user.id = login_data.id " +
                    "INNER JOIN class_ ON qs_user.class_id = class_.class_id " +
                    "INNER JOIN user_type ON qs_user.user_type = user_type.user_type_id " +
                    "INNER JOIN user_status ON qs_user.status = user_status.user_status_id " +
                    "WHERE login=? AND password=?");
            findUser.setString(1, login);
            findUser.setString(2, password);
            resultSet = findUser.executeQuery();

            // method creating a person
            // person = new Mentor(id, firstName, LastName, eMail, className, userType, status);

            try {
                resultSet.next();
                int id = Integer.parseInt(resultSet.getString("id"));
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String eMail = resultSet.getString("email");
                String classId = resultSet.getString("class");
                String userType = resultSet.getString("user_type");
                String status = resultSet.getString("status");

                // testing what is returned
                System.out.println(id + firstName + lastName + eMail + classId + userType + status);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (findUser != null) {

                try {
                    connection.close();
                    findUser.close();
                    resultSet.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (connection != null) {

                try {
                    connection.close();
                    findUser.close();
                    resultSet.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return person;
    }

    public void addPerson(int id, String login, String password) {

        Connection connection = null;
        PreparedStatement findUser = null;

        try {
            connection = DBConnector.getConnection();
            findUser = connection.prepareStatement("INSERT INTO login_data VALUES (?, ?, ?)");
            findUser.setInt(1, id);
            findUser.setString(2, login);
            findUser.setString(3, password);
            findUser.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (findUser != null) {

                try {
                    connection.close();
                    findUser.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (connection != null) {

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

    public void updatePerson(int id, String value, String valueType) {

        Connection connection = null;
        PreparedStatement findUser = null;

        try {
            connection = DBConnector.getConnection();
            findUser = connection.prepareStatement("UPDATE login_data SET ? = ? WHERE id = ?");
            findUser.setString(1, valueType);
            findUser.setString(2, value);
            findUser.setInt(3, id);
            findUser.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (findUser != null) {

                try {
                    connection.close();
                    findUser.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            if (connection != null) {

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

    public void deletePerson(Person person) {

    }
}
