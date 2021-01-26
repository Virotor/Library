package Containers;

import java.util.ArrayList;

public interface IContainer<T> {

    public  void deleteElem(T elem);
    public void addElem(T elem);
    public void updateElem(T oldElem, T newElem);

    public ArrayList<T> requestToDatabase();
}
