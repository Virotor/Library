package Controllers.Database;

import Enums.BookStatus;
import Models.Book;
import Models.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseServices  implements   DatabaseController<Service> {

    private DatabaseConnector databaseConnector;
    private String serName;

    public DatabaseServices(String serName){this.serName= serName;databaseConnector = new DatabaseConnector();}

    public DatabaseServices(){databaseConnector = new DatabaseConnector();}


    @Override
    public ArrayList<Service> getElem() {
        var connection = databaseConnector.connectToDatabase();
        ArrayList<Service> serviceArrayList = new ArrayList<>();
        if(connection == null){
            return serviceArrayList;
        }
        String sql = "SELECT * From Services where ServiceName like ? or ? is NULL";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, serName);
            preparedStatement.setString(2,serName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var service = new Service(resultSet.getString("description"),
                        resultSet.getInt("ServiceId"),
                        resultSet.getString("ServiceName") , resultSet.getInt("Coast"));
                serviceArrayList.add(service);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return serviceArrayList;

    }

    @Override
    public void add(Service elem) {
        var connection = databaseConnector.connectToDatabase();
        if(connection!=null){
            String sql = "INSERT INTO  Services(DESCRIPTION, SERVICENAME, COAST) VALUES (?,?,?)";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, elem.getDescription());
                preparedStatement.setString(2, elem.getServiceName());
                preparedStatement.setInt(3, elem.getMoneyBig());
                int res = preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    @Override
    public void update(Service elem) {
        var connection = databaseConnector.connectToDatabase();
        if(connection!=null){
            String sql = "UPDATE Services set  ServiceName = ?, description = ?, Coast = ? where ServiceId = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, elem.getServiceName());
                preparedStatement.setString(2, elem.getDescription());
                preparedStatement.setInt(3, elem.getMoneyBig());
                preparedStatement.setInt(4, elem.getServiceId());
                int res = preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    @Override
    public void delete(Service elem) {
        var connection = databaseConnector.connectToDatabase();
        if(connection!=null){
            String sql = "Delete  Services where ServiceId = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, elem.getServiceId());
                int res = preparedStatement.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
