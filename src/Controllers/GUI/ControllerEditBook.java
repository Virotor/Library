package Controllers.GUI;

import Controllers.Database.DatabaseBooks;
import Models.Book;
import Models.IssuanceBook;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.Date;

public class ControllerEditBook implements ISceneCreate {


    public TextField bookName;
    public TextField yearOfPub;
    public TextField authors;
    public TextArea description;
    public Button apply;
    public Button cancel;
    public TableView issuanceBook;
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
        issuanceBook = (TableView<IssuanceBook>) namespace.get("issuanceBook");
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
        createTable();
        this.setIssuanceBook();
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

    private void createTable(){
        TableColumn<IssuanceBook, String> nameUser = new TableColumn<>("ФИО");
        nameUser.setCellValueFactory(new PropertyValueFactory<>("userName"));
        nameUser.setSortType(TableColumn.SortType.ASCENDING);
        issuanceBook.getColumns().add(nameUser);

        TableColumn<IssuanceBook, Date> dateTake = new TableColumn<>("Дата выдачи");
        dateTake.setCellValueFactory(new PropertyValueFactory<>("dateTake"));
        dateTake.setSortType(TableColumn.SortType.ASCENDING);
        issuanceBook.getColumns().add(dateTake);

        TableColumn<IssuanceBook, Date> dateReturn = new TableColumn<>("Дата возврата");
        dateReturn.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));
        dateReturn.setSortType(TableColumn.SortType.ASCENDING);
        issuanceBook.getColumns().add(dateReturn);

        TableColumn<IssuanceBook, String> statusBook = new TableColumn<>("Статус книги");
        statusBook.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookStatus().getName()));
        statusBook.setSortType(TableColumn.SortType.ASCENDING);
        issuanceBook.getColumns().add(statusBook);

    }



    public void setIssuanceBook(){
        javafx.concurrent.Service<Void> service = new javafx.concurrent.Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        ObservableList<IssuanceBook> issuanceBooks;
                        DatabaseBooks databaseBooks = new DatabaseBooks(book);
                        issuanceBooks = FXCollections.observableArrayList(databaseBooks.getBookUsers());
                        issuanceBook.setItems(issuanceBooks);
                        return null;
                    }
                };
            }
        };
        service.start();
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
        ownedStage.close();
    }
}
