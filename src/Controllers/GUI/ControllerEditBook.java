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
import javafx.stage.Modality;
import javafx.stage.Stage;

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
        bookName.setText(book.getBookName());
        yearOfPub.setText(String.valueOf(book.getYearOfPub()));
        authors.setText(book.getBookAuthors());
        description.setText(book.getDescription());

        cancel = (Button) namespace.get("cancel");
        cancel.setOnAction(actionEvent -> ownedStage.close());
        apply = (Button) namespace.get("apply");
        apply.setOnAction(actionEvent -> apply());
        ownedStage.showAndWait();
    }

    public void apply() {
        if(isChanged()){
            book.setYearOfPub(Integer.parseInt(yearOfPub.getText()));
            book.setBookName(bookName.getText());
            book.setDescription(description.getText());
            book.setBookAuthors(authors.getText());
            DatabaseBooks databaseBooks = new DatabaseBooks(book);
            if(book.getBookId()==0){
                    databaseBooks.add(book);
            }else{

            }
        }
        ownedStage.close();
    }

    private boolean isChanged(){
        if(book.getBookId()==0)
            return  true;
        else return !book.getBookName().equals(bookName.getText())
                && !book.getDescription().equals(description.getText())
                && !book.getBookAuthors().equals(authors.getText())
                && book.getYearOfPub() != (Integer.parseInt(bookName.getText()));
    }

    public void cancel(ActionEvent actionEvent) {
        closeStage(actionEvent);
    }

    private void closeStage(ActionEvent actionEvent) {
        final Node source = (Node) actionEvent.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
