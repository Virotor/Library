package Controllers.GUI;

import Controllers.Database.DatabaseBooks;
import Controllers.Database.DatabaseServices;
import Controllers.Database.DatabaseUserUsage;
import Controllers.Database.DatabaseUsers;
import Enums.BookStatus;
import Enums.PaymentStatus;
import Enums.PaymentType;
import Models.*;
import com.sun.javafx.collections.ImmutableObservableList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;

public class ControllerEditUser implements ISceneCreate {


    public TextField userName;
    public TextField address;
    public TextField phoneNo;
    public Button apply;
    public Button cancel;
    public TableView tableService;
    public TableView tableBook;
    public Button addNewBook;
    public TableView tableAddBook;
    public TextField nameBook;
    public TextField authors;
    public TextField yearOfPub;
    public Button searchBook;
    public Button cancelAddNewBook;
    public Button addNewService;
    public TableView tableAddService;
    public TextField nameService;
    public ChoiceBox paymentType;
    public ChoiceBox paymentStatus;
    private Stage ownedStage = null;


    public ControllerUser controllerUser;
    public User user;
    public Stage parentStage;

    @Override
    public void createScene() throws IOException {
        if(ownedStage == null)
        ownedStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/EditUser.fxml"));
        Parent content = loader.load();
        Scene scene = new Scene(content);
        ownedStage.setScene(scene);


        var namespace = loader.getNamespace();
        userName = (TextField) namespace.get("userName");
        address = (TextField) namespace.get("address");
        phoneNo = (TextField) namespace.get("phoneNo");
        tableBook = (TableView<IssuanceBook>) namespace.get("tableBook");
        tableService = (TableView<Payment>) namespace.get("tableService");

        userName.setText(user.getUserName());
        address.setText(user.getAddress());
        phoneNo.setText(String.valueOf(user.getPhoneNo()));
        cancel = (Button) namespace.get("cancel");
        cancel.setOnAction(actionEvent -> delete());
        apply = (Button) namespace.get("apply");
        apply.setOnAction(actionEvent -> apply());
        addNewBook  = (Button) namespace.get("addNewBook");
        addNewService = (Button) namespace.get("addNewService");
        addNewBook.setOnAction(actionEvent -> {
            try {
                addNewBook();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        addNewService.setOnAction(actionEvent -> {
            try {
                addNewService();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        this.createTable();
        this.setService();
        this.setBook();
        if(ownedStage.getModality()!=Modality.APPLICATION_MODAL){
            ownedStage.initOwner(parentStage);
            ownedStage.initModality(Modality.APPLICATION_MODAL);
            ownedStage.setResizable(false);
            ownedStage.showAndWait();
        }
    }

    private void addNewService() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/AddNewService.fxml"));
        Parent content = loader.load();
        Scene scene = new Scene(content);
        ownedStage.setScene(scene);
        tableAddService = (TableView<Service>) loader.getNamespace().get("tableAddService");
        createTableForNewService();
        paymentStatus = (ChoiceBox<String>) loader.getNamespace().get("paymentStatus");
        paymentType = (ChoiceBox<String>) loader.getNamespace().get("paymentType");
        ObservableList<String> items = FXCollections.observableArrayList();
        items.addAll("Успешно", "Ошибка");
        paymentStatus.setValue("Успешно");
        paymentStatus.setItems( items);
        ObservableList<String> item1 = FXCollections.observableArrayList();
        item1.addAll("Кредитная карта", "Наличные");
        paymentType.setItems(item1);
        paymentType.setValue("Наличные");
        Button cancel = (Button) loader.getNamespace().get("cancelAddService");
        cancel.setOnAction(e-> {
            try {
                cancel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        setAddNewService(null);
    }


    private void createTableForNewService(){
        TableColumn<Service, String> nameColumn = new TableColumn<Service, String>("Название услуги");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("serviceName"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableAddService.getColumns().add(nameColumn);

        TableColumn<Service, Integer> year = new TableColumn<Service, Integer>("Стоимость");
        year.setCellValueFactory(new PropertyValueFactory<Service, Integer>("moneyBig"));
        year.setSortType(TableColumn.SortType.ASCENDING);
        tableAddService.getColumns().add(year);
        tableAddService.setRowFactory( tv -> {
            TableRow<Service> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        addNewServiceEnd(getPayment(row.getItem()));
                        cancel();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });

    }

    private Payment getPayment(Service service){
        return new Payment(user.getUserId(),service.getServiceId(),"",0
                , PaymentStatus.fromString((String) paymentStatus.getValue())
                , PaymentType.fromString((String) paymentType.getValue()));
    }

    private void addNewServiceEnd(Payment payment){
        DatabaseUserUsage databaseUserUsage = new DatabaseUserUsage(user);
        databaseUserUsage.add(payment);
    }

    private void createTable(){
        TableColumn<IssuanceBook, String> nameUser = new TableColumn<>("Название книги");
        nameUser.setCellValueFactory(new PropertyValueFactory<>("bookName"));
        nameUser.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(nameUser);
        tableBook.setEditable(true);
        TableColumn<IssuanceBook, Date> dateTake = new TableColumn<>("Дата выдачи");
        dateTake.setCellValueFactory(new PropertyValueFactory<>("dateTake"));
        dateTake.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(dateTake);

        TableColumn<IssuanceBook, Date> dateReturn = new TableColumn<>("Дата возврата");
        dateReturn.setCellValueFactory(new PropertyValueFactory<>("dateReturn"));
        dateReturn.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(dateReturn);

        TableColumn<IssuanceBook, String> statusBook = new TableColumn<>("Статус книги");
        statusBook.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookStatus().getName()));
        statusBook.setCellFactory(issuanceBookStringTableColumn -> new TextFieldTableCell<>());
        statusBook.setEditable(true);
        ObservableList<String> items = FXCollections.observableArrayList();
        items.add("Возвращена");
        items.add("Выдана");
        statusBook.setCellFactory(ComboBoxTableCell.forTableColumn(items));
        statusBook.setOnEditCommit(issuanceBookStringCellEditEvent -> {
            DatabaseUserUsage databaseUserUsage = new DatabaseUserUsage(user);
            databaseUserUsage.updateBook(issuanceBookStringCellEditEvent.getRowValue(),issuanceBookStringCellEditEvent.getNewValue());
        });
        statusBook.setSortType(TableColumn.SortType.ASCENDING);
        tableBook.getColumns().add(statusBook);

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




    private  void createTableForNewBooks(){
        TableColumn<Book, String> nameColumn = new TableColumn<Book, String>("Название книги");

        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("bookName"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableAddBook.getColumns().add(nameColumn);

        nameColumn = new TableColumn<Book, String>("Автор");

        nameColumn.setCellValueFactory(new PropertyValueFactory<Book, String>("bookAuthors"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableAddBook.getColumns().add(nameColumn);

        TableColumn<Book, Integer> year = new TableColumn<Book, Integer>("Год издания");

        year.setCellValueFactory(new PropertyValueFactory<Book, Integer>("yearOfPub"));
        year.setSortType(TableColumn.SortType.ASCENDING);
        tableAddBook.getColumns().add(year);


        tableAddBook.setRowFactory( tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    addNewBookEnd(row.getItem());
                    try {
                        this.cancel();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });

    }

    private void addNewBookEnd(Book book){
        DatabaseUserUsage databaseUserUsage = new DatabaseUserUsage(user);
        databaseUserUsage.add(book);
    }


    @FXML
    private void searchBook(ActionEvent actionEvent) {
        Book bookSearch = new Book();
        if(!nameBook.getText().trim().equalsIgnoreCase("")){
            bookSearch.setBookName('%'+nameBook.getText().trim()+'%');
        }
        if(!authors.getText().trim().equalsIgnoreCase("")){
            bookSearch.setBookAuthors('%'+authors.getText().trim()+'%');
        }
        if(!yearOfPub.getText().trim().equalsIgnoreCase("")){
            bookSearch.setYearOfPub(Integer.parseInt(yearOfPub.getText()));
        }
        bookSearch.setBookStatus(BookStatus.Free);
        setAddNewBook(bookSearch);
    }


    public void setAddNewService(String name){
        javafx.concurrent.Service<Void> service = new javafx.concurrent.Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        ObservableList<Service> services;
                        DatabaseServices databaseBooks = new DatabaseServices(name);
                        services = FXCollections.observableArrayList(databaseBooks.getElem());
                        tableAddService.setItems(services);
                        return null;
                    }
                };
            }
        };
        service.start();
    }


    public void setAddNewBook(Book book){
        javafx.concurrent.Service<Void> service = new javafx.concurrent.Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        ObservableList<Book> books;
                        DatabaseBooks databaseBooks = new DatabaseBooks(book);
                        books = FXCollections.observableArrayList(databaseBooks.getElem());
                        tableAddBook.setItems(books);
                        return null;
                    }
                };
            }
        };
        service.start();
    }


    public void addNewBook() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/AddNewBook.fxml"));
        Parent content = loader.load();
        Scene scene = new Scene(content);
        ownedStage.setScene(scene);
        tableAddBook = (TableView<Book>) loader.getNamespace().get("tableAddBook");
        createTableForNewBooks();
        Button cancel = (Button) loader.getNamespace().get("cancel");
        cancel.setOnAction(e-> {
            try {
                cancel();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        setAddNewBook(new Book());
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
                        ObservableList<IssuanceBook> books;
                        DatabaseUserUsage databaseUserUsage = new DatabaseUserUsage(user);
                        books = FXCollections.observableArrayList(databaseUserUsage.getBooks());
                        tableBook.setItems(books);
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
        ownedStage.close();
    }


    public void cancel() throws IOException {
        this.createScene();
    }

    public void searchNewService(ActionEvent actionEvent) {
        setAddNewService('%'+nameService.getText()+'%');
    }
}
