package it.unisa.diem.Controller;

import java.util.List;

import ezvcard.property.Profile;
import it.unisa.diem.Model.AddressBook;
import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Interfaces.ContactList;
import it.unisa.diem.Utility.FileManager;
import it.unisa.diem.Utility.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


/**
 * Controller class for the ProfileSelection view.
 * As a controller, it manages the interaction between the view and the model. Using a list of Profile objects from the model, it can:
 * - show the list of available profiles (up to 3)
 * - manipulate such profiles
 * - import new profiles from external sources (.vcf files)
 * - export profiles to external sources (.vcf files)
 * - open the AddressBook view for the selected profile
 */
public class ProfileSelectionController implements OnEditable {

    public static final int MAXPROFILES = 3;///< The maximum number of profiles that can be managed
    private List<Profile> profileList;///< The list of profiles to manage

    /**
     * Constructs a ProfileSelectionController with the given list of profiles.
     *
     * @param profileList the list of profiles to manage
     */
    public ProfileSelectionController(String pathToProfileList) {
        // Constructor implementation
    }

    /**
     * Exits the application.
     * Called when the user clicks on the exit button on the window from the profile selection view.
     * 
     * @post The ProfileList is loaded to internal file, then the application is closed.
     * @see #onSave()
     */
    @FXML
    public void exit() {
        // Method implementation
    }

    /**
     * Opens the profile creation popup to increment the list of profiles, up to {@link #MAXPROFILES}
     * Called when the user clicks on the profile creation button in the profile selection view.
     * If the maximum number of profiles is reached, the user is notified of the impossibility to add a new profile.
     * @post The new profile is added to the list of profiles or an error message is shown.
     * @see Profile
     */
    @FXML
    @Override
    public void onAdd(ActionEvent event) {
        // Method implementation
    }

    /**
     * Prompts the deletion of the selected profile from the list of profiles.
     * Called when the user clicks on the profile deletion button in the profile selection view.
     * The user will be asked if they are sure to delete the selected profile.
     * @pre There must be at least one profile in the list of profiles for this method to be called.
     * @post The selected profile is removed from the list of profiles.
     * @see Profile
     */
    @FXML
    @Override
    public void onDelete(ActionEvent event) {
        // Method implementation
    }

    /**
     * Opens the profile edit popup to modify the selected profile.
     * Called when the user clicks on the profile edit button in the profile selection view.
     * @pre There must be at least one profile in the list of profiles for this method to be called.
     * @post The selected profile is modified with the new values inserted by the user in the view's text fields.
     * @see it.unisa.diem.Model.Profile#setName(String)
     * @see it.unisa.diem.Model.Profile#setPhone(int)
     * @see it.unisa.diem.Model.Profile#setProfilePicture(String)
     */
    @FXML
    @Override
    public void onEdit(ActionEvent event) {
        // Method implementation
    }

    
    /**
     * Save the changes made to the list of profiles.
     * Called by {@link #onEdit(ActionEvent)} and {@link #onAdd(ActionEvent)} when the user clicks on the save button in the contact's edit view.
     *
     * @post The changes made to the list of Profile are saved.
     * @see FileManager#exportToFile(String, Object)
     */
    public void onSave() {
        // Method implementation
    }
    
     /**
     * Goes to the address book view for the selected profile.
     * Called when the user clicks on the back button in the main view.
     * 
     * @post The address book is saved to the application's assets and the user is taken back to the profile selection view.
     * @see SceneManager#loadAddressBook()
     * @see #onSave()
     */
    @FXML
    public void toAddressBook(ActionEvent event) {
        // Method implementation
    }
}