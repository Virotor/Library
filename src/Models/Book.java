package Models;

import Enums.BookStatus;

import java.util.Date;

public class Book {

    private String bookName;
    private String[] bookAuthors;
    private int bookId;
    private String description;

    private BookStatus bookStatus;

    private Date yearOfPub;

    Book(String bookName, String[] bookAuthors, int bookId, String description, BookStatus bookStatus, Date yearOfPub){
        this.bookAuthors= bookAuthors;
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookStatus = bookStatus;
        this.description = description;
        this.yearOfPub = yearOfPub;
    }

    public String getBookName() {
        return bookName;
    }

    public String[] getBookAuthors() {
        return bookAuthors;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setBookAuthors(String[] bookAuthors) {
        this.bookAuthors = bookAuthors;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Date getYearOfPub() {
        return yearOfPub;
    }

    public void setYearOfPub(Date yearOfPub) {
        this.yearOfPub = yearOfPub;
    }
}
