package Controllers.GUI;

import Controllers.Database.DatabaseBooks;
import Controllers.Manage.AccoutingSingleton;
import Models.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerBook implements ISceneCreate {
    public Button showSearch;
    public AnchorPane searchWindowBook;
    public Tab tab;
    public Stage parentStage;

    public TableView<Book> tableBook;
    public ProgressIndicator progressBar;
    public TextField nameBook;
    public TextField authorBook;
    public TextField yearOfPub;
    public ChoiceBox statusBook;

    @FXML
    public void openSearchWindowBook(ActionEvent actionEvent) {
        searchWindowBook.setVisible(!searchWindowBook.isVisible());
    }



    @Override
    public void createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Book.fxml"));
        Parent content = loader.load();
        tab.setContent(content);
        tableBook = (TableView<Book>) loader.getNamespace().get("tableBook");
        progressBar = (ProgressIndicator) loader.getNamespace().get("progressBar");
        createTable();
        setBook(new Book());

    }

    private void createTable(){
        TableColumn<Book, String> nameColumn = new TableColumn<Book, String>("Название книги");

        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(nameColumn);

        nameColumn = new TableColumn<Book, String>("Автор");

        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("bookAuthors"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(nameColumn);

        TableColumn<Book, Integer> year = new TableColumn<Book, Integer>("Автор");

        year.setCellValueFactory(new PropertyValueFactory<Book, Integer>("yearOfPub"));
        year.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.setRowFactory( tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        editBook(row.getItem());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });
        tableBook.getColumns().add(year);
    }

    private void editBook(Book book) throws IOException {
        ControllerEditBook controllerEditBook = new ControllerEditBook(parentStage,book);
        controllerEditBook.createScene();
    }


    private void setBook(Book book){

        javafx.concurrent.Service<Void> service = new javafx.concurrent.Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        ObservableList<Book> books;
                        progressBar.setVisible(true);
                        DatabaseBooks databaseBooks = new DatabaseBooks(book);
                        books = FXCollections.observableArrayList(databaseBooks.getElem());
                        tableBook.setItems(books);
                        progressBar.setVisible(false);
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    public void searchBook(ActionEvent actionEvent) {
        Book bookSearch = new Book();
        if(!nameBook.getText().trim().equalsIgnoreCase("")){
            bookSearch.setBookName('%'+nameBook.getText().trim()+'%');
        }
        if(!authorBook.getText().trim().equalsIgnoreCase("")){
            bookSearch.setBookAuthors('%'+authorBook.getText().trim()+'%');
        }
        if(!yearOfPub.getText().trim().equalsIgnoreCase("")){
            bookSearch.setYearOfPub(Integer.parseInt(yearOfPub.getText()));
        }
            setBook(bookSearch);
    }

    public void addNewBook(ActionEvent actionEvent) throws IOException {
        editBook(new Book());
    }
}
