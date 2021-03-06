package Models;

import Enums.BookStatus;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;

import java.util.Date;

public class Book {

    private String bookName;        
    private String bookAuthors;
    private int bookId;
    private String description;

    private BookStatus bookStatus;


    private ComboBox<String> comboBox;

    private int yearOfPub;


    public Book() {
        this(null,null ,0 ,null ,BookStatus.New ,0 );
    }

    public Book(String bookName ,
                String bookAuthors,
                int bookId,
                String description,
                BookStatus bookStatus,
                int yearOfPub){
        this.bookAuthors= bookAuthors;
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookStatus = bookStatus;
        this.description = description;
        this.yearOfPub = yearOfPub;
        comboBox = new ComboBox<>(FXCollections.observableArrayList(BookStatus.getNames()));
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthors() {
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

    public void setBookAuthors(String bookAuthors) {
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

    public int getYearOfPub() {
        return yearOfPub;
    }

    public void setYearOfPub(int yearOfPub) {
        this.yearOfPub = yearOfPub;
    }


    public ComboBox<String> getComboBox() {
        comboBox.setValue(bookStatus.getName());
        return comboBox;
    }

    public void setComboBox(ComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }
}
