import Controllers.Database.DatabaseAccount;
import Controllers.GUI.MainController;
import Models.Librarian;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {

    public static void main(String[] args) {

      /*  DatabaseAccount databaseAccount = new DatabaseAccount("","");
        Librarian librarian = new Librarian();
        librarian.librarianName = "Inna";
        librarian.librarianPassword = "22031995";
        databaseAccount.add(librarian);*/
        Application.launch(args);
       /* Path testFilePath1 = Paths.get("C:\\Users\\virot\\OneDrive\\Documents\\GitHub\\Library\\src\\Controllers\\GUI\\ControllerLogin.java");
        Path testFilePath2 = Paths.get("C:\\Users\\virot\\OneDrive\\Documents\\GitHub\\Library\\src\\FXML\\LogIn.fxml");

        System.out.println(testFilePath1.relativize(testFilePath2));*/
    }

    @Override
    public void start(Stage stage) throws IOException {
        MainController mainController = new MainController(stage);
        mainController.createScene();

    }

}
