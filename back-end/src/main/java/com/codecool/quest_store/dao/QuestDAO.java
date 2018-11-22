package com.codecool.quest_store.dao;


import com.codecool.quest_store.model.Item;
import com.codecool.quest_store.model.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        Quest quest=null;
        PreparedStatement pst = null;
        try {
            connection();
            pst = conn.prepareStatement("SELECT * FROM quest WHERE id = ?");
            pst.setInt(1, id);
            resultSet = pst.executeQuery();
            quest=createQuest(resultSet);
            resultSet.close();
            conn.close();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return quest;
    }

    private Quest createQuest(ResultSet myRs)  {
        Quest quest=null;
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
        return quest;
    }

    @Override
    public void add( Item item ) {
        PreparedStatement pst = null;
        Integer id = item.getId();
        Integer access_level = item.getAccess_level();
        String title = item.getTitle();
        String description = item.getDescription();
        Integer quest_value = item.getValue();
        String quest_type =  item.getType();

        try{
            connection();
            pst = conn.prepareStatement("INSERT INTO quest ( ID,access_level,title,description,quest_value,quest_type ) " +
                    "VALUES(?,?,?,?,?,?)");
            pst.setInt(1, id);
            pst.setInt(2, access_level);
            pst.setString(3, title);
            pst.setString(4, description);
            pst.setInt(5, quest_value);
            pst.setString(6, quest_type);
            pst.executeUpdate();
            System.out.println("Inserted successfully");

            resultSet.close();
            conn.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Integer id) {
        PreparedStatement pst = null;
        String atribut = type("Type which atribut would you like to change(title/descritpion/quest_type/access_level/quest_value) ; ");
        if ( atribut.equals("title")  || atribut.equals("description") || atribut.equals("quest_type") ){
            String newtextValue =  type("Type new value ; ");
            try {
                connection();
                pst = conn.prepareStatement(String.format(  "UPDATE quest SET %s = ? WHERE id = ? ;", atribut  )  );
                pst.setString(1, newtextValue);
                pst.setInt(2, id);
                pst.executeUpdate();
                resultSet.close();
                conn.close();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        if ( atribut.equals( "access_level"  )  || atribut.equals( "quest_value" )  ){
            Integer newIntValue =  typeInt("Type new value ; ");
            try {
                connection();
                pst = conn.prepareStatement(String.format(  "UPDATE quest SET %s = ? WHERE id = ? ;", atribut  )  );
                pst.setInt(1, newIntValue);
                pst.setInt(2, id);
                pst.executeUpdate();
                resultSet.close();
                conn.close();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    // it can be in View
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
        PreparedStatement preparedStatement = null;
        try {
            connection();
            preparedStatement = conn.prepareStatement(" DELETE FROM  quest WHERE id = ? ");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            resultSet.close();
            conn.close();
            System.out.println( "Quest with id : "+id+" was deleted." );
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }
}
