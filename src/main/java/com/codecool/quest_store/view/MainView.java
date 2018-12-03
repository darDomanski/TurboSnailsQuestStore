package com.codecool.quest_store.view;

import java.util.Scanner;

public class MainView {

    Scanner reader = new Scanner(System.in);


    public void print(String text){
        System.out.println(text);
    }

    public String typeString(String info){
        print(info);
        String n = reader.nextLine();
        return n;
    }

    public Integer typeInt(String info){
        print(info);
        Integer number = reader.nextInt();
        return number;
    }

    public void printMenu(String title,String positions){
        print(title);
        String [] menu = positions.split(",");
        for (int i =0; i < menu.length; i++){
            print(i+1+". "+menu[i]);
        }
    }




}
