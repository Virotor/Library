package Controllers.GUI;

import Controllers.Database.DatabaseBooks;
import Models.Book;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.converter.IntegerStringConverter;

import javax.xml.transform.Source;
import java.io.IOException;

public class ControllerEditBook implements ISceneCreate {


    public TextField bookName;
    public TextField yearOfPub;
    public TextField authors;
    public TextArea description;
    public Button apply;
    public Button cancel;
    private  Stage parentStage;
    private    Book book;
    public ControllerBook controllerBook;
    Stage ownedStage;

    public ControllerEditBook(){}
    public ControllerEditBook(Stage parentStage, Book book){
        this.book = book;
        this.parentStage = parentStage;
    }


    @Override
    public void createScene() throws IOException {
        ownedStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/EditBook.fxml"));
        Parent content = loader.load();
        Scene scene = new Scene(content);
        ownedStage.initOwner(parentStage);
        ownedStage.initModality(Modality.APPLICATION_MODAL);
        ownedStage.setResizable(false);
        ownedStage.setScene(scene);
        var namespace = loader.getNamespace();
        bookName = (TextField) namespace.get("bookName");
        yearOfPub = (TextField) namespace.get("yearOfPub");
        authors = (TextField) namespace.get("authors");
        description = (TextArea) namespace.get("description");
        yearOfPub.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        bookName.setText(book.getBookName());
        yearOfPub.setText(String.valueOf(book.getYearOfPub()));
        authors.setText(book.getBookAuthors());
        description.setText(book.getDescription());
        cancel = (Button) namespace.get("cancel");
        cancel.setOnAction(actionEvent -> delete());
        apply = (Button) namespace.get("apply");
        apply.setOnAction(actionEvent -> apply());
        ownedStage.setResizable(false);
        ownedStage.showAndWait();
    }

    public void apply() {
        if(isChecked()){
            if(isChanged()){
                book.setYearOfPub(Integer.parseInt(yearOfPub.getText()));
                book.setBookName(bookName.getText());
                book.setDescription(description.getText());
                book.setBookAuthors(authors.getText());
                DatabaseBooks databaseBooks = new DatabaseBooks(book);
                if(book.getBookId()==0){
                    databaseBooks.add(book);
                }else{
                    databaseBooks.update(book);
                }
                controllerBook.setBook(new Book());
            }

            ownedStage.close();
        }
    }

    private boolean isChanged(){
        if(book.getBookId()==0)
            return  true;
        else return !(book.getBookName().equals(bookName.getText())
                && book.getDescription().equals(description.getText())
                && book.getBookAuthors().equals(authors.getText())
                && book.getYearOfPub() == (Integer.parseInt(yearOfPub.getText())));
    }

    private boolean isChecked(){
        if(bookName.getText().equals("") || description.getText().equals("") || authors.getText().equals("") || yearOfPub.getText().equals("")){
            return  false;
        }
        return true;
    }

    private void delete(){
        if(book.getBookId()!=0){
            DatabaseBooks databaseBooks = new DatabaseBooks(new Book());
            databaseBooks.delete(book);
            controllerBook.setBook(new Book());
        }
        ownedStage.close();
    }
}
