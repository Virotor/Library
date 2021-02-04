package Controllers.Database;

import Enums.BookStatus;
import Enums.PaymentStatus;
import Enums.PaymentType;
import Models.*;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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


    public ArrayList<IssuanceBook> getBooks() {
        var connection = databaseConnector.connectToDatabase();
        ArrayList<IssuanceBook> bookArrayList = new ArrayList<>();
        if(connection == null){
            return bookArrayList;
        }
        String sql = "SELECT B.BookId, BookName, isReturned, dateReturn,dateTake, IssuanceId, B.BookId  From BookIssuance " +
                "join Books B on BookIssuance.BookId = B.BookId" +
                " where UserId = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                var book = new IssuanceBook(resultSet.getString("BookName"),
                        user.getUserName(), resultSet.getDate("dateReturn"),
                        resultSet.getDate("dateTake")
                        , BookStatus.fromInt(resultSet.getByte("isReturned"))
                        , resultSet.getInt("IssuanceId")
                        ,resultSet.getInt("BookId"));
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


    public void add(Book book) {

        var connection = databaseConnector.connectToDatabase();
        if(connection == null)
            return;
        String sql =  "INSERT  into BookIssuance(userid, bookid, isreturned, datetake, datereturn) values(?,?,2,?,null) ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, user.getUserId());
            preparedStatement.setInt(2, book.getBookId());
            preparedStatement.setDate(3, Date.valueOf(LocalDateTime.now().toLocalDate()));
            int result = preparedStatement.executeUpdate();
            sql = "UPDATE  Books set BookStatus = 2 where BookId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getBookId());
            result = preparedStatement.executeUpdate();
            System.out.println(result);
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
    }

    public void add(Payment payment) {

        var connection = databaseConnector.connectToDatabase();
        if(connection == null)
            return;
        String sql =  "INSERT  into Payments(paymentstatus, paymenttype, userid, servicesid) values(?,?,?,?) ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, payment.getPaymentStatus().ordinal());
            preparedStatement.setInt(2, payment.getPaymentType().ordinal());
            preparedStatement.setInt(3, user.getUserId());
            preparedStatement.setInt(4, payment.getServiceId());
            int result = preparedStatement.executeUpdate();
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
    }


    public  void updateBook(IssuanceBook book, String newValue){

        book.setBookStatus(BookStatus.fromString(newValue));
        var connection = databaseConnector.connectToDatabase();
        if(connection == null)
            return;
        String sql =  "UPDATE BookIssuance set isReturned = ?," +
                " dateReturn = IIF(? = 3, ?, dateReturn) " +
                " where IssuanceId = ? ";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getBookStatus().ordinal());
            preparedStatement.setInt(2, book.getBookStatus().ordinal());
            preparedStatement.setDate(3, Date.valueOf(LocalDateTime.now().toLocalDate()));
            preparedStatement.setInt(4, book.getBookIssuanceId());
            int result = preparedStatement.executeUpdate();
            sql = "UPDATE  Books set BookStatus = IIF(?=3,1,2) where BookId = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, book.getBookStatus().ordinal());
            preparedStatement.setInt(2, book.getBookId());
            result = preparedStatement.executeUpdate();
            System.out.println(result);
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
    }

    public void delete(Payment elem) {

    }
}
