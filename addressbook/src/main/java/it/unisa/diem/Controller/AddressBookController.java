package it.unisa.diem.Controller;

import it.unisa.diem.AddressBookApplication;
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
import java.io.FileNotFoundException;
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
import javafx.scene.control.SplitPane;
import it.unisa.diem.Model.Interfaces.Filter.PhoneFilter;
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
 * As a controller, it manages the interaction between the view and the model. Using the AddressBook model of the selectedContact Profile, it can:
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
    private Tag currentTag = null; //< Tracks the currently selectedContact tag
    private boolean isNew = false; 
    private Contact selectedContact = null;
    private boolean showingDeletedContacts = false; // Flag to toggle views
    private FilteredList<Contact> deletedFilteredList;
    private List<Tag> selectedTags = new ArrayList<>();
    private static String pathToAddressBook;
    private boolean error = false;
    private boolean editing = false;

    
    @FXML
    private TableView<Contact> contactTableView; //< Reference to the contact view. 
    @FXML
    private TableColumn<Contact, String> nameColumn; //< Reference to the nameColumns view. 
    @FXML
    private TableColumn<Contact, String> surnameColumn; //< Reference to the surnameColumns view. 


    @FXML
    private TableColumn<Contact, String> deletionDateColumn; //< New column for deletion date
    
    @FXML
    private ListView<Button> tagList; //< Reference to the list view in the tag section. 
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML 
    private ListView<String> phoneList; //< Reference to the list of phone numbers in the contact view. 
    @FXML 
    private ListView<String> emailList; //< Reference to the list of email addresses in the contact view. 
    @FXML
    private Button addButton; //< Reference to the add button used to add a contact to the address book.  
    @FXML
    private Button editButton; //< Reference to the edit button used to edit an existing contact in the address book.  
    @FXML
    private Button cancelButton; //< Reference to the cancel button, visible in the edit view.  
    @FXML
    private Button saveButton; //< Reference to the save button, visible in the edit view.  
    @FXML
    private Button deleteButton; //< Reference to the delete button, visible in the edit view.  
    @FXML
    private SplitPane SplitPaneLeft;
    @FXML
    private SplitPane SplitPaneRight;
    @FXML
    private Button exportButton;
    @FXML
    private Button importButton;
    @FXML
    private Button profileButton;
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
    private StackPane stackPaneEmails;
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
    private ObservableList<Contact> deletedContacts;
    
        public boolean hasImageChanged;
        
        public String imageURL;
                
                /**
                 * Constructs an AddressBookController with the given profile.
                 *
                 * @param pathToAddressbook the path to the internal address book file to load
                 * @see AddressBook#readFromFile(String)
                 */
                public AddressBookController() {
                    pathToAddressBook = SceneManager.getAddressBook();
                    try{
                        // File addressBookFile = new File(pathToAddressBook);
                        AddressBook imported;
                        // if(!addressBookFile.exists()) <--- should always be false
                        //     FileManager.exportToFile(pathToAddressBook, new AddressBook());
                        // if(addressBookFile.length()==0) <--- should always be false
                        //     imported = new AddressBook();
                        //else
                        try{imported = AddressBook.readFromFile(pathToAddressBook);}
                        catch (IOException e) {
                            imported = new AddressBook();
                        }
            
                        this.taggableList = imported;
                        this.contactList = imported;
                        this.trashCan = imported;
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }// catch (IOException e) {
                    //     e.printStackTrace();
                    // }
                }
                
                public void initialize(URL location, ResourceBundle resources){
                    exitButton.setOnMouseClicked(e->{
                        exit();}
                    );
                    exitButton.setImage(new Image(getClass().getResource("/it/unisa/diem/view_resources/exit_button.png").toExternalForm()));
                    exitButton.setOnMouseClicked((e)->{exit();});
                    exitButton.setLayoutY(-2d);
                    SplitPaneLeft.setMaxSize(300,300);
                    SplitPaneRight.setMaxSize(500,500);
                    SplitPaneRight.setMinSize(100,200);
                    SplitPaneLeft.setMinSize(100,200);
            
                    initializeContactTableView();
                    initializeContactsFilteredList();
                    initializeTagListView();
                    initializeRecentlyDeleted();
                    contactTableView.setPlaceholder(new Label("There are still no contacts"));
                    
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
                    SceneManager.getPSRunnable().run();
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
                
                private void onDeleteContact(Contact c) {
                    for (Tag t: c.getTags())
                        taggableList.removeTagFromContact(t, c);
                    
                    contactList.delete(c);
                    contacts.remove(c);
                    clearTextFields();
                    deletedContacts.add(c);
                }
                
                private void onAddContact(Contact c){
                    contacts.add(c);
                    contactList.add(c);
                }
                
                private void initializeRecentlyDeleted() {
                    RecentlyDeleted rd = trashCan.trashCan();
                    rd.removeExpired();
                    // Initialize the FilteredList for deleted contacts
            
                    // Create the FilteredList for deleted contacts
                    deletedContacts = FXCollections.observableArrayList();
                    for(Contact c : rd.contacts()){
                        deletedContacts.add(c);
                    }
                    deletedFilteredList = new FilteredList<Contact>(deletedContacts);
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
                
                private void initializeContactTableView() {
                    nameColumn.setCellValueFactory(cellData -> cellData.getValue().getName());
                    surnameColumn.setCellValueFactory(cellData -> cellData.getValue().getSurname());
            
                    contacts = FXCollections.observableArrayList();
                    List<Contact> temp= new ArrayList<>();
                    for (Contact contact : contactList.contacts()) {
                        temp.add(contact);
                    }
            
                    for (Contact contact : temp) {
                        contacts.add(contact);
                    }
                    
                    filteredList = new FilteredList<Contact>(contacts);
                    contactTableView.setItems(filteredList);
            
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
                        PhoneFilter phoneFilter = new PhoneFilter(baseFilter);
                        EmailFilter emailFilter = new EmailFilter(baseFilter);
            
                        boolean matchSearch = tagFilter.test(contact) || nameFilter.test(contact) || emailFilter.test(contact) || phoneFilter.test(contact);
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
                        PhoneFilter phoneFilter = new PhoneFilter(baseFilter);
                        EmailFilter emailFilter = new EmailFilter(baseFilter);
                        return tagFilter.test(contact) || nameFilter.test(contact) || emailFilter.test(contact) || phoneFilter.test(contact);
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
                    currentTag = tag; // Store the currently selectedContact tag
                    searchBar.clear();
                    filteredList.setPredicate(contact -> contact.getTags().contains(tag));
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
                        onRemovePermanently(event);
                    }else {
                        editable();
                       
                        nameField.setPromptText("Name"); 
                        surnameField.setPromptText("Surname"); 
                        phoneList.getItems().clear(); 
                        emailList.getItems().clear(); 
            
                        isNew = true;
                        deleteButton.setDisable(true); 
                        
                        
                    }
                }
                
                /**
                 * Open the edit mode for a contact selectedContact from the address book.
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
                    selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
                    if(showingDeletedContacts) {
                        if (selectedContact != null)
                            onRestore();
                    } else {
                        editable(); 
                        isNew = false; 
                    }
                }
            
                private void editable() {
                    editing = true;
                    nameField.setEditable(true);
                    surnameField.setEditable(true);
                    
                    phone1Field.setVisible(true); 
                    phone2Field.setVisible(true);
                    phone3Field.setVisible(true); 
                    
                    if(!isNew) {
                        try{
                            phone1Field.setText(phoneList.getItems().get(0)); 
                            phone2Field.setText(phoneList.getItems().get(1));
                            phone3Field.setText(phoneList.getItems().get(2));
                            email1Field.setText(emailList.getItems().get(0)); 
                            email2Field.setText(emailList.getItems().get(1));
                            email3Field.setText(emailList.getItems().get(2));
                        } catch(Exception e){
                            
                        }
                    }
                    phone1Field.setEditable(true); 
                    phone2Field.setEditable(true);
                    phone3Field.setEditable(true); 
                    
                    email1Field.setVisible(true); 
                    email2Field.setVisible(true);
                    email3Field.setVisible(true);
                    
                    email1Field.setEditable(true); 
                    email2Field.setEditable(true);
                    email3Field.setEditable(true); 
                    
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
                    emailList.setVisible(false); 
                  
                
                    stackPaneButtons.getChildren().add(stackPaneButtons.getChildren().remove(0));
                    stackPaneEmails.getChildren().add(stackPaneEmails.getChildren().remove(0));
                    stackPanePhones.getChildren().add(stackPanePhones.getChildren().remove(0));

                    if(selectedContact != null && selectedContact.getPicture()!=null && !selectedContact.getPicture().isEmpty()) {
                        profilePicture.setImage(new Image(selectedContact.getPicture()));
                    } else
                        profilePicture.setImage(new Image(getClass().getResource("/it/unisa/diem/view_resources/default_picture.png").toExternalForm()));
                }
                
                private void notEditable() {
                    editing = false;
                    selectedTags.clear();
            
                    nameField.setEditable(false);
                    surnameField.setEditable(false);
                    
                    phone1Field.setVisible(false); 
                    phone2Field.setVisible(false);
                    phone3Field.setVisible(false); 
                    
                    email1Field.setVisible(false); 
                    email2Field.setVisible(false);
                    email3Field.setVisible(false); 
                    
                    email1Field.setEditable(false); 
                    email2Field.setEditable(false);
                    email3Field.setEditable(false); 
                    
                    phone1Field.setEditable(false); 
                    phone2Field.setEditable(false);
                    phone3Field.setEditable(false); 
                    
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
                    emailList.setVisible(true);
                    
                    stackPaneButtons.getChildren().add(stackPaneButtons.getChildren().remove(0));
                    stackPaneEmails.getChildren().add(stackPaneEmails.getChildren().remove(0));
                    stackPanePhones.getChildren().add(stackPanePhones.getChildren().remove(0));
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
                    isNew = false;
                    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
                    confirmationAlert.setTitle("Confirm deletion");
                    confirmationAlert.setHeaderText(null);
                    confirmationAlert.setContentText("Are you sure you want to delete this contact?");
            
                    confirmationAlert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            notEditable();
                            Contact selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
                            if(selectedContact != null) 
                                onDeleteContact(selectedContact);
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
                                errorAlert.setContentText("The selectedContact contact was not deleted due to an error. ");
            
                                errorAlert.showAndWait();
                        }
                    });
                  
                }
                
                private void changeButtons() {
                    if(showingDeletedContacts){
                        editButton.setText("Restore");
                        addButton.setText("Delete");
                    }else {
                        editButton.setText("Edit"); 
                        addButton.setText("Add"); 
                    } 
                }
                
                @FXML
                public void onCancel(ActionEvent event) {
                    // Method implementation
                    isNew = false;
                    notEditable();
                    clearTextFields();
                }
            
                /**
                 * Save the changes made to the address book.
                 * Called by {@link #onEdit(ActionEvent)} and {@link #onAdd(ActionEvent)} when the user clicks on the save button in the contact's edit view.
                 * @post The changes made to the AddressBook are saved.
                 * @see AddressBook#writeToFile(String)
                 */
                @FXML
                public void onSave(ActionEvent event) {
                    error = false; 
                    String name = nameField.getText().trim();
                    String surname = surnameField.getText().trim();
                    String phone1 = phone1Field.getText().trim();
                    String phone2 = phone2Field.getText().trim();
                    String phone3 = phone3Field.getText().trim();
                    String email1 = email1Field.getText().trim();
                    String email2 = email2Field.getText().trim();
                    String email3 = email3Field.getText().trim();
            
                    Contact selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
                    if(selectedContact != null && !isNew) {
                        if ((name == null || name.isEmpty()) && (surname == null || surname.isEmpty()))
                            showError("Must write Surname or Name");
                       
                        if (name != null && !name.isEmpty() && !selectedContact.setName(name))
                            showError("Write a valid Name");
                        else contactList.get(selectedContact).setName(name);
                        if (surname != null && !surname.isEmpty() && !selectedContact.setSurname(surname))
                            showError("Write a valid Surname");
                        else contactList.get(selectedContact).setSurname(surname);
                        if (phone1 != null)
                            if (!selectedContact.setPhoneNumber(phone1, 0)) 
                                if(phone1.isEmpty()) contactList.get(selectedContact).removePhoneNumberAtIndex(0);
                                else showError("Write a valid phone number.");
                            else contactList.get(selectedContact).setPhoneNumber(phone1, 0);  
                    
                        if (phone2 != null)
                        if (!selectedContact.setPhoneNumber(phone2, 1)) 
                            if(phone2.isEmpty()) contactList.get(selectedContact).removePhoneNumberAtIndex(1);
                            else showError("Write a valid phone number.");
                        else contactList.get(selectedContact).setPhoneNumber(phone2, 1);  
                    
                        if (phone3 != null)
                            if (!selectedContact.setPhoneNumber(phone3, 2)) 
                                if(phone3.isEmpty()) contactList.get(selectedContact).removePhoneNumberAtIndex(2);
                                else showError("Write a valid phone number.");
                            else contactList.get(selectedContact).setPhoneNumber(phone3, 2);
                        
                        if (email1 != null)
                            if (!selectedContact.setEmail(email1, 0)) 
                                if(email1.isEmpty()) contactList.get(selectedContact).removeEmailAtIndex(0);
                                else showError("Write a valid email.");
                            else contactList.get(selectedContact).setEmail(email1, 0); 
                        if (email2 != null)
                            if (!selectedContact.setEmail(email2, 1)) 
                                if(email2.isEmpty()) contactList.get(selectedContact).removeEmailAtIndex(1);
                                else showError("Write a valid email.");
                            else contactList.get(selectedContact).setEmail(email2, 1); 
                        if (email3 != null)
                            if (!selectedContact.setEmail(email3, 2)) 
                                if(email3.isEmpty()) contactList.get(selectedContact).removeEmailAtIndex(2);
                                else showError("Write a valid email.");
                            else contactList.get(selectedContact).setEmail(email3, 2); 
                        
                        // TAG
                        if (selectedTags != null) {
                            for(Tag t: selectedTags)
                                System.out.println(t.getNameValue());
                            for(Tag t : selectedTags){
                                selectedContact.addTag(t.getNameValue());
                                taggableList.addTagToContact(t, selectedContact);
                            }
                            removeTag();
                        }
            
                        if (!error) {
                            notEditable();
                            searchBar.clear();
                            displayContact(selectedContact);
                        }
            
                    } else {
                        SafeContact newContact = SafeContact.safeContact(name, surname);
                        if (newContact != null) {
                          
                            if (phone1 != null && !phone1.isEmpty()) if(!newContact.addPhoneNumber(phone1)) 
                                showError("Write a valid phone number.");
                            if (phone2 != null && !phone2.isEmpty()) if (!newContact.addPhoneNumber(phone2)) 
                                showError("Write a valid phone number.");
                            if (phone3 != null && !phone3.isEmpty()) if (!newContact.addPhoneNumber(phone3)) 
                                showError("Write a valid phone number.");
            
                            if (email1 != null && !email1.isEmpty()) if(!newContact.addEmail(email1)) 
                                showError("Write a valid email");
                            if (email2 != null && !email2.isEmpty()) if(!newContact.addEmail(email2)) 
                                showError("Write a valid email");
                            if (email3 != null && !email3.isEmpty()) if(!newContact.addEmail(email3)) 
                                showError("Write a valid email");
            
                            if(selectedTags != null)
                                for(Tag t : selectedTags){
                                    newContact.addTag(t.getNameValue());
                                    taggableList.addTagToContact(t, newContact);
                                } 
            
            
                            
                            if (!error) {
                                if (newContact != null)
                                    onAddContact((Contact)newContact);
                                notEditable();
                                searchBar.clear();
                                error = false;
                                displayContact(newContact);
                            }
            
                            
                            
                            
                        } else showError("Error in saving.\nInsert valid fields.");
                    }
                    if(hasImageChanged){
                        profilePicture.setImage(new Image(imageURL));
                        hasImageChanged = false;
                    }
    }

    private void removeTag() {
        Set<Tag> tagSet = taggableList.getTagMap().keySet(); 
        for(Tag t : tagSet) 
            if(!selectedTags.contains(t)) {
                taggableList.removeTagFromContact(t, selectedContact);
            }
                
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("An error occurred.");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        error = true;
    }

    private void clearTextFields() {
        nameField.clear();
        surnameField.clear();
        phone1Field.clear();
        phone2Field.clear();
        phone3Field.clear();
        email1Field.clear();
        email2Field.clear();
        email3Field.clear();
        selectedTags.clear();
        phoneList.getItems().clear();
        emailList.getItems().clear();
        selectedTags.clear();
    }
        
    /**
     * Opens the visualization pane for a contact selectedContact from the address book.
     * Called when the user clicks on the contact's name in the contact's visualization pane.
     *
     * @pre The contact to be viewed must exist in the address book.
     * @post The contact's details are displayed in the visualization panel.
     * @see ContactList#get(Contact)
     */
    @FXML
    public void onSelect(MouseEvent event) {
        clearTextFields();

        if(editing) {
            isNew = false; 
            notEditable();
        } 
        selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
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
        if(c.getPicture()!=null &&  !c.getPicture().isEmpty()) {
            profilePicture.setImage(new Image(c.getPicture()));
        } else
            profilePicture.setImage(new Image(getClass().getResource("/it/unisa/diem/view_resources/default_picture.png").toExternalForm()));

                
        for(int i=0 ; i<c.getEmailList().length ; i++) {
            emailList.getItems().add(c.getEmailAtIndex(i)); 
        }
        
        for(int i=0 ; i<c.getPhoneNumberList().length ; i++) {
            phoneList.getItems().add(c.getPhoneNumberAtIndex(i)); 
        }
        
        
        for (Tag t: c.getTags())
            selectedTags.add(t);
    }

    /**
     * Opens the visualization of the recently deleted contacts.
     * Called when the user clicks on the trash can button in the address book view.
     * @post The list of contacts is filtered to show only those in the trash can.
     * @see TrashCan#trashCan()
     */
    @FXML
    public void onTrashCanSelected(ActionEvent event) {
        showingDeletedContacts = true;
        clearTextFields();
        changeButtons();
        // Bind the FilteredList to the TableView and show the deletion date column
        searchBar.clear();
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
     * The user will be asked if they are sure to restore the selectedContact contact.
     *
     * @pre The trash can must contain at least one contact.
     * @post The contact is removed from the trash can and added back to the address book.
     * @see TrashCan#restore(Contact)
     */
    public void onRestore() {
        if (selectedContact != null) {
            trashCan.restore(selectedContact);
            onAddContact(selectedContact);
            deletedContacts.remove(selectedContact);
            clearTextFields();
        }
    }

    /**
     * Delete permanently a contact from recently deleted.
     * Called when the user clicks on the contact deletion button in the recently deleted view.
     * The user will be asked if they are sure to irreversibly delete the selectedContact contact.
     *
     * @pre The recently deleted list must contain at least one contact.
     * @post The contact is removed from the recently deleted list.
     * @see TrashCan#delete(Contact)
     */
    @FXML
    public void onRemovePermanently(ActionEvent event) {
        if (selectedContact != null) {
            trashCan.delete(selectedContact);
            deletedContacts.remove(selectedContact);
            clearTextFields();
        }
    }

    /**
     * Shows the list of contacts marked with the selectedContact tag.
     * Called when the user selects a tag from the tag list in the main view.
     * 
     * @pre There must be at least one tag in the list to select.
     * @post {@link #filteredList} is linked to the addressbook's tag map; the list of contacts is filtered to show only those marked with the selectedContact tag.
     * @see TaggableList#getTagMap()
     */
    @FXML
    public void onSetTagFilter(ActionEvent event) {
        // Method implementation Fatto in un metodo interno
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
        clearTextFields();
        if (!showingDeletedContacts){
            currentTag = null; // Clear the selectedContact tag
            filteredList.setPredicate(contact -> true); // Show all contacts
        } else {
            backToContacts();
        }
    }
    
    private void backToContacts() {
        // Restore the original contact list and hide the deletion date column
        showingDeletedContacts = false;
        changeButtons();
        contactTableView.setItems(filteredList);
        deletionDateColumn.setVisible(false);
        clearTextFields();
        // Remove the listener from the search bar
        initializeContactsFilteredList();
    }
    
    /**
     * Goes back to the profile selection view.
     * Called when the user clicks on the back button in the main view.
     * 
     * @post The address book is saved to the application's assets and the user is taken back to the profile selection view.
     * @see SceneManager#loadProfileSelection()
     */
    @FXML
    public void toProfileSelection(ActionEvent event) {
        SceneManager.setABRunnable(()->{export();});
        try {
            AddressBookApplication.setRoot("ProfileSelection");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); //! This should never happen
        }
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
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("VCard Files", "*.vcf"));
            File selectedFile = fileChooser.showOpenDialog(exitButton.getScene().getWindow());
            if (selectedFile != null) {
                imageURL = selectedFile.toURI().toString();
                hasImageChanged = true;
                AddressBook imported = FileManager.importFromVCard(selectedFile.getAbsolutePath());
                if (imported != null) {
                    contactList = imported;
                    contacts.clear();
                    for(Contact c : contactList.contacts()){
                        contacts.add(c);
                    }
                    filteredList.setPredicate(filteredList.getPredicate());
                    contactTableView.getSelectionModel().selectFirst();
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("An  occurred while importing from VCard file.");
            alert.showAndWait();
        }     
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
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Export to VCard");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to create a new file or select an existing file?");

        ButtonType createButton = new ButtonType("Create New");
        ButtonType selectButton = new ButtonType("Select Existing");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(createButton, selectButton, cancelButton);

        alert.showAndWait().ifPresent(response -> {
            if (response == createButton) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("VCard Files", "*.vcf"));
                File newFile = fileChooser.showSaveDialog(exitButton.getScene().getWindow());
                if (newFile != null) {
                    try {
                        FileManager.exportAsVCard(newFile.getAbsolutePath(), (AddressBook) contactList);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Alert errorAlert = new Alert(AlertType.ERROR);
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("An error occurred while exporting to VCard file.");
                        errorAlert.showAndWait();
                    }
                }
            } else if (response == selectButton) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("VCard Files", "*.vcf"));
                File selectedFile = fileChooser.showOpenDialog(exitButton.getScene().getWindow());
                if (selectedFile != null) {
                    try {
                        FileManager.exportAsVCard(selectedFile.getAbsolutePath(), (AddressBook) contactList);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Alert errorAlert = new Alert(AlertType.ERROR);
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("An error occurred while exporting to VCard file.");
                        errorAlert.showAndWait();
                    }
                }
            }
        });
    }
    
    @FXML
    public void onMouseEntered(MouseEvent event) {
        menuBar.setVisible(true);
    }
    
    @FXML
    public void onMouseExited(MouseEvent event) {
        menuBar.setVisible(true); 
    }

    @FXML
    public void onTag(ActionEvent event) {
       
        selectedTags.clear();
        
        List<Tag> temporaryTags = new ArrayList<>(); 
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); 
        pane.setPrefSize(tagButton.getScene().getWidth(), tagButton.getScene().getHeight()); 

        HBox popHBox = new HBox();
        popHBox.setStyle("-fx-padding: 10px 20px 15px 5px;");

        Parent root = tagButton.getScene().getRoot();
        ((Pane) root).getChildren().add(pane); 

        Popup popup = new Popup();
        int tagNumber = taggableList.getTagMap().keySet().size();

        VBox popupContent = new VBox(tagNumber); 
        List<CheckBox> checkBoxes = new ArrayList<>(); 
        
        VBox next = new VBox(); 
        CheckBox c;
        selectedContact = (Contact)contactTableView.getSelectionModel().getSelectedItem();
        SetProperty<Tag> temp = null;
        if(selectedContact != null)
            temp = selectedContact.getTags(); 
      
        for (Object tagObj : taggableList.getTagMap().keySet()) {
            Tag tag = (Tag) tagObj;
            c = new CheckBox(tag.getNameValue()); 
            
            if(!isNew || temp != null) {
                if( temp.contains(tag) ) c.setSelected(true);
            } 
            checkBoxes.add(c);
            popupContent.getChildren().add(c);
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

                temporaryTags.add(newTag);  
                CheckBox newTagCheckBox = new CheckBox(newTag.getNameValue());
                popupContent.getChildren().add(newTagCheckBox); 
                checkBoxes.add(newTagCheckBox);
                customTagField.clear();  
            }
        });

  
        closeButton.setOnAction(e -> {
            for(CheckBox cb : checkBoxes) {
                if (cb.isSelected()) {
                    Tag t = new Tag();
                    t.setNameValue(cb.getText());
                    selectedTags.add(t);  
                } 
            }
            temporaryTags.clear();
            popup.hide();
            pane.setVisible(false);
        });

        popup.show(tagButton.getScene().getWindow());
        pane.setVisible(true);

    }
    
    @FXML
    public void onSelectProfileImage(MouseEvent event) {
        editable();

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(searchBar.getScene().getWindow());
        profilePicture.setImage(new Image(selectedFile.toURI().toString()));
        profilePicture.setVisible(true);
    }
        // // ImagePathChecker pathChecker = new ImagePathChecker(); 
        
        // // FileChooser fileChooser = new FileChooser();
        
        // // FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image Files", ".png", ".jpg", ".jpeg", ".gif");
        // // fileChooser.getExtensionFilters().add(imageFilter);
        
        // // File selectedFile = fileChooser.showOpenDialog(null); 
        
        // // if (selectedFile != null) {
        // //     String filePath = selectedFile.getAbsolutePath();
        // //     if (pathChecker.check(filePath)) {
              
        // //         Image image = new Image(selectedFile.toURI().toString());
        // //         profilePicture.setImage(image); 
        // //         saveProfileImage(filePath);
                
        // //     } else {
        // //         Alert alert = new Alert(AlertType.ERROR);
        // //         alert.setTitle("An error occurred.");
        // //         alert.setHeaderText(null);
        // //         alert.setContentText("The selectedContact image is not valid.\nPath Selected: "+filePath);

        // //         alert.showAndWait(); 
        // //     }
        // // }
}