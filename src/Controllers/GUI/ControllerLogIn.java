package Controllers.GUI;

import Controllers.Database.DatabaseAccount;
import Controllers.Database.DatabaseBooks;
import Controllers.Manage.AccoutingSingleton;
import Models.Book;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class ControllerLogIn implements  ISceneCreate {

    public PasswordField password;
    public TextField login;
    public Text error;


    private Stage parentStage;

    private Stage ownedStage;

@FXML
   private Button logIn;

    public ControllerLogIn(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public ControllerLogIn(){}

    @FXML
    private void click(ActionEvent event) throws InterruptedException {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        error.setVisible(false);
        DatabaseAccount databaseAccount = new DatabaseAccount(password.getText(), login.getText());
        Runnable r = databaseAccount::getElem;
        Thread myThread = new Thread(r);
        myThread.start();
        myThread.join();
        if (databaseAccount.librarians.isEmpty()) {
            error.setVisible(true);
        } else {
            AccoutingSingleton.instantiate(databaseAccount.librarians.get(0));

            stage.close();
        }


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
