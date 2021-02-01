package Enums;

public enum BookStatus {
    New ("Новая"),
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
}
