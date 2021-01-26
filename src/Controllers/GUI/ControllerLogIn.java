package Controllers.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class ControllerLogIn implements  ISceneCreate {

    private Stage parentStage;
    private Stage ownedStage;

@FXML
   private Button logIn;

    public ControllerLogIn(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public ControllerLogIn(){}

    @FXML
    private void click(ActionEvent event){
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
}


    @Override
    public void createScene() throws IOException {
        ownedStage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/FXML/LogIn.fxml"));
        Parent content = loader.load();
        Scene scene = new Scene(content);

        ownedStage.initOwner(parentStage);
        ownedStage.initModality(Modality.APPLICATION_MODAL);

        ownedStage.setResizable(false);
        ownedStage.setScene(scene);
        ownedStage.showAndWait();
    }
}
