package com.codecool.quest_store.dao;


import com.codecool.quest_store.model.Artifact;
import com.codecool.quest_store.model.Item;
import com.codecool.quest_store.model.Quest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArtifactDAO implements ItemDAO {

    List<Item> artifacts = new ArrayList<>();
    Artifact artifact = null;

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


    public ArtifactDAO() {
        connection ();
    }

    @Override
    public List<Item> getAll() {
        PreparedStatement pst = null;
        try {
            connection();
            pst = conn.prepareStatement("SELECT * FROM artifact ");
            resultSet = pst.executeQuery();
            createArtifacts(resultSet);
            resultSet.close();
            conn.close();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return artifacts;
    }

    private void createArtifacts(ResultSet myRs)  {
        try{
            while (myRs.next()) {
                Integer id  = myRs.getInt("id");
                Integer access_level =myRs.getInt("access_level");
                String title = myRs.getString("title");
                String description = myRs.getString("description");
                Integer artifact_price = myRs.getInt("artifact_price");
                String artifact_type = myRs.getString("artifact_type");
                artifact = new Artifact(id,access_level,title,description,artifact_price,artifact_type);
                artifacts.add(artifact);
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }

    @Override
    public Item getById(Integer id){
        Artifact artifact=null;
        PreparedStatement pst = null;
        try {
            connection();
            pst = conn.prepareStatement("SELECT * FROM artifact WHERE id = ?");
            pst.setInt(1, id);
            resultSet = pst.executeQuery();
            artifact= (Artifact) createArtifact(resultSet);
            resultSet.close();
            conn.close();
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
        return artifact;
    }

    private Item createArtifact(ResultSet myRs)  {
        Artifact artifact=null;
        try{
            while (myRs.next()) {
                Integer id  = myRs.getInt("id");
                Integer access_level =myRs.getInt("access_level");
                String title = myRs.getString("title");
                String description = myRs.getString("description");
                Integer value = myRs.getInt("artifact_price");
                String type = myRs.getString("artifact_type");
                artifact = new Artifact(id,access_level,title,description,value,type);
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
        return artifact;
    }

    @Override
    public void add( Item item ) {
        PreparedStatement pst = null;
        Integer id = item.getId();
        Integer access_level = item.getAccess_level();
        String title = item.getTitle();
        String description = item.getDescription();
        Integer artifact_price = item.getValue();
        String artifact_type =  item.getType();

        try{
            connection();
            pst = conn.prepareStatement("INSERT INTO quest ( ID,access_level,title,description,artifact_price,artifact_type ) " +
                    "VALUES(?,?,?,?,?,?)");
            pst.setInt(1, id);
            pst.setInt(2, access_level);
            pst.setString(3, title);
            pst.setString(4, description);
            pst.setInt(5, artifact_price);
            pst.setString(6, artifact_type);
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
        String atribut = type("Type which atribut would you like to change(title/description/artifact_type/access_level/artifact_price) ; ");
        if ( atribut.equals("title")  || atribut.equals("description") || atribut.equals("artifact_type") ){
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
        if ( atribut.equals( "access_level"  )  || atribut.equals( "artifact_price" )  ){
            Integer newIntValue =  typeInt("Type new value ; ");
            try {
                connection();
                pst = conn.prepareStatement(String.format(  "UPDATE artifact SET %s = ? WHERE id = ? ;", atribut  )  );
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
            preparedStatement = conn.prepareStatement(" DELETE FROM  artifact WHERE id = ? ");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();
            resultSet.close();
            conn.close();
            System.out.println( "Artifact with id : "+id+" was deleted." );
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }



}
