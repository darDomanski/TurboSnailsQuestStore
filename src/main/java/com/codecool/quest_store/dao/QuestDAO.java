package com.codecool.quest_store.dao;


import com.codecool.quest_store.model.Item;
import com.codecool.quest_store.model.Quest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestDAO implements ItemDAO {
    private DBConnector connectionPool;

    public QuestDAO(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }


    @Override
    public List<Item> getAll() {
        Connection connection = null;
        List<Item> quests = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM quest ");
            resultSet = preparedStatement.executeQuery();
            createQuests(resultSet, quests);

            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return quests;
    }


    @Override
    public List<Item> getAllBasic(){
        Connection connection = null;
        List<Item> quests = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM quest WHERE quest_type='basic'  ");
            resultSet = preparedStatement.executeQuery();
            createQuests(resultSet, quests);

            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return quests;
    }


    @Override
    public List<Item> getAllExtra(){
        Connection connection = null;
        List<Item> quests = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM quest WHERE quest_type='magic'  ");
            resultSet = preparedStatement.executeQuery();
            createQuests(resultSet, quests);

            preparedStatement.close();
            resultSet.close();
            connection.close();

        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return quests;
    }



    private void createQuests(ResultSet resultSet, List<Item> quests) {
        try{
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer access_level = resultSet.getInt("access_level");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Integer value = resultSet.getInt("quest_value");
                String type = resultSet.getString("quest_type");
                Item quest = new Quest(id, access_level, title, description, value, type);
                quests.add(quest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item getById(Integer id){
        Quest quest = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM quest WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            quest = createQuest(resultSet);

            preparedStatement.close();
            resultSet.close();
            connection.close();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return quest;
    }

    private Quest createQuest(ResultSet resultSet) {
        Quest quest = null;
        try{
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer access_level = resultSet.getInt("access_level");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Integer value = resultSet.getInt("quest_value");
                String type = resultSet.getString("quest_type");
                quest = new Quest(id,access_level,title,description,value,type);
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
        return quest;
    }

    @Override
    public void add( Item item ) {
        Integer access_level = item.getAccess_level();
        String title = item.getTitle();
        String description = item.getDescription();
        Integer quest_value = item.getValue();
        String quest_type =  item.getType();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO quest ( access_level,title,description,quest_value,quest_type ) " +
                    "VALUES(?,?,?,?,?)");
            preparedStatement.setInt(1, access_level);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, description);
            preparedStatement.setInt(4, quest_value);
            preparedStatement.setString(5, quest_type);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String attribute = type("Type which attribute would you like to change(title/description/quest_type/access_level/quest_value) ; ");

        if (attribute.equals("title") || attribute.equals("description") || attribute.equals("quest_type")) {
            String newTextValue = type("Type new value ; ");

            try {
                connection = connectionPool.getConnection();
                preparedStatement = connection.prepareStatement(String.format("UPDATE quest SET %s = ? WHERE id = ? ;", attribute));
                preparedStatement.setString(1, newTextValue);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();

                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }

        if (attribute.equals("access_level") || attribute.equals("quest_value")) {
            Integer newIntValue =  typeInt("Type new value ; ");
            try {
                preparedStatement = connection.prepareStatement(String.format("UPDATE quest SET %s = ? WHERE id = ? ;", attribute));
                preparedStatement.setInt(1, newIntValue);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();

                preparedStatement.close();
                resultSet.close();
                connection.close();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
    }

    // it SHOULD be in View
    private String type(String info){
        Scanner reader = new Scanner(System.in);
        System.out.println(info);
        String n = reader.nextLine();
        return n;
    }

    private Integer typeInt(String info){
        Scanner reader = new Scanner(System.in);
        System.out.println(info);
        Integer n = reader.nextInt();
        return n;
    }

    @Override
    public void delete(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(" DELETE FROM  quest WHERE id = ? ");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            preparedStatement.close();
            connection.close();
            System.out.println( "Quest with id : "+id+" was deleted." );
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

}
