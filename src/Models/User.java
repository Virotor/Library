package Models;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class User {
    private String address;
    private String PhoneNo;
    private int UserId;
    private String userName;

    public User(String address, String phoneNo, int userId, String userName) {
        this.address = address;
        PhoneNo = phoneNo;
        UserId = userId;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public ArrayList<Book> getBooks(){
        return new ArrayList<>();
    }

    public ArrayList<Service> getService(){
        return new ArrayList<>();
    }


}
