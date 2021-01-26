package Containers;

import Models.Service;

import java.util.ArrayList;

public class ServicesContainer  implements  IContainer<Service> {

    private ArrayList<Service> lServices;

    public ServicesContainer(){ lServices= new ArrayList<>();}

    @Override
    public void deleteElem(Service elem) {

    }

    @Override
    public void addElem(Service elem) {

    }

    @Override
    public void updateElem(Service oldElem, Service newElem) {

    }

    @Override
    public ArrayList<Service> requestToDatabase() {
        return null;
    }
}
