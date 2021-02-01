package Controllers.GUI;

import Controllers.Database.DatabaseUserUsage;
import Controllers.Database.DatabaseUsers;
import Models.Book;
import Models.Payment;
import Models.User;
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

import java.io.IOException;

public class ControllerEditUser implements ISceneCreate {


    public TextField userName;
    public TextField address;
    public TextField phoneNo;
    public Button apply;
    public Button cancel;
    public TableView tableService;
    public TableView tableBook;
    private Stage ownedStage;


    public ControllerUser controllerUser;
    public User user;
    public Stage parentStage;

    @Override
    public void createScene() throws IOException {
        ownedStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/EditUser.fxml"));
        Parent content = loader.load();
        Scene scene = new Scene(content);
        ownedStage.initOwner(parentStage);
        ownedStage.initModality(Modality.APPLICATION_MODAL);
        ownedStage.setResizable(false);
        ownedStage.setScene(scene);
        var namespace = loader.getNamespace();
        userName = (TextField) namespace.get("userName");
        address = (TextField) namespace.get("address");
        phoneNo = (TextField) namespace.get("phoneNo");
        tableBook = (TableView<Book>) namespace.get("tableBook");
        tableService = (TableView<Payment>) namespace.get("tableService");
        //phoneNo.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        userName.setText(user.getUserName());
        address.setText(user.getAddress());
        phoneNo.setText(String.valueOf(user.getPhoneNo()));
        cancel = (Button) namespace.get("cancel");
        cancel.setOnAction(actionEvent -> delete());
        apply = (Button) namespace.get("apply");
        apply.setOnAction(actionEvent -> apply());
        this.createTable();
        this.setService();
        this.setBook();
        ownedStage.showAndWait();
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

        TableColumn<Book, Integer> year = new TableColumn<Book, Integer>("Год издания");

        year.setCellValueFactory(new PropertyValueFactory<Book, Integer>("yearOfPub"));
        year.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(year);

        nameColumn = new TableColumn<>("Статус");

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookStatus().getName()));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(nameColumn);


        TableColumn<Payment, String> nameColumnService = new TableColumn<Payment, String>("Название услуги");
        nameColumnService.setCellValueFactory(new PropertyValueFactory<Payment, String>("serviceName"));
        nameColumnService.setSortType(TableColumn.SortType.ASCENDING);
        tableService.getColumns().add(nameColumnService);

        TableColumn<Payment, String> statusPayment = new TableColumn<>("Статус оплаты");
        statusPayment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentStatus().getName()));
        statusPayment.setSortType(TableColumn.SortType.ASCENDING);
        tableService.getColumns().add(statusPayment);

        TableColumn<Payment, String> typePayment = new TableColumn<>("Тип оплаты");
        typePayment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentType().getName()));
        typePayment.setSortType(TableColumn.SortType.ASCENDING);
        tableService.getColumns().add(typePayment);
    }


    public void setService(){
        javafx.concurrent.Service<Void> service = new javafx.concurrent.Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        ObservableList<Payment> payments;
                        //progressBar.setVisible(true);
                        DatabaseUserUsage databaseUserUsage = new DatabaseUserUsage(user);
                        payments = FXCollections.observableArrayList(databaseUserUsage.getPayments());
                        tableService.setItems(payments);
                        //progressBar.setVisible(false);
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    public void setBook(){
        javafx.concurrent.Service<Void> service = new javafx.concurrent.Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        ObservableList<Book> books;
                        //progressBar.setVisible(true);
                        DatabaseUserUsage databaseUserUsage = new DatabaseUserUsage(user);
                        books = FXCollections.observableArrayList(databaseUserUsage.getBooks());
                        tableBook.setItems(books);
                        //progressBar.setVisible(false);
                        return null;
                    }
                };
            }
        };
        service.start();
    }

    public void apply() {
        if(isChecked()){
            if(isChanged()){
                user.setUserName(userName.getText());
                user.setAddress(address.getText());
                user.setPhoneNo(phoneNo.getText());
                DatabaseUsers databaseUsers = new DatabaseUsers();
                if(user.getUserId()==0){
                    databaseUsers.add(user);
                }else{
                    databaseUsers.update(user);
                }
                controllerUser.setUser(null);
            }

            ownedStage.close();
        }
    }

    private boolean isChanged(){
        if(user.getUserId()==0)
            return  true;
        else return !(user.getUserName().equals(userName.getText())
                && user.getAddress().equals(address.getText())
                && user.getPhoneNo().equals(phoneNo.getText()));
    }

    private boolean isChecked(){
        if(userName.getText().equals("") || address.getText().equals("") || phoneNo.getText().equals("")){
            return  false;
        }
        return true;
    }

    private void delete(){
        if(user.getUserId()!=0){
            DatabaseUsers databaseUsers = new DatabaseUsers();
            databaseUsers.delete(user);
            controllerUser.setUser(null);
        }
        ownedStage.close();
    }


}
