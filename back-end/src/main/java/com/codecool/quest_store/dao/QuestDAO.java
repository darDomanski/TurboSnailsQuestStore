package com.codecool.quest_store.dao;


import com.codecool.quest_store.model.Item;
import com.codecool.quest_store.model.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuestDAO implements ItemDAO {

    List<Item> quests = null;
    Quest quest = null;

//    public Connection con = null;
    public ResultSet resultSet = null;


    public QuestDAO() {

    }

    @Override
    public List<Item> getAll() {

        quests = new ArrayList<>();
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DBConnector.getConnection();
            preparedStatement = con.prepareStatement("SELECT * FROM quest ");
            resultSet = preparedStatement.executeQuery();
            createQuests(resultSet);
            resultSet.close();
            con.close();
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
        Connection con = null;
        Quest quest=null;
        PreparedStatement pst = null;
        try {
            con = DBConnector.getConnection();
            pst = con.prepareStatement("SELECT * FROM quest WHERE id = ?");
            pst.setInt(1, id);
            resultSet = pst.executeQuery();
            quest=createQuest(resultSet);
            resultSet.close();
            con.close();
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
        Connection con = null;
        PreparedStatement pst = null;
        Integer id = item.getId();
        Integer access_level = item.getAccess_level();
        String title = item.getTitle();
        String description = item.getDescription();
        Integer quest_value = item.getValue();
        String quest_type =  item.getType();

        try{
            con = DBConnector.getConnection();
            pst = con.prepareStatement("INSERT INTO quest ( access_level,title,description,quest_value,quest_type ) " +
                    "VALUES(?,?,?,?,?)");
//            pst.setInt(1, id);
            pst.setInt(1, access_level);
            pst.setString(2, title);
            pst.setString(3, description);
            pst.setInt(4, quest_value);
            pst.setString(5, quest_type);
            pst.executeUpdate();
            System.out.println("Inserted successfully");
            con.close();
        } catch(Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Integer id) {
        Connection con = null;
        PreparedStatement pst = null;
        String atribut = type("Type which atribut would you like to change(title/descritpion/quest_type/access_level/quest_value) ; ");
        if ( atribut.equals("title")  || atribut.equals("description") || atribut.equals("quest_type") ){
            String newtextValue =  type("Type new value ; ");
            try {
                con = DBConnector.getConnection();
                pst = con.prepareStatement(String.format(  "UPDATE quest SET %s = ? WHERE id = ? ;", atribut  )  );
                pst.setString(1, newtextValue);
                pst.setInt(2, id);
                pst.executeUpdate();
                resultSet.close();
                con.close();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
        if ( atribut.equals( "access_level"  )  || atribut.equals( "quest_value" )  ){
            Integer newIntValue =  typeInt("Type new value ; ");
            try {
                con = DBConnector.getConnection();
                pst = con.prepareStatement(String.format(  "UPDATE quest SET %s = ? WHERE id = ? ;", atribut  )  );
                pst.setInt(1, newIntValue);
                pst.setInt(2, id);
                pst.executeUpdate();
                resultSet.close();
                con.close();
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
        Connection con = null;
        PreparedStatement preparedStatement = null;
        try {
            con = DBConnector.getConnection();
            preparedStatement = con.prepareStatement(" DELETE FROM  quest WHERE id = ? ");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            con.close();
            System.out.println( "Quest with id : "+id+" was deleted." );
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }

}
