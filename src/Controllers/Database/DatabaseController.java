package Controllers.Database;

import java.sql.Connection;
import java.util.ArrayList;

public interface DatabaseController<T> {

    public ArrayList<T> getElem();
    public   void add(T elem);
    public   void update(T elem);
    public   void delete(T elem);

}
