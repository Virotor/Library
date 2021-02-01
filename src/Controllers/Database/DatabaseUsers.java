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
        var connection = databaseConnector.connectToDatabase();
        if(connection!=null){
            String sql = "INSERT INTO  Users(userName, Address, PhoneNo) Values (?,?,?)";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, elem.getUserName());
                preparedStatement.setString(2, elem.getAddress());
                preparedStatement.setString(3, elem.getPhoneNo());
                int res = preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    @Override
    public void update(User elem) {
        var connection = databaseConnector.connectToDatabase();
        if(connection!=null){
            String sql = "UPDATE  Users set userName = ?, Address = ?, PhoneNo = ? where  UserId = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, elem.getUserName());
                preparedStatement.setString(2, elem.getAddress());
                preparedStatement.setString(3, elem.getPhoneNo());
                preparedStatement.setInt(3, elem.getUserId());
                int res = preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    @Override
    public void delete(User elem) {
        var connection = databaseConnector.connectToDatabase();
        if(connection!=null){
            String sql = "DELETE  USers where UserId = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, elem.getUserId());
                int res = preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }
}
