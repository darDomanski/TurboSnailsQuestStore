package com.codecool.quest_store.controller;

import com.codecool.quest_store.dao.ItemDAO;
import com.codecool.quest_store.dao.QuestDAO;
import com.codecool.quest_store.model.Item;
import com.codecool.quest_store.model.Quest;
import com.codecool.quest_store.view.MainView;

import java.util.List;
import java.util.Scanner;

public class QuestController {

    private Scanner reader = new Scanner(System.in);
    private boolean flag = true;
    MainView mainView = new MainView();
    ItemDAO quests = new QuestDAO();

    public QuestController() {
        while(flag){
            showMenu();
            userDecision();
        }
    }

    public void showMenu(){
        String title = "\nMenu Quest\n";
        String menu = "GetAll," +
                "GetById," +
                "Add," +
                "Update," +
                "Delete," +
                "Exit";
        mainView.printMenu( title, menu);
    }

    public void userDecision() {
        mainView.print("Choose option : ");
        int n = reader.nextInt();
        switch (n) {
            case 1:
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
                break;

            case 2:
                Integer number =  mainView.typeInt("Enter id of quest : ");
                System.out.println("id : "+quests.getById(number).getId());
                System.out.println("access level :  : "+quests.getById(number).getAccess_level());
                System.out.println("title : "+quests.getById(number).getTitle());
                System.out.println("description : "+quests.getById(number).getDescription());
                System.out.println("value : "+quests.getById(number).getValue());
                System.out.println("type : "+quests.getById(number).getType());
                break;

            case 3:

                Item quest10 = new Quest(22,5,"aaaa","xxxxxxxx afd xcv", 500, "basic");
                quests.add(quest10);
                break;
            case 4:
                quests.update(mainView.typeInt("Enter id  of quest for update : "));
                break;
            case 5:
                quests.delete(mainView.typeInt("Enter id  of quest for delete : "));
                break;
            case 6:
                flag = false;
                break;
            default:
                mainView.print("Empty  ");
                break;
        }
    }
}
