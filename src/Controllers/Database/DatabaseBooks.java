package Controllers.Database;

import Enums.BookStatus;
import Models.Book;
import Models.IssuanceBook;

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
        String sql = "SELECT * From Books where (BookAuthors like ? or ? IS NULL) " +
                "and (BookName like ? or ? is NULL) " +
                "and (YearOfPub = ? or ? = 0) " +
                "and (BookStatus = ? or ? = 0)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, bookElem.getBookAuthors());
            preparedStatement.setString(2, bookElem.getBookAuthors());
            preparedStatement.setString(3, bookElem.getBookName());
            preparedStatement.setString(4, bookElem.getBookName());
            preparedStatement.setInt(5, bookElem.getYearOfPub());
            preparedStatement.setInt(6, bookElem.getYearOfPub());
            preparedStatement.setInt(7, bookElem.getBookStatus().ordinal());
            preparedStatement.setInt(8, bookElem.getBookStatus().ordinal());
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

    public ArrayList<IssuanceBook> getBookUsers(){
        var connection = databaseConnector.connectToDatabase();
        ArrayList<IssuanceBook> bookArrayList = new ArrayList<>();
        if(connection == null){
            return bookArrayList;
        }
        String sql = "SELECT BookName, userName, dateTake, dateReturn, isReturned, IssuanceId, B.BookId From BookIssuance " +
                "join Books B on BookIssuance.BookId = B.BookId " +
                "join Users U on U.UserId = BookIssuance.UserId"+
                " where (b.BookId = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, bookElem.getBookId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var book = new IssuanceBook(resultSet.getString("BookName"),
                        resultSet.getString("userName"), resultSet.getDate("dateReturn"),
                        resultSet.getDate("dateTake")
                        , BookStatus.fromInt(resultSet.getByte("isReturned"))
                        , resultSet.getInt("IssuanceId")
                        , resultSet.getInt("BookId"));
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
        var connection = databaseConnector.connectToDatabase();
        if(connection!=null){
            String sql = "UPDATE Books set  BookName = ?, BookAuthors = ?, YearOfPub = ?, Description = ? where BookId = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, bookElem.getBookName());
                preparedStatement.setString(2, bookElem.getBookAuthors());
                //preparedStatement.setInt(3, bookElem.getBookStatus().ordinal());
                preparedStatement.setInt(3, bookElem.getYearOfPub());
                preparedStatement.setString(4, bookElem.getDescription());
                preparedStatement.setInt(5,bookElem.getBookId());
                int res = preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    @Override
    public void delete(Book elem) {
        var connection = databaseConnector.connectToDatabase();
        if(connection!=null){
            String sql = "Delete  Books where BookId = ?";
            PreparedStatement preparedStatement = null;
            try {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, elem.getBookId());
                int res = preparedStatement.executeUpdate();
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

        }
    }
}
