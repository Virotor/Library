package Controllers.Database;

import Enums.BookStatus;
import Models.Book;
import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseUsers implements DatabaseController<User> {

    private DatabaseConnector databaseConnector;
    private String userName;

    public DatabaseUsers() {
        databaseConnector = new DatabaseConnector();
        userName = null;
    }

    public  DatabaseUsers(String userName){
        databaseConnector = new DatabaseConnector();
        this.userName = userName;
    }

    @Override
    public ArrayList<User> getElem() {
        var connection = databaseConnector.connectToDatabase();
        ArrayList<User> userArrayList = new ArrayList<>();
        if(connection == null){
            return userArrayList;
        }
        String sql = "SELECT * From Users where userName like ? or ? is null";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,userName);
            preparedStatement.setString(2,userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var user = new User(resultSet.getString("Address"),
                        resultSet.getString("PhoneNo"), resultSet.getInt("UserId"),
                        resultSet.getString("userName"));
                userArrayList.add(user);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
        return userArrayList;


    }

    @Override
    public void add(User elem) {

    }

    @Override
    public void update(User elem) {

    }

    @Override
    public void delete(User elem) {

    }
}
