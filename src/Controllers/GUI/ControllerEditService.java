package Controllers.GUI;

import Controllers.Database.DatabaseBooks;
import Controllers.Database.DatabaseServices;
import Models.Book;
import Models.Service;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;

public class ControllerEditService implements  ISceneCreate{


    public Button apply;
    public Button cancel;
    public TextArea description;
    public TextField serviceName;
    public TextField coast;
    private Stage ownedStage;
    public Stage parentStage;

    public Service service;
    public ControllerService controllerService;

    @Override
    public void createScene() throws IOException {
        ownedStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/EditService.fxml"));
        Parent content = loader.load();
        Scene scene = new Scene(content);
        ownedStage.initOwner(parentStage);
        ownedStage.initModality(Modality.APPLICATION_MODAL);
        ownedStage.setResizable(false);
        ownedStage.setScene(scene);
        var namespace = loader.getNamespace();
        serviceName = (TextField) namespace.get("serviceName");
        coast = (TextField) namespace.get("coast");
        description = (TextArea) namespace.get("description");
        coast.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
        serviceName.setText(service.getServiceName());
        coast.setText(String.valueOf(service.getMoneyBig()));
        description.setText(service.getDescription());
        cancel = (Button) namespace.get("cancel");
        cancel.setOnAction(actionEvent -> delete());
        apply = (Button) namespace.get("apply");
        apply.setOnAction(actionEvent -> apply());
        ownedStage.showAndWait();
    }



    public void apply() {
        if(isChecked()){
            if(isChanged()){
                service.setMoneyBig(Integer.parseInt(coast.getText()));
                service.setServiceName(serviceName.getText());
                service.setDescription(description.getText());
                DatabaseServices databaseServices = new DatabaseServices();
                if(service.getServiceId()==0){
                    databaseServices.add(service);
                }else{
                    databaseServices.update(service);
                }
                controllerService.setService(null);
            }

            ownedStage.close();
        }
    }

    private boolean isChanged(){
        if(service.getServiceId()==0)
            return  true;
        else return !(service.getServiceName().equals(serviceName.getText())
                && service.getDescription().equals(description.getText())
                && service.getMoneyBig() == (Integer.parseInt(coast.getText())));
    }

    private boolean isChecked(){
        if(serviceName.getText().equals("") || description.getText().equals("") || coast.getText().equals("")){
            return  false;
        }
        return true;
    }

    private void delete(){
        if(service.getServiceId()!=0){
            DatabaseServices databaseServices = new DatabaseServices();
            databaseServices.delete(service);
            controllerService.setService(null);
        }
        ownedStage.close();
    }
}
