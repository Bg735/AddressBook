package it.unisa.diem.Controller;

import java.util.List;

import ezvcard.property.Profile;
import javafx.collections.transformation.FilteredList;
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
    private List<Profile> profileList;

    /**
     * Constructs a ProfileSelectionController with the given list of profiles.
     *
     * @param profileList the list of profiles to manage
     */
    public ProfileSelectionController(List<Profile> profileList) {
        // Constructor implementation
    }

    /**
     * Add a new profile to the list of profiles.
     * Called when the user clicks on the profile creation button in the profile selection view.
     * If the maximum number of profiles is reached, the user is notified of the impossibility to add a new profile.
     */
    @FXML
    @Override
    public void onAdd(ActionEvent event) {
        // Method implementation
    }

    @FXML
    @Override
    public void onDelete(ActionEvent event) {
        // Method implementation
    }

    @FXML
    @Override
    public void onEdit(ActionEvent event) {
        // Method implementation
    }

    @FXML
    public void onSelect(ActionEvent event) {
        // Method implementation
    }

    @FXML
    public void onImport(ActionEvent event) {
        // Method implementation
    }

    @FXML
    void onAddNImport(ActionEvent event) {
        // Method implementation
    }

    @FXML
    public void onExport(ActionEvent event) {
        // Method implementation
    }
}