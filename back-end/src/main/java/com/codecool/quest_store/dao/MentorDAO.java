package com.codecool.quest_store.dao;

import com.codecool.quest_store.model.Mentor;
import com.codecool.quest_store.model.Person;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MentorDAO implements PersonDAO {

    public static void main(String[] args) {
        MentorDAO md = new MentorDAO();
        List<Person> mentors = md.getAll();
        System.out.println(mentors);
    }

    @Override
    public List<Person> getAll() {
        String sql = "SELECT qs_user.id, first_name, last_name, email, class_.name AS class_name,\n" +
                " user_type.user_type_name AS user_type, \n" +
                " user_status.user_status_name AS status\n" +
                " FROM qs_user\n" +
                "INNER JOIN login_data ON qs_user.id = login_data.id\n" +
                "INNER JOIN class_ ON qs_user.class_id = class_.class_id\n" +
                "INNER JOIN user_type ON qs_user.user_type = user_type.user_type_id\n" +
                "INNER JOIN user_status ON qs_user.status = user_status.user_status_id\n" +
                "WHERE user_type.user_type_name = 'mentor'";
        List<Person> mentors = new ArrayList<Person>();

        Connection connection = DBConnector.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if (resultSet.getString("user_type") != "mentor") {
                    continue;
                }
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

        } catch (SQLException e) {
            System.err.println("Error, cant get all objects from database!");
            e.printStackTrace();
        }

        return mentors;
    }

    @Override
    public void insert(Person person) {

    }

    @Override
    public void update(int id, String column, String newValue) {

    }

    @Override
    public void delete(int id) {

    }

}
