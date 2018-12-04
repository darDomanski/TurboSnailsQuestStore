package com.codecool.quest_store.dao;


import com.codecool.quest_store.model.Artifact;
import com.codecool.quest_store.model.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArtifactDAO implements ItemDAO {
    private DBConnector connectionPool;

    public ArtifactDAO(DBConnector connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Item> getAll() {
        List<Item> artifacts = new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = this.connection.prepareStatement("SELECT * FROM artifact ");
            resultSet = preparedStatement.executeQuery();
            createArtifacts(resultSet, artifacts);

            preparedStatement.close();
            resultSet.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return artifacts;
    }

    private void createArtifacts(ResultSet resultSet, List<Item> artifacts) {
        try{
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer access_level = resultSet.getInt("access_level");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Integer artifact_price = resultSet.getInt("artifact_price");
                String artifact_type = resultSet.getString("artifact_type");
                Item artifact = new Artifact(id, access_level, title, description, artifact_price, artifact_type);
                artifacts.add(artifact);
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
    }

    @Override
    public Item getById(Integer id){
        Item artifact = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement("SELECT * FROM artifact WHERE id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            artifact = createArtifact(resultSet);

            resultSet.close();
            resultSet.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
        return artifact;
    }

    private Item createArtifact(ResultSet resultSet) {
        Artifact artifact=null;
        try{
            while (resultSet.next()) {
                Integer id = resultSet.getInt("id");
                Integer access_level = resultSet.getInt("access_level");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                Integer value = resultSet.getInt("artifact_price");
                String type = resultSet.getString("artifact_type");
                artifact = new Artifact(id,access_level,title,description,value,type);
            }
        }catch(SQLException e ){
            e.printStackTrace();
        }
        return artifact;
    }

    @Override
    public void add( Item item ) {
        Integer id = item.getId();
        Integer access_level = item.getAccess_level();
        String title = item.getTitle();
        String description = item.getDescription();
        Integer artifact_price = item.getValue();
        String artifact_type =  item.getType();

        PreparedStatement preparedstatement = null;
        try{
            preparedstatement = this.connection.prepareStatement("INSERT INTO quest ( ID,access_level,title,description,artifact_price,artifact_type ) " +
                    "VALUES(?,?,?,?,?,?)");
            preparedstatement.setInt(1, id);
            preparedstatement.setInt(2, access_level);
            preparedstatement.setString(3, title);
            preparedstatement.setString(4, description);
            preparedstatement.setInt(5, artifact_price);
            preparedstatement.setString(6, artifact_type);
            preparedstatement.executeUpdate();

            preparedstatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Integer id) {
        String attribute = type("Type which attribute would you like to change(title/description/artifact_type/access_level/artifact_price) ; ");
        PreparedStatement preparedStatementt = null;

        if (attribute.equals("title") || attribute.equals("description") || attribute.equals("artifact_type")) {
            String newTextValue = type("Type new value ; ");

            try {
                preparedStatementt = this.connection.prepareStatement(String.format("UPDATE quest SET %s = ? WHERE id = ? ;", attribute));
                preparedStatementt.setString(1, newTextValue);
                preparedStatementt.setInt(2, id);
                preparedStatementt.executeUpdate();

                preparedStatementt.close();
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
        }
        if (attribute.equals("access_level") || attribute.equals("artifact_price")) {
            Integer newIntValue =  typeInt("Type new value ; ");
            try {
                preparedStatementt = this.connection.prepareStatement(String.format("UPDATE artifact SET %s = ? WHERE id = ? ;", attribute));
                preparedStatementt.setInt(1, newIntValue);
                preparedStatementt.setInt(2, id);
                preparedStatementt.executeUpdate();

                preparedStatementt.close();
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
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = this.connection.prepareStatement(" DELETE FROM  artifact WHERE id = ? ");
            preparedStatement.setInt(1, id);
            preparedStatement.executeQuery();

            preparedStatement.close();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }
}
