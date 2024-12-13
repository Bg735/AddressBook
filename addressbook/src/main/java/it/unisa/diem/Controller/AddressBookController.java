package it.unisa.diem.Controller;

import it.unisa.diem.Model.AddressBook;
import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Interfaces.ContactList;
import it.unisa.diem.Model.Interfaces.TaggableList;
import it.unisa.diem.Model.Interfaces.TrashCan;
import it.unisa.diem.Model.Tag;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

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
    public static final int DAYS = 30; /**< Number of days a contact remains in the recently-deleted-list */
    
    private TaggableList taggableList; /**< Reference to access the tag-related methods of the address book. */
    private TrashCan trashCan; /**< Reference to access the recently-deleted-related methods of the address book. */
    private ContactList contactList; /**< Reference to access the contact-list-related methods of the address book. */
    private FilteredList<Contact> filteredList; /**< Data structure to store the list of contacts of the main view. */
   
    
    @FXML
    private ListView<Contact> shownList; /**< Reference to the list view in the main view. */
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML 
    private ListView<String> phoneList; /**< Reference to the list of phone numbers in the contact view. */
    @FXML 
    private ListView<String> emailList; /**< Reference to the list of email addresses in the contact view. */
    @FXML
    private Button addButton; /**< Reference to the add button used to add a contact to the address book. */ 
    @FXML
    private Button editButton; /**< Reference to the edit button used to edit an existing contact in the address book. */ 
    @FXML
    private Button cancelButton; /**< Reference to the cancel button, visible in the edit view. */ 
    @FXML
    private Button saveButton; /**< Reference to the save button, visible in the edit view. */ 
    @FXML
    private Button deleteButton; /**< Reference to the delete button, visible in the edit view. */ 
 
    
    
    
    
    
    /**
     * Constructs an AddressBookController with the given profile.
     *
     * @param pathToAddressbook the path to the internal address book file to load
     * @see AddressBook#readFromFile(String)
     */
    public AddressBookController(String pathToAddressBook) {
        AddressBook addressBook = AddressBook.readFromFile(pathToAddressBook);
        this.taggableList = addressBook;
        this.trashCan = addressBook;
        this.contactList = addressBook;
        addressBook.trashCan().removeOlderThan(DAYS); // Example call to removeOlderThan
    }


    /**
     * Exits the application.
     * Called when the user clicks on the exit button on the window from the address book view.
     * 
     * @post The Addressbook is loaded to internal file, then the application is closed.
     * @see #onSave()
     */
    @FXML
    public void exit() {
        // Method implementation
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
        nameField.setEditable(true);
        surnameField.setEditable(true);
        phoneList.setDisable(false);   
        emailList.setDisable(false);   
        tagsList.setDisable(false); 
        
        nameField.setText(""); 
        surnameField.setText(""); 
        phoneList.getItems().clear(); 
        emailList.getItems().clear(); 
        tagsList.getItems().clear(); 
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
     * @see Contact#setEmail(String, int)
     * @see Contact#setPhoneNumber(String, int)
     * @see Contact#addTag(Tag)
     * @see Contact#removeTag(Tag)
     * @see Contact#setName(String)
     * @see Contact#setSurname(String)
     */
    @FXML
    public void onEdit(ActionEvent event) {
        // Method implementation
        deleteButton.setVisible(true); 
        cancelButton.setVisible(true); 
        saveButton.setVisible(true); 
        deleteButton.setDisable(false); 
        cancelButton.setDisable(false); 
        saveButton.setDisable(false); 
        
        editButton.setDisable(true); 
        addButton.setDisable(true); 
        editButton.setVisible(false); 
        addButton.setVisible(false); 
    }

    /**
     * Save the changes made to the address book.
     * Called by {@link #onEdit(ActionEvent)} and {@link #onAdd(ActionEvent)} when the user clicks on the save button in the contact's edit view.
     * @post The changes made to the AddressBook are saved.
     * @see AddressBook#writeToFile(String)
     */
    public void onSave() {
        // Method implementation
        String name = nameField.getText();
        String surname = surnameField.getText();

        ObservableList<String> phones = phoneList.getItems();
        ObservableList<String> emails = emailList.getItems();
        
        String[] phoneArray = phones.toArray(new String[0]);
        String[] emailArray = emails.toArray(new String[0]);
      
        Contact newContact = new Contact(name, surname, phoneArray, emailArray);

        contactList.add(newContact);
    }

    /**
     * Opens the visualization pane for a contact selected from the address book.
     * Called when the user clicks on the contact's name in the contact's visualization pane.
     *
     * @pre The contact to be viewed must exist in the address book.
     * @post The contact's details are displayed in the visualization panel.
     * @see ContactList#get(Contact)
     */
    @FXML
    public void onSelect(ActionEvent event) {
        // Method implementation
    }

    /**
     * Opens the visualization of the recently deleted contacts.
     * Called when the user clicks on the trash can button in the address book view.
     * @post The list of contacts is filtered to show only those in the trash can.
     * @see TrashCan#trashCan()
     */
    @FXML
    public void onTrashCanSelected(ActionEvent event) {
        // Method implementation
    }

    /**
     * Restores a contact from the trash can.
     * Called when the user clicks on the contact restoration button in the trash can view.
     * The user will be asked if they are sure to restore the selected contact.
     *
     * @pre The trash can must contain at least one contact.
     * @post The contact is removed from the trash can and added back to the address book.
     * @see TrashCan#restore(Contact)
     */
    @FXML
    public void onRestore(ActionEvent event) {
        // Method implementation
    }

    /**
     * Delete permanently a contact from recently deleted.
     * Called when the user clicks on the contact deletion button in the recently deleted view.
     * The user will be asked if they are sure to irreversibly delete the selected contact.
     *
     * @pre The recently deleted list must contain at least one contact.
     * @post The contact is removed from the recently deleted list.
     * @see TrashCan#delete(Contact)
     */
    @FXML
    public void onRemovePermanently(ActionEvent event) {
        // Method implementation
    }

    /**
     * Shows the list of contacts marked with the selected tag.
     * Called when the user selects a tag from the tag list in the main view.
     * 
     * @pre There must be at least one tag in the list to select.
     * @post {@link #filteredList} is linked to the addressbook's tag map; the list of contacts is filtered to show only those marked with the selected tag.
     * @see TaggableList#getTagMap()
     */
    @FXML
    public void onSetTagFilter(ActionEvent event) {
        // Method implementation
    }

    /**
     * Resets the visualized list of contacts, linking it to the full contact list.
     * Called when the user clicks on the addresbook home button or types a substring in the search bar.
     *
     * @pre {@link #onSetTagFilter(ActionEvent)} must have been called after the last time this method was called.
     * @post {@link #filteredList} is linked back to the addressbook's list of contacts; the list of contacts shows the complete list of the addressbook, or a filtered list based on the content of the search bar, if it is not empty.
     * @see ContactList#contacts()
     */
    public void onResetTagFilter(ActionEvent event) {
        // Method implementation
    }

    /**
     * Goes back to the profile selection view.
     * Called when the user clicks on the back button in the main view.
     * 
     * @post The address book is saved to the application's assets and the user is taken back to the profile selection view.
     * @see SceneManager#loadProfileSelection()
     * @see #onSave()
     */
    @FXML
    public void toProfileSelection(ActionEvent event) {
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
    public void onImportFromVCardFile(ActionEvent event) {
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
    public void onExportToVCardFile(ActionEvent event) {
        // Method implementation
    }
}
