package it.unisa.diem.Controller;

import it.unisa.diem.Model.AddressBook;
import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Interfaces.ContactList;
import it.unisa.diem.Model.Interfaces.TaggableList;
import it.unisa.diem.Model.Interfaces.TrashCan;
import it.unisa.diem.Model.Interfaces.Filter.Filter;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

/**
 * Controller class for the AddressBook view.
 * As a controller, it manages the interaction between the view and the model. Using the AddressBook model of the selected Profile, it can:
 * - show an alphabetically ordered list of contacts
 * - manipulate such contacts
 * - filter the list based on a substring
 * - filter the list based the tags assigned to them
 * - mantain a "trash can", allowing to view and retrieve recently eliminated contacts.
 */
public class AddressBookController implements OnEditable {
    private TaggableList taggableList; ///< Reference to access the tag-related methods of the address book.
    private TrashCan trashCan; ///< Reference to access the recently-deleted-related methods of the address book.
    private ContactList contactList; ///< Reference to access the contact-list-related methods of the address book.
    private FilteredList<Contact> filteredList; ///< Data structure to store the list of contacts of the main view.
    @FXML
    private ListView<Contact> shownList; ///< Reference to the list view in the main view.


    /**
     * Constructs an AddressBookController with the given profile.
     *
     * @param pathToAddressboook the path to the internal address book file to load
     */
    public AddressBookController(String pathToAddressBook) {
        AddressBook addressBook = new AddressBook(pathToAddressBook);
        this.taggableList = addressBook;
        this.trashCan = addressBook;
        this.contactList = addressBook;
        addressBook.trashCan().removeOlderThan(30); // Example call to removeOlderThan
    }

    /**
     * Opens the edit mode for an empty contact.
     * Called when the user clicks on the contact creation button in the main view.
     *
     * @post A new contact is added to the address book using the values inserted by the user in the view's text fields.
     * @see ContactList#add(Contact)
     */
    @FXML
    public void onAdd(ActionEvent event) {
        // Method implementation
    }

    /**
     * Delete a contact from the address book.
     * Called when the user clicks on the contact deletion button in the contact's edit view.
     *
     * @pre The contact to be deleted must exist in the address book.
     * @post The contact is removed from the address book.
     * @see ContactList#delete(Contact)
     */
    @FXML
    public void onDelete(ActionEvent event) {
        // Method implementation
    }

    /**
     * Open the edit mode for a contact selected from the address book.
     * Called when the user clicks on the edit button in the contact's visualization pane.
     *
     * @pre The contact to be edited must exist in the address book.
     * @post The contact is opened in edit mode.
     * @see ContactList#get(Contact)
     */
    @FXML
    public void onEdit(ActionEvent event) {
        // Method implementation
    }

    /**
     * Save the changes made to a contact.
     * Called by {@link #onEdit(ActionEvent)} and {@link #onAdd(ActionEvent)} when the user clicks on the save button in the contact's edit view.
     *
     * @pre The contact to be saved must exist in the address book.
     * @post The changes made to the contact are saved.
     */
    @FXML
    public void onSave(ActionEvent event) {
        // Method implementation
    }

    /**
     * Opens the visualization pane for a contact selected from the address book.
     * Called when the user clicks on the contact's name in the contact's visualization pane.
     *
     * @pre The contact to be viewed must exist in the address book.
     * @post The contact's details are displayed in the visualization panel.
     * @see AddressBook#get(Contact)
     */
    @FXML
    public void onSelect(ActionEvent event) {
        // Method implementation
    }


    /**
     * Shows the list of contacts marked with the selected tag.
     * Called when the user selects a tag from the tag list in the main view.
     * 
     * @pre The tag must exist in the address book.
     */
    @FXML
    public void onSetTagFilter(ActionEvent event) {
        // Method implementation
    }

    public void onResetTagFilter(ActionEvent event) {
        // Method implementation
    }

    /**
     * Import AddressBook object from internal file.
     * Called when the user opens the addressbook view and an AddressBookController already exists.
     * In that case, this method is called instead of the constructor in order to get the selected AddressBook object.
     *
     * @pre The internal file must exist and be accessible.
     * @post The address book is imported from the internal file.
     * @invariant The address book must remain consistent.
     * @see AddressBook#importFromFile(String)
     */
    @FXML
    public void onImportBookFromFile(ActionEvent event) {
        // Method implementation
    }

    /**
     * Export AddressBook object to internal file.
     * Called when the user exits the addressbook view (or the application).
     *
     * @pre The address book must be loaded and not null.
     * @post The address book is exported to the internal file.
     * @invariant The address book must remain consistent.
     * @see AddressBook#exportToFile(String)
     */
    @FXML
    public void onExportBookToFile(ActionEvent event) {
        // Method implementation
    }

    /**
     * Import contacts from a VCard file.
     * Called when the user selects the import from VCard file option from the address book view.
     *
     * @param path the path of the VCard file from which to import the address book.
     * @pre The VCard file must exist and be accessible.
     * @post The contacts are imported from the VCard file.
     * @invariant The address book must remain consistent.
     * @see AddressBook#importFromVCard(String)
     */
    @FXML
    public void onImportFromVCardFile(String path) {
        // Method implementation
    }

    /**
     * Export contacts to a VCard file.
     * Called when the user selects the export to VCard file option from the address book view.
     *
     * @param path the path of the VCard file on which to export the address book.
     * @pre The address book must be loaded and not null.
     * @post The contacts are exported to the VCard file.
     * @invariant The address book must remain consistent.
     * @see AddressBook#exportToVCard(String)
     */
    @FXML
    public void onExportToVCardFile(String path) {
        // Method implementation
    }
}
