package it.unisa.diem.Model;

import java.io.IOException;
import java.io.Serializable;

import it.unisa.diem.Model.Interfaces.Checker.CharacterLimitStringChecker;
import it.unisa.diem.Model.Interfaces.Checker.ItalianPhoneChecker;
import it.unisa.diem.Utility.FileManager;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a profile in the application.
 * 
 * A profile is characterized by a name, a phone number, a profile picture and an address book. Only the name field is mandatory.
 * The name is subject to a maximum length of {@value #MAX_NAMELEN} characters.
 * The phone number is subject to the constraints of {@link ItalianPhoneChecker}.
 * It is created to grant full compatibility with a JavaFX UI
 * 
 * @invariant name!=null
 * @invariant phone!=null
 * @invariant profilePicture!=null
 * @invariant addressBookPath!=null
 */
public class Profile implements Serializable{
    public static final int MAX_NAMELEN = 50; /**< The maximum length of the name */

    private transient StringProperty name; /**< The name of the profile */
    private transient StringProperty phone; /**< The phone number associated to the profile */
    private String profilePicture; /**< The path to the profile picture */
    private String addressBookPath; /**< The internal assets path to the address book associated to the profile */

    /**
     * Creates a new Profile with the given name, phone number, profile picture and address book path.
     * 
     * @param name
     * @param phone
     * @param profilePicture
     * @param addressBookPath
     */
    public Profile(StringProperty name, StringProperty phone, StringProperty profilePicture, StringProperty addressBookPath) throws IOException {
        // Constructor implementation
        this.name = name; 
        this.phone = phone; 
        setProfilePicture(profilePicture.get()); 
        setAddressBookPath(addressBookPath.get()); 
        
    }

    /**
     * Returns the name of the profile.
     * 
     * @pre !name.isEmpty()
     * @post getName().equals(name)
     * @param[in] name the new name of the contact
     * @return the name of the profile
     */
    public StringProperty getName() {
        return name;
    }

    /**
     * Sets the name of the profile, given that it satisfies the satisfies the condition of {@link CharacterLimitStringChecker} (character limit is set to {@value #MAX_NAMELEN}).
     * 
     * @param name the new name of the profile
     * @return true if the name is valid
     *         false otherwise
     */
    public boolean setName(String name) {
        CharacterLimitStringChecker checker = new CharacterLimitStringChecker(MAX_NAMELEN); 
        if(checker.check(name)) {
            this.name = new SimpleStringProperty(name); 
            return true; 
        }
        return false; 
    }

    /**
     * Returns the phone number of the profile.
     * 
     * @pre !phone.isEmpty()
     * @post getPhone().equals(phone)
     * @param[in] phone the new phone number of the profile
     * @return the phone number of the profile
     */
    public StringProperty getPhone() {
        return phone; 
    }

    /**
     * Sets the phone number of the profile, given that it satisfies the satisfies the condition of {@link ItalianPhoneChecker}.
     * 
     * @param phone the new phone number of the profile
     * @return true if the phone number is valid
     *         false otherwise
     */
    public boolean setPhone(String phone) {
        ItalianPhoneChecker checker = new ItalianPhoneChecker(); 
        if(checker.check(phone)) {
            this.phone = new SimpleStringProperty(phone); 
            return true; 
        }
        return false; 
    }

    /**
     * Returns the internal path of the picture of the Contact.
     * 
     * @return internal relative path to the picture of the Contact
     */
    public String getProfilePicture() {
        return profilePicture;
    }

    /**
     * Returns the internal assets path of the address book associated to the profile.
     * @return the internal assets path of the address book associated to the profile
     */
    public String getAddressBookPath() {
        return addressBookPath;
    }


    /**
     * Sets the internal assets path of the address book associated to the profile.
     * 
     * @param addressBookPath the internal assets path of the address book associated to the profile
     * @throws IOException if the specified path is not valid
     */
    public void setAddressBookPath(String addressBookPath) throws IOException {
        Path path = Paths.get(addressBookPath);
        if(!Files.exists(path)) {
            throw new IOException("File: " + addressBookPath + " does not exist.");
        }
        this.addressBookPath = addressBookPath;
    }

    /**
     * Takes the picture at the specified path and copies it in the application's assets folder, then assigns it as the profile picture.
     * 
     * @param[in] profilePicture the path of the picture to assign to the contact
     * @throws FileNotFoundException if the picture cannot be copied in the assets folder, or the specified path is not valid
     */
    public void setProfilePicture(String profilePicture) throws FileNotFoundException {
        Path profilePicturePath = Paths.get(profilePicture);
        if(!Files.exists(profilePicturePath)) {
            throw new FileNotFoundException("File: " + profilePicture + " does not exist.");
        }
        
        String fileName = profilePicturePath.getFileName().toString();
        String extension = fileName.substring(fileName.lastIndexOf('.')+1); 
        Path destinationPath = Paths.get(FileManager.generateProfilePicturePath(extension)); 
        this.profilePicture = Files.copy(profilePicturePath, destinationPath, StandardCopyOption.REPLACE_EXISTING).toString(); 
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(name.get());
        out.writeObject(phone.get());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        name = new SimpleStringProperty((String) in.readObject());
        phone = new SimpleStringProperty((String) in.readObject());
    }
}