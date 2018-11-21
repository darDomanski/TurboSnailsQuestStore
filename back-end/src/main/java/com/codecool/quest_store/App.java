package com.codecool.quest_store;

import com.codecool.quest_store.dao.ItemDAO;
import com.codecool.quest_store.dao.QuestDAO;
import com.codecool.quest_store.model.Item;

import java.util.List;

public class App {

    public static void main(String[] args) {

        System.out.println("hello snails");

        ItemDAO quests = new QuestDAO();
        List<Item> allquests = quests.getAll();

        for ( int i = 0; i < allquests.size(); i++){
            System.out.print(allquests.get(i).getId() +" | ");
            System.out.print(allquests.get(i).getAccess_level()+" | ");
            System.out.print(allquests.get(i).getTitle()+" | ");
            System.out.print(allquests.get(i).getDescription()+" | ");
            System.out.print(allquests.get(i).getValue()+" | ");
            System.out.print(allquests.get(i).getType()+" | ");
            System.out.println();
        }

        System.out.println(quests.getById(1).getTitle());
        System.out.println(quests.getById(2).getTitle());

    }

}
