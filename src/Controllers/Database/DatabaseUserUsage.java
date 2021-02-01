package Controllers.Database;

import Enums.BookStatus;
import Enums.PaymentStatus;
import Enums.PaymentType;
import Models.Book;
import Models.Payment;
import Models.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseUserUsage{

    private DatabaseConnector databaseConnector;

    private User user;

    public DatabaseUserUsage(User user){ this.user = user; databaseConnector = new DatabaseConnector();}

    public ArrayList<Payment> getPayments() {
        var connection = databaseConnector.connectToDatabase();
        ArrayList<Payment> paymentArrayList = new ArrayList<>();
        if(connection == null){
            return paymentArrayList;
        }
        String sql = "SELECT PaymentId, PaymentStatus, PaymentType, ServiceName, ServiceId From Payments " +
                "join Services S on Payments.ServicesId = S.ServiceId" +
                " where UserId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var payment = new Payment(
                        user.getUserId(),
                        resultSet.getInt("ServiceId"),
                        resultSet.getString("ServiceName"),
                        resultSet.getInt("PaymentId"),
                        PaymentStatus.fromInt(resultSet.getInt("PaymentStatus")),
                        PaymentType.fromInt(resultSet.getInt("PaymentType")));
                paymentArrayList.add(payment);
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
        return paymentArrayList;


    }


    public ArrayList<Book> getBooks() {
        var connection = databaseConnector.connectToDatabase();
        ArrayList<Book> bookArrayList = new ArrayList<>();
        if(connection == null){
            return bookArrayList;
        }
        String sql = "SELECT B.BookId, BookName, BookAuthors, isReturned, YearOfPub  From BookIssuance " +
                "join Books B on BookIssuance.BookId = B.BookId" +
                " where UserId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var book = new Book(resultSet.getString("BookName"),
                        resultSet.getString("BookAuthors"), resultSet.getInt("BookId"),
                        null , BookStatus.fromInt(resultSet.getByte("isReturned")),
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


    public void add(Payment elem) {

    }


    public void update(Payment elem) {

    }

    public void delete(Payment elem) {

    }
}
