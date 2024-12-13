package it.unisa.diem.Utility;

import java.io.IOError;
import java.io.IOException;

import it.unisa.diem.AddressBookApplication;
import it.unisa.diem.Controller.AddressBookController;
import it.unisa.diem.Controller.ProfileSelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Utility class to manage the scene of the application.
 * It is used to load the JavaFX UIs.
 */
public class SceneManager {

    private static ProfileSelectionController profileSelectionController=null; /**< The controller of the ProfileSelection view */
    private static AddressBookController addressBookController=null; /**< The controller of the AddressBook view */
    private static Stage stage;
    private static Scene scene;

    /**
     * Loads the view specified by the given FXML file.
     * @param fxml
     */
    private static void loadScene(String fxml, Stage stage) throws IOException {
        SceneManager.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(AddressBookApplication.class.getResource(fxml + "View.fxml"));
        try { 
            fxmlLoader.setController(Class.forName("it.unisa.diem.Controller."+fxml.substring(0, 1).toUpperCase() + fxml.substring(1)+"Controller").getConstructor().newInstance());
        } catch (Exception e) {
            System.exit(1);
        }
        
        SceneManager.scene = new Scene(fxmlLoader.load());
        SceneManager.stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the ProfileSelection view.
     * If the related controller has already been instantiated for the current session, it is reused.
     */
    public static void loadProfileSelection(Stage stage) throws IOException {
        loadScene("ProfileSelection", stage);   
    }

    /**
     * Loads the AddressBook view.
     * If the related controller has already been instantiated for the current session, it is reused.
     */
    public static void loadAddressBook(Stage stage) throws IOException {
        loadScene("AddressBook", stage);
    }
}