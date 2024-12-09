package it.unisa.diem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The main class of the application, which loads the assets and starts the GUI.
 * @see Application
 */
public class AddressBookApplication extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
        //loadFromFile()
        //foreach(profile){cancella i vecchi dagli eliminati di recente}
        //verifica che i path esistono e li crea in caso contrario
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + "View.fxml"));
        try { 
            fxmlLoader.setController(Class.forName("it.unisa.diem."+fxml.substring(0, 1).toUpperCase() + fxml.substring(1)+"Controller").getConstructor().newInstance());
        } catch (Exception e) {
            System.exit(1);
        }
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}