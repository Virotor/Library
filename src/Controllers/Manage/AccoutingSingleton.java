package Controllers.Manage;

import Models.Librarian;
import javafx.scene.control.Tab;

public class AccoutingSingleton {

    private static Librarian librarianManager;
    private static  boolean isAcc = false;
    private static Tab tab;

    public static Librarian getLibrarianManager() {
        return librarianManager;
    }

    public static boolean isIsAcc() {
        return isAcc;
    }


    public static void instantiate(Librarian librarianManager){
        AccoutingSingleton.librarianManager = librarianManager;
        isAcc = true;
        tab.setDisable(false);
    }

    public static void logOn(){
        librarianManager = null;
        isAcc = false;
        tab.setDisable(true);
    }


    public static void setTab(Tab tab) {
        tab.setDisable(true);
        AccoutingSingleton.tab = tab;
    }
}
