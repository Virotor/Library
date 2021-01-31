package Controllers.GUI;

import Controllers.Manage.AccoutingSingleton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController implements ISceneCreate {

    public Button showSearch;
    public AnchorPane searchWindowBook;
    public Tab bookTab;
    public Tab userTab;
    private Stage stage;



    @FXML
    Text logIn;

    @FXML
    Text userName;


    public MainController(Stage stage) {
        this.stage = stage;
    }
    public MainController(){};



    @FXML
    private void logIn(MouseEvent mouseEvent) throws IOException {

        if(!AccoutingSingleton.isIsAcc()){
            ControllerLogIn controllerLogIn = new ControllerLogIn(stage);
            controllerLogIn.createScene();
            logIn.setText("Выход");
            userName.setText(AccoutingSingleton.getLibrarianManager().librarianName);
        }
        else{
            logIn.setText("Вход");
            userName.setText("Аноним");
        }
    }






    @Override
    public void createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Main.fxml"));
        Parent content = loader.load();
        Scene scene = new Scene(content);
        stage.setScene(scene);
        ControllerBook controllerBook = new ControllerBook();
        controllerBook.tab = (Tab) loader.getNamespace().get("bookTab");
        controllerBook.createScene();
        controllerBook.parentStage = stage;
        ControllerUser controllerUser = new ControllerUser();
        controllerUser.tab = (Tab) loader.getNamespace().get("userTab");
        controllerUser.createScene();
        controllerUser.parentStage = stage;
        stage.show();
    }

}
