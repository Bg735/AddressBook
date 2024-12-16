package it.unisa.diem.Utility;

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

    private static Stage stage;
    private static Scene scene;
    private static String pathToAddressBook;
    private static Runnable psrun;
    private static Runnable abrun;


    /**
     * Loads the view specified by the given FXML file.
     * @param[in] fxml
     */
    private static void loadScene(String fxml, Stage stage, String pathToAddressBook) throws IOException {
        SceneManager.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(AddressBookApplication.class.getResource(fxml + "View.fxml"));
        try { 
            if(pathToAddressBook==null){
                fxmlLoader.setController(Class.forName("it.unisa.diem.Controller."+fxml.substring(0, 1).toUpperCase() + fxml.substring(1)+"Controller").getConstructor(String.class).newInstance(pathToAddressBook));    
            }
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
        loadScene("ProfileSelection", stage,null);   
    }

    /**
     * Loads the AddressBook view.
     * If the related controller has already been instantiated for the current session, it is reused.
     */
    public static void loadAddressBook(Stage stage, String pathToAddressBook) throws IOException {
        loadScene("AddressBook", stage, pathToAddressBook);
    }

    /**
     * Used by the ProfileSelectionController to specify the address book to load.
     */
    public static void setAddressBook(String path){
        pathToAddressBook=path;
    }

    /**
     * Used by the AddressBookController to get the address book to load.
     */
    public static String getAddressBook(){
        return pathToAddressBook;
    }

    public static void setPSRunnable(Runnable run){
        SceneManager.psrun=run;
    }

    public static Runnable getPSRunnable(){
        return psrun;
    }

    public static void setABRunnable(Runnable run){
        SceneManager.abrun=run;
    }

    public static Runnable getABRunnable(){
        return abrun;
    }
}