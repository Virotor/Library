package Controllers.Database;

import Enums.BookStatus;
import Models.Book;
import Models.Librarian;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseBooks implements DatabaseController<Book> {


    private Book bookElem;


    private DatabaseConnector databaseConnector;

    public DatabaseBooks(Book elem){
        bookElem = elem;
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public ArrayList<Book> getElem(){
        var connection = databaseConnector.connectToDatabase();
        ArrayList<Book> bookArrayList = new ArrayList<>();
        if(connection == null){
            return bookArrayList;
        }
        String sql = "SELECT * From Books where (BookAuthors like ? or ? IS NULL) and (BookName like ? or ? is NULL) and (YearOfPub = ? or ? = 0)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookElem.getBookAuthors());
            preparedStatement.setString(2, bookElem.getBookAuthors());
            preparedStatement.setString(3, bookElem.getBookName());
            preparedStatement.setString(4, bookElem.getBookName());
            preparedStatement.setInt(5, bookElem.getYearOfPub());
            preparedStatement.setInt(6, bookElem.getYearOfPub());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var book = new Book(resultSet.getString("BookName"),
                        resultSet.getString("BookAuthors"), resultSet.getInt("BookId"),
                        resultSet.getString("Description") , BookStatus.fromInt(resultSet.getByte("BookStatus")),
                        resultSet.getInt("YearOfPub"));
                bookArrayList.add(book);
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
        return bookArrayList;


    }



    @Override
    public void add(Book elem) {
        var connection = databaseConnector.connectToDatabase();
        if(connection!=null){
            String sql = "INSERT INTO  Books(BookName, BookAuthors, BookStatus, YearOfPub, Description) " +
                    "values (?,?,?,?,?)";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bookElem.getBookName());
                preparedStatement.setString(2, bookElem.getBookAuthors());
                preparedStatement.setInt(3, bookElem.getBookStatus().ordinal());
                preparedStatement.setInt(4, bookElem.getYearOfPub());
                preparedStatement.setString(5, bookElem.getDescription());
                int res = preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    @Override
    public void update(Book elem) {

    }

    @Override
    public void delete(Book elem) {

    }
}
