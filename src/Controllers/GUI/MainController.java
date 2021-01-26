package Controllers.GUI;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController implements ISceneCreate {

    public Button showSearch;
    public AnchorPane searchWindowBook;
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
        ControllerLogIn controllerLogIn = new ControllerLogIn(stage);
        controllerLogIn.createScene();
    }



    @Override
    public void createScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/Main.fxml"));
        Parent content = loader.load();
        Scene scene = new Scene(content);

        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void openSearchWindowBook(ActionEvent actionEvent) {
       searchWindowBook.setVisible(!searchWindowBook.isVisible());
    }
}
