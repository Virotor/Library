package Controllers.Database;

import Models.Librarian;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseAccount implements  DatabaseController<Librarian> {

    private String password;

    private String userName;

    private DatabaseConnector databaseConnector;

    public ArrayList<Librarian> librarians = new ArrayList<>();



    public DatabaseAccount(String password, String userName) {
        this.password = password;
        this.userName = userName;
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public ArrayList<Librarian> getElem() {

        var connection = databaseConnector.connectToDatabase();
        if(connection == null){
            return null;
        }
        String sql = "Select * From Accounts where Password = ? and UserName = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var lib = new Librarian();
                lib.librarianPassword = resultSet.getString("Password");
                lib.librarianName = resultSet.getString("UserName");
                lib.librarianId = resultSet.getInt("UserId");
                librarians.add(lib);
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
        return null;
    }

    @Override
    public void add(Librarian elem) {
        var connection = databaseConnector.connectToDatabase();
        if(connection != null){
            String sql = "INSERT INTO Accounts (UserName, Password) VALUES (?,?)";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, elem.librarianName);
                preparedStatement.setString(2, elem.librarianPassword);
                int rows = preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            finally {
                try {
                    connection.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(Librarian elem) {

    }

    @Override
    public void delete(Librarian elem) {

    }

    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }
}
