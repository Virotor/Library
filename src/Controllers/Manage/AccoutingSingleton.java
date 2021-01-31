package Controllers.Manage;

import Models.Librarian;

public class AccoutingSingleton {

    private static Librarian librarianManager;
    private static  boolean isAcc = false;

    public static Librarian getLibrarianManager() {
        return librarianManager;
    }

    public static boolean isIsAcc() {
        return isAcc;
    }


    public static void instantiate(Librarian librarianManager){
        AccoutingSingleton.librarianManager = librarianManager;
        isAcc = true;
    }

    public static void logOn(){
        librarianManager = null;
        isAcc = false;
    }


}
