package it.unisa.diem.Utility;

import it.unisa.diem.Controller.AddressBookController;
import it.unisa.diem.Controller.ProfileSelectionController;

/**
 * Utility class to manage the scene of the application.
 * It is used to load the JavaFX UIs.
 */
public class SceneManager {

    ProfileSelectionController profileSelectionController=null; /**< The controller of the ProfileSelection view */
    AddressBookController addressBookController=null; /**< The controller of the AddressBook view */

    /**
     * Loads the view specified by the given FXML file.
     * @param fxml
     */
    private static void loadScene(String fxml) {
        // TODO: Implement this method
    }

    /**
     * Loads the ProfileSelection view.
     * If the related controller has already been instantiated for the current session, it is reused.
     */
    public static void loadProfileSelection() {
        // TODO: Implement this method
    }

    /**
     * Loads the AddressBook view.
     * If the related controller has already been instantiated for the current session, it is reused.
     */
    public static void loadAddressBook() {
        // TODO: Implement this method
    }
}