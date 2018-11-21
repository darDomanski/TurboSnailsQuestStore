package com.codecool.quest_store.dao;


import com.codecool.quest_store.model.Item;
import com.codecool.quest_store.model.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestDAO implements ItemDAO {

    List<Item> quests = new ArrayList<>();
    Quest quest = null;


    public String jdbUrl = "jdbc:postgresql://localhost:5432/quest_store";
    public String user = "admin";
    public String password = "admin";

    public Connection conn = null;
    public Statement statement = null;
    public ResultSet resultSet = null;

    public void connection () {
        try {
            conn = DriverManager.getConnection(jdbUrl, user, password);
            System.out.println("connection established");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public QuestDAO() {
        connection ();
    }


    @Override
    public List<Item> getAll() {
        PreparedStatement pst = null;
        try {
            connection();
            pst = conn.prepareStatement("SELECT * FROM quest ");
            resultSet = pst.executeQuery();
            createQuests(resultSet);

            resultSet.close();
            conn.close();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return quests;
    }

    private void createQuests(ResultSet myRs)  {
        try{
            while (myRs.next()) {
                Integer id  = myRs.getInt("id");
                Integer access_level =myRs.getInt("access_level");
                String title = myRs.getString("title");
                String description = myRs.getString("description");
                Integer value = myRs.getInt("quest_value");
                String type = myRs.getString("quest_type");
                quest = new Quest(id,access_level,title,description,value,type);
                quests.add(quest);
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }


    @Override
    public Item getById(Integer id){
        PreparedStatement pst = null;
        try {
            connection();
            pst = conn.prepareStatement("SELECT * FROM quest WHERE id = ?");
            pst.setInt(1, id);
            resultSet = pst.executeQuery();
            createQuest(resultSet);
            resultSet.close();
            conn.close();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return quest;
    }


    private void createQuest(ResultSet myRs)  {
        try{
            while (myRs.next()) {
                Integer id  = myRs.getInt("id");
                Integer access_level =myRs.getInt("access_level");
                String title = myRs.getString("title");
                String description = myRs.getString("description");
                Integer value = myRs.getInt("quest_value");
                String type = myRs.getString("quest_type");
                quest = new Quest(id,access_level,title,description,value,type);
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }





    @Override
    public void add() {

    }

    @Override
    public void update() {

    }

    @Override
    public void delete() {

    }


}
