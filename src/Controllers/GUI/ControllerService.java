package Controllers.GUI;

import Controllers.Database.DatabaseBooks;
import Controllers.Database.DatabaseServices;
import Models.Book;
import Models.Service;
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
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;

public class ControllerService implements ISceneCreate {


    public Stage parentStage;
    public Tab tab;
    public TableView tableService;
    public ProgressIndicator progressBar;
    public Button showSearch;
    public AnchorPane searchWindowService;
    public TextField serviceName;


    @Override
    public void createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Service.fxml"));
        Parent content = loader.load();
        tab.setContent(content);
        tableService = (TableView<Service>) loader.getNamespace().get("tableService");
        progressBar = (ProgressIndicator) loader.getNamespace().get("progressBar");
        createTable();
        setService(null);
    }

    private void createTable(){
        TableColumn<Service, String> nameColumn = new TableColumn<Service, String>("Название услуги");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("serviceName"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableService.getColumns().add(nameColumn);

        nameColumn = new TableColumn<Service, String>("Описание");
        nameColumn.setCellValueFactory(new PropertyValueFactory<Service, String>("description"));
        nameColumn.setSortType(TableColumn.SortType.ASCENDING);
        tableService.getColumns().add(nameColumn);

        TableColumn<Service, Integer> year = new TableColumn<Service, Integer>("Стоимость");
        year.setCellValueFactory(new PropertyValueFactory<Service, Integer>("moneyBig"));
        year.setSortType(TableColumn.SortType.ASCENDING);
        tableService.getColumns().add(year);
        tableService.setRowFactory( tv -> {
            TableRow<Service> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    try {
                        editService(row.getItem());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row ;
        });

    }


    private void editService(Service service) throws IOException {
            ControllerEditService controllerEditService = new ControllerEditService();
            controllerEditService.parentStage = parentStage;
            controllerEditService.controllerService = this;
            controllerEditService.service = service;
            controllerEditService.createScene();
    }


    public void setService(String services){

        javafx.concurrent.Service<Void> service = new javafx.concurrent.Service<>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<>() {
                    @Override
                    protected Void call() {
                        ObservableList<Service> serviceObservableList;
                        progressBar.setVisible(true);
                        DatabaseServices databaseBooks = new DatabaseServices(services);
                        serviceObservableList = FXCollections.observableArrayList(databaseBooks.getElem());
                        tableService.setItems(serviceObservableList);
                        progressBar.setVisible(false);
                        return null;
                    }
                };
            }
        };
        service.start();
    }
    public void searchService(ActionEvent actionEvent) {
        setService('%'+serviceName.getText()+'%');
    }

    public void addNewService(ActionEvent actionEvent) throws IOException {
        editService(new Service());
    }

    public void openServiceSearchWindow(ActionEvent actionEvent) {
        searchWindowService.setVisible(!searchWindowService.isVisible());
    }
}
