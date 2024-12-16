package it.unisa.diem.Controller;

import it.unisa.diem.Model.AddressBook;
import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Interfaces.Checker.ImagePathChecker;
import it.unisa.diem.Model.Interfaces.ContactList;
import it.unisa.diem.Model.Interfaces.Filter.BaseFilter;
import it.unisa.diem.Model.Interfaces.Filter.EmailFilter;
import it.unisa.diem.Model.Interfaces.Filter.NameFilter;
import it.unisa.diem.Model.Interfaces.Filter.TagFilter;
import it.unisa.diem.Model.Interfaces.TaggableList;
import it.unisa.diem.Model.Interfaces.TrashCan;
import it.unisa.diem.Model.LocalDateProperty;
import it.unisa.diem.Model.RecentlyDeleted;
import it.unisa.diem.Model.SafeContact;
import it.unisa.diem.Model.Tag;
import it.unisa.diem.Utility.FileManager;
import it.unisa.diem.Utility.SceneManager;

import java.io.File;
import java.io.IOException;
import java.io.StreamCorruptedException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.beans.Observable;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Popup;

/**
 * Controller class for the AddressBook view.
 * As a controller, it manages the interaction between the view and the model. Using the AddressBook model of the selected Profile, it can:
 * - show an alphabetically ordered list of contacts
 * - manipulate such contacts
 * - filter the list based on a substring
 * - filter the list based the tags assigned to them
 * - mantain a "trash can", allowing to view and retrieve recently eliminated contacts.
 */
public class AddressBookController implements Initializable {
    public static final int DAYS = 30; //< Number of days a contact remains in the recently-deleted-list
    
    private TaggableList<Contact> taggableList; //< Reference to access the tag-related methods of the address book.
    private TrashCan trashCan; //< Reference to access the recently-deleted-related methods of the address book.
    private ContactList contactList; //< Reference to access the contact-list-related methods of the address book.
    private FilteredList<Contact> filteredList; //< Data structure to store the list of contacts of the main view.
    private Tag currentTag = null; //< Tracks the currently selected tag
    private boolean isNew; 
    private Contact selected;
    private boolean showingDeletedContacts = false; // Flag to toggle views
    private FilteredList<Contact> deletedFilteredList;
    private List<Tag> selectedTags = new ArrayList<>();
    private static String pathToAddressBook;

    
    @FXML
    private TableView<Contact> contactTableView; //< Reference to the contact view. */
    @FXML
    private TableColumn<Contact, String> nameColumn; //< Reference to the nameColumns view. */
    @FXML
    private TableColumn<Contact, String> surnameColumn; //< Reference to the surnameColumns view. */
    // New column for deletion date
    @FXML
    private TableColumn<Contact, String> deletionDateColumn;
    
    @FXML
    private ListView<Button> tagList; //< Reference to the list view in the tag section. */
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML 
    private ListView<String> phoneList; //< Reference to the list of phone numbers in the contact view. */
    @FXML 
    private ListView<String> emailList; //< Reference to the list of email addresses in the contact view. */
    @FXML
    private Button addButton; //< Reference to the add button used to add a contact to the address book. */ 
    @FXML
    private Button editButton; //< Reference to the edit button used to edit an existing contact in the address book. */ 
    @FXML
    private Button cancelButton; //< Reference to the cancel button, visible in the edit view. */ 
    @FXML
    private Button saveButton; //< Reference to the save button, visible in the edit view. */ 
    @FXML
    private Button deleteButton; //< Reference to the delete button, visible in the edit view. */ 
    @FXML
    private HBox hBoxEditable; 
    @FXML
    private HBox hBoxNotEditable; 
    @FXML 
    private ButtonBar menuBar; 
    @FXML
    private StackPane stackPaneButtons; 
    @FXML
    private TextField searchBar;
    @FXML
    private ImageView profilePicture;
    @FXML
    private StackPane stackPanePhones; 
    @FXML
    private TextField phone1Field; 
    @FXML
    private TextField phone2Field;  
    @FXML
    private TextField phone3Field;
    @FXML
    private TextField email1Field;
    @FXML
    private TextField email2Field;
    @FXML
    private TextField email3Field;
    @FXML
    private Button binButton;
    @FXML
    private StackPane firstStackPane;
    @FXML
    private Button tagButton;
    @FXML
    private ImageView exitButton;

    private ObservableList<Contact> contacts;
    
    /**
     * Constructs an AddressBookController with the given profile.
     *
     * @param pathToAddressbook the path to the internal address book file to load
     * @see AddressBook#readFromFile(String)
     */
    public AddressBookController() {
        pathToAddressBook = SceneManager.getAddressBook();
        try{
            File addressBookFile = new File(pathToAddressBook);
            AddressBook imported;
            if(!addressBookFile.exists())
                FileManager.exportToFile(pathToAddressBook, new AddressBook());
            if(addressBookFile.length()==0)
                imported = new AddressBook();
            else
                imported = AddressBook.readFromFile(pathToAddressBook);
            this.taggableList = imported;
            this.contactList = imported;
            this.trashCan = imported;
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void initialize(URL location, ResourceBundle resources){
        // Configurazione delle colonne
        contactList.add(new Contact("Elli","pata"));
        contactList.add(new Contact("Gennaro","bello"));
        contactList.add(new Contact("Francesco","papa"));
        contactList.add(new Contact("Maurizio","fro"));
        Contact c = new Contact("La","Bri");
        c.addEmail("labriciola@gmail.com", "labri@hot.com");
        c.addTag("micio");
        c.addTag("Uni");
        contactList.add(c);
        Contact c1 = new Contact("Omar","Il bello");
        c1.addTag("micio");
        c1.addTag("sesso");
        contactList.add(c1);
        contactList.add(new Contact("abcdefghijklmnopqrts","jhbfhgebhgberhberhjbg"));
        Contact c2 = new Contact("Oh","No");
        c2.addTag("eliminato");
        c2.addTag("prefe");
        contactList.add(c2);
        contactList.delete(c2);


        initializeContactTableView();
        initializeContactsFilteredList();
        initializeTagListView();
        initializeRecentlyDeleted();
        
        Platform.runLater(() -> {
            if (!filteredList.isEmpty()) {
                contactTableView.getSelectionModel().selectFirst();
            }
            Contact selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
            if(selectedContact != null) {
                editButton.setDisable(false);
                displayContact(selectedContact); 
            }
        });
        
        contacts.remove(c1);
        //contacts.add(new Contact("Elli","Bella"));
    }
    
    /**
     * Exits the application.
     * Called when the user clicks on the exit button on the window from the address book view.
     * 
     * @post The AddressBook is loaded to internal file, then the application is closed.
     * @see #export()
     */
    @FXML
    public void exit() {
        export();
        Platform.exit();
    }

    private void export(){
        try {
            ObservableSet<Contact> set = FXCollections.observableSet();
            for(Contact c : contacts){
                set.add(c);
            }
            contactList.contacts().set(set);
            FileManager.exportToFile(pathToAddressBook, (AddressBook)contactList);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }  
    }

    private void initializeContactTableView() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
        surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getSurname());

        contacts = FXCollections.observableArrayList();
        
        for(Contact c : contactList.contacts()){
            contacts.add(c);
        }
        
        filteredList = new FilteredList<Contact>(contacts);
        contactTableView.setItems(filteredList);

        // contactList.contacts().addListener((SetChangeListener<Contact>) change -> {
        //     if (change.wasAdded()) {
        //         filteredList.add(change.getElementAdded());
        //     } else if (change.wasRemoved()) {
        //         filteredList.remove(change.getElementRemoved());
        //     }
        // });
    }
    
    private <T> void initializeFilteredList(FilteredList<T> listToFilter, TextField searchBar, Predicate<T> filterPredicate) {
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            listToFilter.setPredicate(item -> {
                // If the search bar is empty, show all items
                if (newValue == null || newValue.trim().isEmpty()) {
                    return true;
                }
                // Apply the custom filter predicate
                return filterPredicate.test(item);
            });
        });
    }

    private void initializeContactsFilteredList(){
        initializeFilteredList(filteredList, searchBar, contact -> {
            String searchString = searchBar.getText().trim().toLowerCase();
            TagFilter tagFilterBase = null;
            if (currentTag != null) {
                tagFilterBase = new TagFilter(new BaseFilter(new SimpleStringProperty(currentTag.getNameValue())));
            }
            BaseFilter baseFilter = new BaseFilter(new SimpleStringProperty(searchString));
            TagFilter tagFilter = new TagFilter(baseFilter);
            NameFilter nameFilter = new NameFilter(baseFilter);
            EmailFilter emailFilter = new EmailFilter(baseFilter);

            boolean matchSearch = tagFilter.test(contact) || nameFilter.test(contact) || emailFilter.test(contact);
            if (currentTag != null) {
                return tagFilterBase.test(contact) && matchSearch;
            } else {
                return matchSearch;
            }
        });
    }
    
    private void initializeDeletedFilteredList(){
        initializeFilteredList(deletedFilteredList, searchBar, contact -> {
            String lowerCaseFilter = searchBar.getText().trim().toLowerCase();
            BaseFilter baseFilter = new BaseFilter(new SimpleStringProperty(lowerCaseFilter));
            TagFilter tagFilter = new TagFilter(baseFilter);
            NameFilter nameFilter = new NameFilter(baseFilter);
            EmailFilter emailFilter = new EmailFilter(baseFilter);
            return tagFilter.test(contact) || nameFilter.test(contact) || emailFilter.test(contact);
        });

    }
    
    private void initializeTagListView() {
        ObservableList<Button> tagButtons = FXCollections.observableArrayList();

        for (Object tagObj : taggableList.getTagMap().keySet()) {
            Tag tag = (Tag) tagObj; // Cast esplicito a Tag
            Button tagButton = createTagButton(tag);
            tagButtons.add(tagButton);
        }

        tagList.setItems(tagButtons);

        taggableList.getTagMap().addListener((MapChangeListener<Tag, SetProperty<Contact>>) change -> {
            if (change.wasAdded()) {
                Button newButton = createTagButton(change.getKey());
                tagButtons.add(newButton);
            }
            if (change.wasRemoved()) {
                tagButtons.removeIf(button -> button.getText().equals(change.getKey().toString()));
            }
        });
    }

    private Button createTagButton(Tag tag) {
        Button tagButton = new Button(tag.getNameValue());
        tagButton.setPrefWidth(80);
        tagButton.setContentDisplay(ContentDisplay.LEFT);
        tagButton.setOnAction(event -> {
            if(showingDeletedContacts){
                backToContacts();
            }
            displayContactsForTag(tag);
        });
        return tagButton;
    }

    private void displayContactsForTag(Tag tag) {
        currentTag = tag; // Store the currently selected tag
        searchBar.clear();
        filteredList.setPredicate(contact -> contact.getTags().contains(tag));
    }
    
    private void initializeRecentlyDeleted() {
        RecentlyDeleted rd = trashCan.trashCan();
        rd.removeExpired();
        // Initialize the FilteredList for deleted contacts

        // Create the FilteredList for deleted contacts
        deletedFilteredList = new FilteredList<>(FXCollections.observableArrayList(rd.contacts()), p -> true);

        // Add a new column for deletion date
        deletionDateColumn = new TableColumn<>("Deletion Date");
        deletionDateColumn.setCellValueFactory(cellData -> {
            // Find the deletion date for the contact
            for (Map.Entry<LocalDateProperty, SetProperty<Contact>> entry : rd.get().entrySet()) {
                if (entry.getValue().contains(cellData.getValue())) {
                    LocalDate deletionDate = entry.getKey().get();
                    // Format the date for display
                    String formattedDate = deletionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    return new SimpleStringProperty(formattedDate);
                }
            }
            return new SimpleStringProperty("Unknown");
        });

        // Initially hide the deletion date column in the main view
        deletionDateColumn.setVisible(false);
        deletionDateColumn.setMinWidth(75);
        deletionDateColumn.setPrefWidth(75);
        contactTableView.getColumns().add(deletionDateColumn);

        
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
        clearTextFields();
        if(showingDeletedContacts) {
            onDeleteFromBin();
        }else {
            editable();

            nameField.setPromptText("Name"); 
            surnameField.setPromptText("Surname"); 
            phoneList.getItems().clear(); 
            emailList.getItems().clear(); 

            isNew = true;
            
            
        }
    }
    
    private void onDeleteFromBin(){
        selected = (Contact)contactTableView.getSelectionModel().getSelectedItem();
        if(selected != null) trashCan.trashCan().remove(selected);
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
        selected = (Contact)contactTableView.getSelectionModel().getSelectedItem();
        if(showingDeletedContacts) {
            if (selected != null)
                onRestore();
        } else {
            editable(); 
            isNew = false; 
        }
    }

    private void editable() {
        nameField.setEditable(true);
        surnameField.setEditable(true);
        
        phone1Field.setVisible(true); 
        phone2Field.setVisible(true);
        phone3Field.setVisible(true); 
        tagButton.setVisible(true); 
        tagButton.setDisable(false); 
        
        // buttons enabled and visible 
        deleteButton.setVisible(true); 
        deleteButton.setDisable(false); 
        
        saveButton.setVisible(true); 
        saveButton.setDisable(false); 
        
        cancelButton.setVisible(true); 
        cancelButton.setDisable(false); 
        
        // buttons disabled and invisible
        addButton.setVisible(false); 
        addButton.setDisable(true); 
        
        editButton.setVisible(false); 
        editButton.setDisable(true); 

        phoneList.setVisible(false); 
    
        stackPanePhones.getChildren().add(stackPanePhones.getChildren().remove(0));
        stackPaneButtons.getChildren().add(stackPaneButtons.getChildren().remove(0));
    }
    
    private void notEditable() {
        
        nameField.setEditable(false);
        surnameField.setEditable(false);
        phone1Field.setVisible(false); 
        phone2Field.setVisible(false);
        phone3Field.setVisible(false); 
        tagButton.setVisible(false); 
        tagButton.setDisable(true); 
      
        // buttons disabled and invisible
        deleteButton.setVisible(false); 
        deleteButton.setDisable(true); 
        
        saveButton.setVisible(false); 
        saveButton.setDisable(true); 
        
        cancelButton.setVisible(false); 
        cancelButton.setDisable(true); 
        
        // buttons enabled and visible 
        addButton.setVisible(true); 
        addButton.setDisable(false); 
        
        editButton.setVisible(true); 
        
        phoneList.setVisible(true); 
        
        stackPanePhones.getChildren().add(stackPanePhones.getChildren().remove(0));
        stackPaneButtons.getChildren().add(stackPaneButtons.getChildren().remove(0));
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
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm deletion");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Are you sure you want to delete this contact?");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                notEditable();
                Contact selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
                if(selectedContact != null) 
                    contacts.remove(selectedContact);
                    //contactList.delete(selectedContact);
                if (!filteredList.isEmpty()) {
                    contactTableView.getSelectionModel().selectFirst();
                }
                Contact firstContact = (Contact)contactTableView.getItems().get(0);
                if(firstContact != null) {
                    editButton.setDisable(false);
                    displayContact(firstContact); 
                }
            } else {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setTitle("An error occurred");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("The selected contact was not deleted due to an error. ");

                    errorAlert.showAndWait();
            }
        });
      
    }
    
    @FXML
    public void onCancel(ActionEvent event) {
        // Method implementation
        notEditable(); 
    }

    /**
     * Save the changes made to the address book.
     * Called by {@link #onEdit(ActionEvent)} and {@link #onAdd(ActionEvent)} when the user clicks on the save button in the contact's edit view.
     * @post The changes made to the AddressBook are saved.
     * @see AddressBook#writeToFile(String)
     */
    public void onSave() {
        // Recupera i dati immessi dall'utente
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String phone1 = phone1Field.getText().trim();
        String phone2 = phone2Field.getText().trim();
        String phone3 = phone3Field.getText().trim();
        String email1 = email1Field.getText().trim();
        String email2 = email2Field.getText().trim();
        String email3 = email3Field.getText().trim();
        
        Contact selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
        if(selectedContact != null) {
            if (name.isEmpty() && surname.isEmpty()) showError("Must write Surname or Name");
            if (!name.isEmpty() && !selectedContact.setName(name))
                //showError("Write a valid Name");
            if (!surname.isEmpty() && !selectedContact.setSurname(surname))
                //showError("Write a valid Surname");
            
            if (!phone1.isEmpty()) selectedContact.setPhoneNumber(phone1, 1);
            if (!phone2.isEmpty()) selectedContact.setPhoneNumber(phone2, 2);
            if (!phone3.isEmpty()) selectedContact.setPhoneNumber(phone3, 3);

            if (!email1.isEmpty()) selectedContact.setEmail(email1, 1);
            if (!email2.isEmpty()) selectedContact.setEmail(email2, 2);
            if (!email3.isEmpty()) selectedContact.setEmail(email3, 3);
            
            // MODIFICA I TAGS
        } else {
            SafeContact newContact = SafeContact.safeContact(name, surname);
            if (newContact != null) {
                // Aggiungi i numeri di telefono se validi
                if (!phone1.isEmpty()) newContact.addPhoneNumber(phone1);
                if (!phone2.isEmpty()) newContact.addPhoneNumber(phone2);
                if (!phone3.isEmpty()) newContact.addPhoneNumber(phone3);

                // Aggiungi le email se valide
                if (!email1.isEmpty()) newContact.addEmail(email1);
                if (!email2.isEmpty()) newContact.addEmail(email2);
                if (!email3.isEmpty()) newContact.addEmail(email3);

                // leggi i TAG

                // Aggiungi il contatto a una lista o esegui altre operazioni di salvataggio
                Contact c = newContact;
                if (c != null)
                    contacts.add(newContact);
                    //contactList.add(c);

                
            } else showError("Error on Saving");
        }
        notEditable();
        // Update filtered list to show the newly added contact
        searchBar.clear();
        
    }

    // Metodo per mostrare messaggi d'errore
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Aggiorna la FilteredList per riflettere le modifiche
    private void updateFilteredList() {
        filteredList.setPredicate(filteredList.getPredicate()); // Triggera l'aggiornamento
    }

    // Metodo per resettare i campi di testo
    private void clearTextFields() {
        nameField.clear();
        surnameField.clear();
        phone1Field.clear();
        phone2Field.clear();
        phone3Field.clear();
        email1Field.clear();
        email2Field.clear();
        email3Field.clear();
        // -> METTERE SCEGLI IMMAGINE
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
    public void onSelect(MouseEvent event) {
        // Method implementation
        Contact selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
        if(selectedContact != null) {
            editButton.setDisable(false);
            displayContact(selectedContact);
            notEditable();
        }
    }
    
    private void displayContact(Contact c) {
        nameField.setText(c.getNameValue()); 
        surnameField.setText(c.getSurnameValue()); 
        phoneList.getItems().clear(); 
        emailList.getItems().clear(); 
        profilePicture.setImage(new Image(getClass().getResource(c.getPicture()).toExternalForm()));

                
        for(int i=0 ; i<c.getEmailList().length ; i++) {
            emailList.getItems().add(c.getEmailAtIndex(i)); 
        }
        
        for(int i=0 ; i<c.getPhoneNumberList().length ; i++) {
            phoneList.getItems().add(c.getPhoneNumberAtIndex(i)); 
        }
        
        
        // I TAG VISIBILI ??
    }

    /**
     * Opens the visualization of the recently deleted contacts.
     * Called when the user clicks on the trash can button in the address book view.
     * @post The list of contacts is filtered to show only those in the trash can.
     * @see TrashCan#trashCan()
     */
    @FXML
    public void onTrashCanSelected(ActionEvent event) {
        editButton.setText("Restore");
        addButton.setText("Delete");

        
        // Bind the FilteredList to the TableView and show the deletion date column
        searchBar.clear();
        showingDeletedContacts = true;
        contactTableView.setItems(deletedFilteredList);
        deletionDateColumn.setVisible(true);
        
        if (!deletedFilteredList.isEmpty()) {
            contactTableView.getSelectionModel().selectFirst();
        }
        Contact selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
        if(selectedContact != null) {
            displayContact(selectedContact); 
        }
        
        
        // Update the SearchBar to filter the recently deleted contacts
        initializeDeletedFilteredList();
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
    public void onRestore() {
        trashCan.restore(selected);
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
        searchBar.clear();
        if (!showingDeletedContacts){
            currentTag = null; // Clear the selected tag
            filteredList.setPredicate(contact -> true); // Show all contacts
        } else {
            backToContacts();
        }
    }
    
    private void backToContacts() {
        // Restore the original contact list and hide the deletion date column
        showingDeletedContacts = false;
        contactTableView.setItems(filteredList);
        deletionDateColumn.setVisible(false);

        // Remove the listener from the search bar
        initializeContactsFilteredList();
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
    
    @FXML
    public void onMouseEntered(MouseEvent event) {
        // Method implementation
        menuBar.setVisible(true);
    }
    
    @FXML
    public void onMouseExited(MouseEvent event) {
        // Method implementation
        menuBar.setVisible(true); 
    }

    @FXML
    public void onTag(ActionEvent event) {
        
        List<Tag> temporaryTags = new ArrayList<>(); 
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); 
        pane.setPrefSize(tagButton.getScene().getWidth(), tagButton.getScene().getHeight()); // Imposta le dimensioni dell'overlay

        HBox popHBox = new HBox();
        popHBox.setStyle("-fx-padding: 10px 20px 15px 5px;");

        Parent root = tagButton.getScene().getRoot();
        ((Pane) root).getChildren().add(pane); 

        Popup popup = new Popup();
        int tagNumber = taggableList.getTagMap().keySet().size();

        VBox popupContent = new VBox(tagNumber); 
        List<CheckBox> checkBoxes = new ArrayList<>(); 
        
        VBox next = new VBox(); 

        int i = 0;
        for (Object tagObj : taggableList.getTagMap().keySet()) {
            Tag tag = (Tag) tagObj;
            CheckBox c = new CheckBox(tag.getNameValue()); 
            if(!isNew) {
                selected = (Contact)contactTableView.getSelectionModel().getSelectedItem();
                SetProperty<Tag> temp = selected.getTags(); 
                for(Tag t : temp) {
                    if(c.getText().equals(t.getNameValue()))
                        c.setSelected(true); 
                
            } 
            checkBoxes.add(c); 
            popupContent.getChildren().add(c);
            i++;
        }

        TextField customTagField = new TextField();
        customTagField.setPromptText("Add a new Tag");
        next.getChildren().add(customTagField);

        Button addTagButton = new Button("Add");
        next.getChildren().add(addTagButton);

        Button closeButton = new Button("Done");
        next.getChildren().add(closeButton);

        popupContent.setStyle("-fx-background-color: white;");
        next.setStyle("-fx-background-color: white;");
        popHBox.getChildren().addAll(popupContent, next);
        popup.getContent().add(popHBox);

        addTagButton.setOnAction(e -> {
            String customTag = customTagField.getText();
            if (!customTag.isEmpty()) {
              
                Tag newTag = new Tag();
                newTag.setNameValue(customTag);

                boolean tagExists = temporaryTags.stream().anyMatch(t -> t.getNameValue().equals(newTag.getNameValue())) 
                                    || taggableList.getTagMap().containsKey(newTag);

                if (!tagExists) {
                    temporaryTags.add(newTag);  
                    CheckBox newTagCheckBox = new CheckBox(newTag.getNameValue());
                    popupContent.getChildren().add(newTagCheckBox);  // Aggiungi la checkbox per il nuovo tag
                    checkBoxes.add(newTagCheckBox); // Aggiungi la nuova checkbox alla lista
                    customTagField.clear();  // Pulisci il campo di input
                }
            }
        });

  
        closeButton.setOnAction(e -> {
            for(CheckBox cb : checkBoxes) {
                if (cb.isSelected()) {
                    Tag t = new Tag();
                    t.setNameValue(cb.getText());

                    boolean tagExists = temporaryTags.stream().anyMatch(existingTag -> existingTag.getNameValue().equals(t.getNameValue())) 
                                        || taggableList.getTagMap().containsKey(t);
                    if (!tagExists) {
                        selectedTags.add(t);  
                    }
                }
            }
            temporaryTags.clear();
            popup.hide();
            pane.setVisible(false);
        });

            popup.show(tagButton.getScene().getWindow());
            pane.setVisible(true);
        }
    }
    
    @FXML
    public void onSelectProfileImage(MouseEvent event) {

        ImagePathChecker pathChecker = new ImagePathChecker(); 
        
        FileChooser fileChooser = new FileChooser();
        
        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        
        File selectedFile = fileChooser.showOpenDialog(null); 
        
        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            if (pathChecker.check(filePath)) {
              
                Image image = new Image(selectedFile.toURI().toString());
                profilePicture.setImage(image); 
                saveProfileImage(filePath);
                
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("An error occurred.");
                alert.setHeaderText(null);
                alert.setContentText("The selected image is not valid.\nPath Selected: "+filePath);

                alert.showAndWait(); 
            }
        }
    }

    private void saveProfileImage(String imagePath) {
   
        String destinationPath = FileManager.generateProfilePicturePath(imagePath.substring(imagePath.lastIndexOf('.')+1).toLowerCase()); 
        try {
            Files.copy(Paths.get(imagePath), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("An error occurred.");
            alert.setHeaderText(null);
            alert.setContentText("The image was not saved correctly. ");

            alert.showAndWait(); 
        }
    }
    
}