package Models;

import Enums.BookStatus;

import java.sql.Date;

public class IssuanceBook {

    private int bookIssuanceId;
    private int bookId;
    private String bookName;
    private String userName;
    private Date   dateReturn;
    private Date  dateTake;
    private BookStatus bookStatus;

    public IssuanceBook(String bookName, String userName, Date dateReturn, Date dateTake, BookStatus bookStatus, int bookIssuanceId, int bookId) {
        this.bookName = bookName;
        this.userName = userName;
        this.dateReturn = dateReturn;
        this.dateTake = dateTake;
        this.bookStatus = bookStatus;
        this.bookIssuanceId = bookIssuanceId;
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getDateReturn() {
        return dateReturn;
    }

    public void setDateReturn(Date dateReturn) {
        this.dateReturn = dateReturn;
    }

    public Date getDateTake() {
        return dateTake;
    }

    public void setDateTake(Date dateTake) {
        this.dateTake = dateTake;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {this.bookStatus = bookStatus; }

    public int getBookIssuanceId() {
        return bookIssuanceId;
    }

    public void setBookIssuanceId(int bookIssuanceId) {
        this.bookIssuanceId = bookIssuanceId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
}
