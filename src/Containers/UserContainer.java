package Containers;

import Models.User;

import java.util.ArrayList;

public class UserContainer implements IContainer<User> {

    private ArrayList<User> lUsers;

    public UserContainer(){ lUsers = new ArrayList<>();}

    @Override
    public void deleteElem(User elem) {

    }

    @Override
    public void addElem(User elem) {

    }

    @Override
    public void updateElem(User oldElem, User newElem) {

    }

    @Override
    public ArrayList<User> requestToDatabase() {
        return null;
    }
}
