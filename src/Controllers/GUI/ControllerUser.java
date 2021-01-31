package Controllers.GUI;

import Controllers.Database.DatabaseBooks;
import Controllers.Database.DatabaseUsers;
import Models.Book;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerUser implements ISceneCreate{


    public ProgressIndicator progressBar;
    public AnchorPane searchWindowUser;
    public TextField nameUser;
    public TableView tableBook;

    public Tab tab;
    public Stage parentStage;

    public void searchUser(ActionEvent actionEvent) {
        setUser('%'+nameUser.getText().trim()+'%');
    }

    public void openSearchWindowUser(ActionEvent actionEvent) {
        searchWindowUser.setVisible(!searchWindowUser.isVisible());
    }

    private void createTable(){
        TableColumn<User, String> nameColumn = new TableColumn<User, String>("Имя пользователя");

        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(nameColumn);

        nameColumn = new TableColumn<User, String>("Адрес");

        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("address"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(nameColumn);

        nameColumn = new TableColumn<User, String>("Номер телефона");

        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("PhoneNo"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(nameColumn);
    }


    private void setUser(String userName){

        javafx.concurrent.Service<Void> service = new javafx.concurrent.Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        ObservableList<User> users;
                        progressBar.setVisible(true);
                        DatabaseUsers databaseBooks = new DatabaseUsers(userName);
                        users = FXCollections.observableArrayList(databaseBooks.getElem());
                        tableBook.setItems(users);
                        progressBar.setVisible(false);
                        return null;
                    }
                };
            }
        };
        service.start();
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
        setUser(null);
    }
}
