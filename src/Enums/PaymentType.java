package Enums;

public enum PaymentType {
    CreditCard ("Кредитная карта"),
    Nall ("Наличные");


    private final String name;



    PaymentType(String s) {
        this.name = s;
    }

    public static PaymentType fromInt(int x){
        switch (x){
            case 0:
                return  CreditCard;
            case 1:
                return  Nall;
        }
        return null;
    }

    public static PaymentType fromString(String str){
        switch (str){
            case "Кредитная карта":
                return  CreditCard;
            case "Наличные":
                return  Nall;
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
