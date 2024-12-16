package it.unisa.diem.Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import it.unisa.diem.AddressBookApplication;
import it.unisa.diem.Model.AddressBook;
import it.unisa.diem.Model.Profile;
import it.unisa.diem.Utility.FileManager;
import it.unisa.diem.Utility.SceneManager;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;



/**
 * Controller class for the ProfileSelection view.
 * As a controller, it manages the interaction between the view and the model. Using a list of Profile objects from the model, it can:
 * - show the list of available profiles (up to 3)
 * - manipulate such profiles
 * - import new profiles from external sources (.vcf files)
 * - export profiles to external sources (.vcf files)
 * - open the AddressBook view for the selected profile
 */
public class ProfileSelectionController implements Initializable {

    public static final int MAX_PROFILES = 3;//< The maximum number of profiles that can be managed by the application */
    private ProfileList profileList;//< The list of profiles to manage */
    
    
    @FXML
    private Button saveProfileButton;

    @FXML
    private AnchorPane emptyListPromptPane;

    @FXML
    private Label createEditProfileLabel;

    @FXML
    private AnchorPane profileEditPane;

    @FXML
    private TextField editPhoneField;

    @FXML
    private TextField editNameField;
    
    @FXML
    private Pane noImagePane;

    @FXML
    private Button cancelProfileButton;

    @FXML
    private ImageView editProfilePicture;

    @FXML
    private ImageView exitButton;
    
    @FXML
    private AnchorPane profileSelectionPane;

    @FXML
    private HBox profileSelectionBox;

    @FXML
    private Button firstProfileButton;
    
    @FXML
    private StackPane stack;

    @FXML
    private HBox addButtonPane;

    public LayerPane lp;

    public ProfileMaker pm;

    private class ProfileList implements Serializable{
        
        //private static final long serialVersionUID = 5543605897692372336L;
        private transient ListProperty<Profile> profileList;

        public ProfileList() {
            this.profileList = createProfileList();  
        }

        public ListProperty<Profile> createProfileList(){
            return new SimpleListProperty<Profile>(FXCollections.observableArrayList(param -> new Observable[]{param.getName(), param.getPhone()}));
        } 

        public ListProperty<Profile> getProfileList(){
            return profileList;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            
            for(Profile p : profileList.get()){
                out.writeObject(p);
            }
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            
            profileList = createProfileList();
            try {
                while (true) {
                    profileList.add((Profile) in.readObject());
                }
            } catch (IOException e) {
                // End of stream reached
            }
        }
        
        public void export() {
            try {
                FileManager.exportToFile(FileManager.getProfileListPath(), this);
            } catch (StreamCorruptedException | ClassCastException e) {
                e.printStackTrace();
                System.exit(1); //! This should never happen
            }
        }
    }
    class LayerPane{
        private StackPane pane;

        public LayerPane(StackPane pane){
            this.pane=pane;
            pane.setVisible(true);
        }

        public void setPages(Node... pages){
            pane.getChildren().setAll(pages);
        }

        public void showPage(Node page){
            stack.getChildren().forEach(node -> node.setVisible(false));
            page.setVisible(true);
        }

        public boolean isVisible(Node node){
            boolean visible=false;
            for(Node n : pane.getChildren()){
                if(n.equals(node)){
                    return visible=n.isVisible();
                }
            }
            return visible;
        }
    }

    public static enum ProfileState {
        ADD,
        EDIT
    }

    private class ProfileMaker{

        public Pane pane;
        private ProfileState state;
        private Profile profile;
        private boolean hasImageChanged;
        private String URL;

        public ProfileMaker(Pane pane){
            this.pane=pane;
            URL="";
        }

        public void setState(ProfileState state){
            this.state=state;
        }

        public ProfileState getState(){
            return state;
        }

        public void setProfile(Profile profile){
            this.profile=profile;
        }

        public Profile getProfile(){
            return profile;
        }

        public void setImageChanged(boolean hasImageChanged){
            this.hasImageChanged=hasImageChanged;
        }

        public boolean hasImageChanged(){
            return hasImageChanged;
        }
        
        public String getURL() { 
            return URL;
        }

        public void setURL(String URL){
            this.URL=URL;
        }

    }
        
    class ProfileView{
        private Pane pane;
        private Profile profile;
        private ImageView profilePicture;
        private ImageView profileMenu;
        private Label nameLabel;
        private Label phoneLabel;
        private ContextMenu contextMenu;
        private VBox rect;

        public ProfileView(Profile profile){
            try {
                this.pane=((StackPane)AddressBookApplication.loadFXML("Profile"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.profile=profile;
            profilePicture=(ImageView)pane.lookup("#profilePicture");
            nameLabel=(Label)pane.lookup("#nameLabel");
            nameLabel.setMinWidth(nameLabel.getParent().minWidth(3));
            nameLabel.setMaxWidth(nameLabel.getParent().maxWidth(3));
            nameLabel.textProperty().addListener((ov, prevText, currText) -> {
                Platform.runLater(() -> {
                    Text text = new Text(currText);
                    text.setFont(nameLabel.getFont()); // Set the same font, so the size is the same
                    double width = text.getLayoutBounds().getWidth() // This big is the Text in the TextField
                            + nameLabel.getPadding().getLeft() + nameLabel.getPadding().getRight() // Add the padding of the TextField
                            + 2d; // Add some spacing
                    nameLabel.setPrefWidth(width); // Set the width

                    // Adjust font size to fit the parent width
                    double parentWidth = ((Region) nameLabel.getParent()).getWidth();
                    double fontSize = nameLabel.getFont().getSize();
                    while (width > parentWidth && fontSize > 0) {
                        fontSize -= 1;
                        text.setFont(new Font(nameLabel.getFont().getName(), fontSize));
                        width = text.getLayoutBounds().getWidth()
                                + nameLabel.getPadding().getLeft() + nameLabel.getPadding().getRight()
                                + 2d;
                    }
                    nameLabel.setFont(new Font(nameLabel.getFont().getName(), fontSize));
                });
            });

            phoneLabel=(Label)pane.lookup("#phoneLabel");
            phoneLabel.setMinWidth(Region.USE_PREF_SIZE);
            phoneLabel.setMaxWidth(Region.USE_PREF_SIZE);
            phoneLabel.textProperty().addListener((ov, prevText, currText) -> {
                Platform.runLater(() -> {
                    Text text = new Text(currText);
                    text.setFont(phoneLabel.getFont()); // Set the same font, so the size is the same
                    double width = text.getLayoutBounds().getWidth() // This big is the Text in the TextField
                            + phoneLabel.getPadding().getLeft() + phoneLabel.getPadding().getRight() // Add the padding of the TextField
                            + 2d; // Add some spacing
                    phoneLabel.setPrefWidth(width); // Set the width
                });
            });

            profileMenu=(ImageView)pane.lookup("#profileMenu");
            profileMenu.setImage(new Image(new File("addressbook\\assets\\view_resources\\dot-menu.png").toURI().toString()));
            profilePicture.setImage(new Image(new File(profile.getProfilePicture()).toURI().toString()));
            nameLabel.textProperty().bind(profile.getName());
            phoneLabel.textProperty().bind(profile.getPhone());
            contextMenu = new ContextMenu();
            contextMenu.getItems().addAll(
                new MenuItem("Edit"),
                new MenuItem("Delete")
            );
            contextMenu.getItems().get(0).setOnAction((e)->{onEdit(profile);});
            contextMenu.getItems().get(1).setOnAction((e)->{onDelete(profile);});
            profileMenu.setOnMouseClicked((e)->{
                contextMenu.show(profileMenu, e.getScreenX(), e.getScreenY());
            });

            rect=(VBox)pane.lookup("#rect");
            rect.setOnMouseClicked((e)->{
                toAddressBook(this.profile);
            });
        }

        public Pane getPane(){
            return pane;
        }

        public Profile getProfile(){
            return profile;
        }

        public void setProfile(Profile profile){
            this.profile=profile;
        }

        public void updateImage(){
            profilePicture.setImage(new Image(new File(profile.getProfilePicture()).toURI().toString()));
        }

        }

        private void showProfileList(){
            profileSelectionBox.getChildren().clear();
            if(profileList.getProfileList().size() == 0) {
                lp.showPage(emptyListPromptPane);
            } else {
                for(Profile p : profileList.getProfileList()){
                    Pane pane = new ProfileView(p).getPane();
                    HBox.setHgrow(pane, Priority.ALWAYS);
                    profileSelectionBox.getChildren().add(pane);
                }
                lp.showPage(profileSelectionPane);
            }
        }


        @Override
        public void initialize(URL location, ResourceBundle resources) {
        exitButton.setImage(new Image(new File("addressbook\\assets\\view_resources\\exit_button.png").toURI().toString()));
        exitButton.setOnMouseClicked((e)->{exit();});
        exitButton.setOnMouseEntered((e)->{startHover(e);});
        exitButton.setOnMouseExited((e)->{endHover(e);});

        ((ImageView)addButtonPane.getChildren().get(0)).setImage(new Image(new File("addressbook\\assets\\view_resources\\plus_button.png").toURI().toString()));

        noImagePane.setOnMouseClicked((e)->{
            chooseImage(e);
        });
        noImagePane.setOnMouseEntered((e)->{startHover(e);});
        noImagePane.setOnMouseExited((e)->{endHover(e);});

        editProfilePicture.setOnMouseClicked((e)->{
            chooseImage(e);
        });
        editProfilePicture.setOnMouseEntered((e)->{startHover(e);});
        editProfilePicture.setOnMouseExited((e)->{endHover(e);});


        addButtonPane.setDisable(profileList.getProfileList().size() >= MAX_PROFILES);
        profileList.getProfileList().addListener((observable, oldValue, newValue) -> {
            addButtonPane.setDisable(profileList.getProfileList().size() >= MAX_PROFILES);
        });
        addButtonPane.setVisible(true);
        lp=new LayerPane(stack);
        pm=new ProfileMaker(profileEditPane);
        lp.setPages(profileSelectionPane,profileEditPane,emptyListPromptPane);
        showProfileList();

        

        /* profileEditPane initialization */
        editNameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                saveProfileButton.setDisable(false);
            } else {
                saveProfileButton.setDisable(true);
            }
        });

        /* profileSelectionPane initialization */
        addButtonPane.setOnMouseClicked(event -> {
            onAdd(null);
        });
    }


    /**
     * Constructs a ProfileSelectionController loading the list of profiles from the application's internal storage.
     *
     */
    public ProfileSelectionController() {
        try {
            File profileListFile = new File(FileManager.getProfileListPath());
            ProfileList imported;
            if(!profileListFile.exists())
                FileManager.exportToFile(FileManager.getProfileListPath(), new ProfileList());
            if(profileListFile.length()==0)
                imported = new ProfileList();
            else
                imported = FileManager.importFromFile(FileManager.getProfileListPath());
            profileList = imported;
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Exits the application.
     * Called when the user clicks on the exit button on the window from the profile selection view.
     * 
     * @post The ProfileList is loaded to internal file, then the application is closed.
     * @see #ProfileList::export()
     */
    @FXML
    public void exit() {
        profileList.export();
        SceneManager.getABRunnable().run();
        Platform.exit();
    }

    /**
     * Opens the profile creation popup to increment the list of profiles, up to {@link #MAX_PROFILES}
     * Called when the user clicks on the profile creation button in the profile selection view.
     * If the maximum number of profiles is reached, the user is notified of the impossibility to add a new profile.
     * @post The new profile is added to the list of profiles or an error message is shown.
     * @see Profile
     */
    @FXML
    public void onAdd(ActionEvent event) {
        lp.showPage(profileEditPane);
        pm.setState(ProfileState.ADD);
        editNameField.clear();
        editPhoneField.clear();
        editProfilePicture.setVisible(false);
        noImagePane.setVisible(true);
        createEditProfileLabel.setText("Create new Profile");
    }

    /**
     * Prompts the deletion of the selected profile from the list of profiles.
     * Called when the user clicks on the profile deletion button in the profile selection view.
     * The user will be asked if they are sure to delete the selected profile.
     * @pre There must be at least one profile in the list of profiles for this method to be called.
     * @post The selected profile is removed from the list of profiles.
     * @see Profile
     */

    public void onDelete(Profile profile) {
        Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete profile");
        confirmationAlert.setHeaderText("Are you sure you want to delete the profile?");
        confirmationAlert.setContentText("This action cannot be undone.");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                profileList.getProfileList().remove(profile);
                showProfileList();
            }
        });
    }

    /**
     * Opens the profile edit popup to modify the selected profile.
     * Called when the user clicks on the profile edit button in the profile selection view.
        * @param profile the profile to edit 
        * @pre There must be at least one profile in the list of profiles for this method to be called.
        * @post The selected profile is modified with the new values inserted by the user in the view's text fields.

        */

    public void onEdit(Profile profile) { // Change create new profile in edit profile 
        lp.showPage(profileEditPane);
        pm.setState(ProfileState.EDIT);
        pm.setProfile(profile);
        editNameField.setText(profile.getName().get());
        editPhoneField.setText(profile.getPhone().get());
        if(profile.getProfilePicture().equals(FileManager.DEFAULT_PICTURE_PATH)){
            editProfilePicture.setVisible(false);
            noImagePane.setVisible(true);
        }
        else{
            editProfilePicture.setVisible(true);
            noImagePane.setVisible(false);
        }
        createEditProfileLabel.setText("Edit Profile");
    }
    // 1. Una volta imoostato una immagine non ti fa cambuare l'immagine ma ti mette in automatico la èrima che hai messo 
    // 2. Non modifica correttamente il numero di telefono 
    // 3. Provare a rimuovere un profilo e ricrearlo
    /**
     * Save the changes made to the list of profiles.
     * Called by {@link #onEdit(ActionEvent)} and {@link #onAdd(ActionEvent)} when the user clicks on the save button in the contact's edit view.
     *
     * @post The changes made to the list of Profile are saved.
     * @see FileManager#exportToFile(String, Object)
     */
    public void onSave() {
        Profile p = null;
        if(pm.getState()==ProfileState.ADD){
            p=new Profile();
            try {
                String path=Files.createFile(Paths.get(FileManager.generateAddressBookPath())).toAbsolutePath().toString();
                new AddressBook().writeToFile(path);
                p.setAddressBookPath(path);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1); //! This should never happen
            } 
        }
        else if (pm.getState()==ProfileState.EDIT){
            p=pm.getProfile();
        }
        if(pm.hasImageChanged()){
            try {
                p.setProfilePicture(pm.getURL());
            } catch (IOException e) {
                Alert confirmationAlert = new Alert(AlertType.ERROR);
                if(e instanceof FileNotFoundException){
                    confirmationAlert.setTitle("File not found.");
                    confirmationAlert.setContentText("The specified file does not exist. Use another file or try again.");
                }
                else{
                    confirmationAlert.setTitle("Error during image loading");
                    confirmationAlert.setContentText("An error occurred while trying to load the image. Try again.");
                }
                confirmationAlert.setHeaderText(null);
                confirmationAlert.showAndWait();
                e.printStackTrace();
                return;
            }
        }
        if(!p.setName(editNameField.getText())){
            Alert confirmationAlert = new Alert(AlertType.ERROR);
            confirmationAlert.setTitle("Wrong name format");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Error: the name must be at most 50 character long.");
            confirmationAlert.showAndWait();
            return;
        }
        if(!p.setPhone(editPhoneField.getText())){
            Alert confirmationAlert = new Alert(AlertType.ERROR);
            confirmationAlert.setTitle("Wrong phone format");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Error: the inserted phone number is not a valid italian phone number.");
            confirmationAlert.showAndWait();
            return;
        }
        
        if(pm.getState()==ProfileState.ADD){
            profileList.getProfileList().add(p);
        }

        pm.setImageChanged(false);
        showProfileList();
    }
    
    /**
    * Goes to the address book view for the selected profile.
    * Called when the user clicks on the back button in the main view.
    * @param[in] profile the profile to load the address book for
    * 
    * @post The address book is saved to the application's assets and the user is taken back to the profile selection view.
    * @see SceneManager#loadAddressBook()
    */
    @FXML
    public void toAddressBook(Profile profile) {
        SceneManager.setPSRunnable(()->{profileList.export();});
        SceneManager.setAddressBook(profile.getAddressBookPath());
        try {
            AddressBookApplication.setRoot("AddressBook");
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1); //! This should never happen
        }
    }

    @FXML
    void chooseImage(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
        File selectedFile = fileChooser.showOpenDialog(noImagePane.getScene().getWindow());
        pm.setURL(selectedFile.getAbsolutePath());
        editProfilePicture.setImage(new Image(selectedFile.toURI().toString()));
        editProfilePicture.setVisible(true);
        noImagePane.setVisible(false); 
        pm.setImageChanged(true);
    }
    
    @FXML
    void onCancelEdit(ActionEvent event) {
        showProfileList();
    }

    @FXML
    void startHover(MouseEvent event) {
        ((Node)event.getSource()).setStyle("-fx-background-color:rgb(224, 167, 69);");
    }

    @FXML
    void endHover(MouseEvent event) {
        ((Node)event.getSource()).setStyle("-fx-background-color: transparent;");
    }
}