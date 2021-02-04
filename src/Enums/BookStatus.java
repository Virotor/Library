package Enums;

import javafx.collections.ObservableList;

import java.util.ArrayList;

public enum BookStatus {
    New ("Все"),
    Free ("свободная"),
    Issuance("Выдана"),
    GetBack ("Возвращена");

    String name;

    BookStatus(String s) {
        this.name = s;
    }

    public String getName(){
        return  name;
    }
    public static BookStatus fromInt(int x){
        switch (x){
            case 0:
                return  New;
            case 1:
                return  Free;
            case 2:
                return  Issuance;
            case 3:
                return  GetBack;
        }
        return null;
    }


    public static String[] getNames(){
        return new String[] {"Выдана","Возвращена"};
    }

    public static BookStatus fromString(String value){
        switch (value){
            case "Все":
                return New;
            case "Свободна":
                return Free;
            case "Выдана":
                return  Issuance;
            case "Возвращена":
                return  GetBack;
        }
        return  null;
    }
}
