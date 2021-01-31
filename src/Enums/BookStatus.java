package Enums;

public enum BookStatus {
    New,
    Free,
    Issuance,
    GetBack;


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
