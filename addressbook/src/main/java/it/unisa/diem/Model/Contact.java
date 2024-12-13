package it.unisa.diem.Model;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.TreeSet;

import ezvcard.VCard;
import ezvcard.parameter.ImageType;
import ezvcard.property.Categories;
import ezvcard.property.Email;
import ezvcard.property.FormattedName;
import ezvcard.property.Photo;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import it.unisa.diem.Model.Interfaces.Taggable;
import it.unisa.diem.Model.Interfaces.Checker.ItalianPhoneChecker;
import it.unisa.diem.Utility.FileManager;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

/**
 * A class representing a contact in the address book.
 * 
 * A contact is defined by a name, a surname, a list of email addresses and a list of phone numbers.
 * It can also be marked with an arbitrary number of tags, which are used to categorize contacts, and can show a profile picture.
 * It is created to grant full compatibility with a JavaFX UI
 * 
 * @invariant name!=null
 * @invariant surname!=null
 * @invariant email!=null
 * @invariant phoneNumber!=null
 * @invariant tags!=null
 * @invariant fullName!=null
 */

public class Contact implements Comparable<Contact>, Serializable, Taggable {
    public static final int MAX_EMAILS = 3; /**< The maximum number of emails that can be associated with a contact */
    public static final int MAX_PHONENUMBERS = 3; /**< The maximum number of phone numbers that can be associated with a contact */
    
    private transient StringProperty name; /** The given name(s) of the person to be associated with the contact */
    private transient StringProperty surname; /** The family name(s) of the person to be associated with the contact */
    private transient StringProperty fullName; /** The full name of the person to be associated with the contact (in the form "surname name")*/
    private String[] email; /** The email addresses of the person to be associated with the contact */
    private String[] phoneNumber; /** The phone numbers of the person to be associated with the contact */
    private transient SetProperty<Tag> tags; /** The tags associated with the contact */
    private String picture; /** The internal path of the picture associated with the contact */


    /**
     * Creates a new Contact with default values.
     */
    public Contact() {
        name = new SimpleStringProperty();
        surname = new SimpleStringProperty();
        fullName=new SimpleStringProperty();
        fullName.bind(Bindings.concat(surname," ", name));
        picture = "";     
        email = new String[MAX_EMAILS];
        for (int i = 0; i < MAX_EMAILS; i++) {
            email[i] = "";
        }

        phoneNumber = new String[MAX_PHONENUMBERS];
        for (int i = 0; i < MAX_PHONENUMBERS; i++) {
            phoneNumber[i] = "";
        }
        tags = new SimpleSetProperty<Tag>(FXCollections.observableSet(new TreeSet<Tag>()));
    }

    /**
     * Creates a new Contact with the given name and surname.
     * 
     * @param[in] name the name of the new Contact
     * @param[in] surname the surname of the new Contact
     */
    public Contact(String name, String surname) {
        this();
        setName(name);
        setSurname(surname);
    }

    private int size(String[] s){
        int i=0;
        for(String e:s){
            if(e.isEmpty())
                break;
            i++;
        }
        return i;
    }

    /**
     * Sets the name of the Contact to the given value.
     * 
     * @invariant name != null
     * @pre !name.isEmpty()
     * @post getNameValue().equals(name)
     * @param[in] name the new name of the contact
     * @return true (allowing for possible constrains to this class' paths' version of the method)
     */
    public boolean setName(String name) {
        this.name.set(name);
        return true;
    }


    /**
     * Returns the name of the Contact as a StringProperty.
     * 
     * @invariant name!=null
     * @return the name of the Contact
     */
    public StringProperty getName() {
        return name;
    }

    /**
     * Returns the name of the Contact as a String.
     * 
     * @pre getNameValue().equals(getName().get())
     * @invariant name!=null
     * @return
     */
    public String getNameValue() {
        return name.get();
    }

    /**
     * Sets the surname of the Contact to the given value.
     * 
     * @invariant surname != null
     * @pre !surname.isEmpty()
     * @post getSurnameValue().equals(surname)
     * @param[in] surname the new surname of the contact
     * @return true (allowing for possible constrains to this class' paths' version of the method)
     */
    public boolean setSurname(String surname) {
        this.surname.set(surname);
        return true;
    }

    /**
     * Returns the surname of the Contact as a StringProperty.
     * 
     * @invariant surname!=null
     * @return the surname of the Contact
     */
    public StringProperty getSurname() {
        return surname;
    }

    /**
     * Returns the surname of the Contact as a String.
     * 
     * @pre getSurnameValue().equals(getSurname().get())
     * @invariant surname!=null
     * @return the surname of the Contact
     */
    public String getSurnameValue() {
        return surname.get();
    }

    /**
     * Takes the picture at the specified path and copies it in the application's assets folder, then assigns it to the contact as a profile picture.
     * 
     * @param[in] picture the path of the picture to assign to the contact
     * @throws IOException if the picture cannot be copied in the assets folder, or the specified path is not valid
     * @return true (allowing for possible constrains to this class' paths' version of the method)
     */
    public boolean setPicture(String picture) {
        Path profilePicturePath = Paths.get(picture);
        if(!Files.exists(profilePicturePath)) {
            throw new FileNotFoundException("File: " + picture + " does not exist.");
        }
        
        String fileName = profilePicturePath.getFileName().toString();
        String extension= fileName.substring(fileName.lastIndexOf('.')+1);
        Path destinationPath = Paths.get(FileManager.generateContactPicturePath(extension));
        this.picture = Files
        .copy(profilePicturePath, destinationPath, StandardCopyOption.REPLACE_EXISTING).toString();
        return True
    }
    /**
     * Returns the internal path of the picture of the Contact.
     * 
     * @invariant picture!=null
     * @return internal relative path to the picture of the Contact
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Returns the list of the email addresses associated to the Contact.
     * 
     * @invariant email!=null
     * @return the list of the email addresses associated to the Contact
     */
    public String[] getEmailList() {
        return email;
    }

    /**
     * Returns the email address at the specified index.
     * 
     * @pre 0 <= index < size(email)
     * @param[in] index the index of the email address to return
     * @return the email address at the specified index
     */
    public String getEmailAtIndex(int index) {
        return email[index];
    }

    /**
     * Returns the list of phone numbers associated to the Contact.
     * 
     * @invariant phoneNumber!=null
     * @return the list of phone numbers associated to the Contact
     */
    public String[] getPhoneNumberList() {
        return phoneNumber;
    }

    /**
     * Returns the phone number at the specified index.
     * 
     * @pre 0 <= index < size(phoneNumber)
     * @param[in] index the index of the phone number to return
     * @return the phone number at the specified index
     */
    public String getPhoneNumberAtIndex(int index) {
        return phoneNumber[index];
    }

    /**
     * Adds the given email addresses to the contact if there is enough space.
     * The array passed as an argument must have no null elements, and from the first empty string (included), every other string will be ignored.
     * 
     * @invariant email != null
     * @pre size(email) > 0
     * @pre size(email) <= {@link Contact#MAX_EMAILS}
     * @post getEmailList().containsAll(email)
     * @post getEmailList().size() == old.getEmailList().size() + size(email)
     * @post getEmailList().containsAll(old.getEmailList())
     * @param[in] email the email addresses to add to the contact
     * @return true if the email addresses have been added (meaning there is enough space for all of them)
     */
    public boolean addEmail(String... email) {
        if(MAX_EMAILS-size(this.email)<size(email))
            return false;
        for (String mail : email) {
            this.email[size(this.email)]=mail;
        }
        return true;
    }

    /**
     * Sets the email address at the specified index to the given value, if it is not an empty String (in that case, use {@link #removeEmailAtIndex}).
     * 
     * @invariant email != null
     * @pre size(email) > 0
     * @pre index >= 0
     * @pre index < getEmailList().size()
     * @post getEmailAtIndex(index).equals(email)
     * @param[in] email the new email address
     * @param[in] index the index of the email address to set
     * @return true if the email address has been set (hence the argument String is not empty), false otherwise
     */
    public boolean setEmail(String email, int index)  {
        if(email.isEmpty())
            return false;
        this.email[index]=email;
        return true;
    }

    /**
     * Removes the email address at the specified index if it is not an empty string.
     * The removed email will be replaced with an empty String and possible holes in the array will be filled with shifting of the other emails.
     * 
     * @pre index >= 0
     * @pre index < getEmailList().size()
     * @post getEmailList().size() == getEmailList().size()@pre - 1
     * @post !getEmailList().contains(getEmailAtIndex(index)@pre)
     * @param[in] index the index of the email address to remove
     * @return true if the email address exists (and has been removed), meaning is not an empty String
     */    
    public boolean removeEmailAtIndex(int index) {
        String result=email[index];
        if(!result.isEmpty()){
            for(int i=index;i<MAX_EMAILS-1;i++){
                email[i]=email[i+1];
            }
            email[MAX_EMAILS-1]="";
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Adds the given phone numbers to the contact.
     * The array passed as an argument must have no null elements, and from the first empty string (included), every other string will be ignored.
     * 
     * @invariant phoneNumber != null
     * @pre size(phoneNumber) > 0
     * @post getPhoneNumberList().containsAll(phoneNumber)
     * @post getPhoneNumberList().size() == old.getPhoneNumberList().size() + size(phoneNumber)
     * @post getPhoneNumberList().containsAll(old.getPhoneNumberList())
     * @param[in] phoneNumber the phone numbers to add to the contact
     * @return true if the phone numbers have been added, false otherwise
     */
    public boolean addPhoneNumber(String... phoneNumber) {
        if(MAX_PHONENUMBERS-size(this.phoneNumber)<size(phoneNumber))
            return false;
        for (String phone : phoneNumber) {
            this.phoneNumber[size(this.phoneNumber)]=phone;
        }
        return true;
    }


    /**
     * Sets the phone number at the specified index to the given value, if it is not an empty String (in that case, use {@link #removePhoneNumberAtIndex}).
     * 
     * @pre phoneNumber 
     * @pre size(phoneNumber) > 0
     * @pre index >= 0
     * @pre index < getPhoneNumberList().size()
     * @post getPhoneNumberAtIndex(index).equals(phoneNumber)
     * @param[in] phoneNumber the new phone number
     * @param[in] index the index of the phone number to set
     * @return true (allowing for possible constrains to this class' paths' version of the method)
     */
    public boolean setPhoneNumber(String phoneNumber, int index) {
        if(phoneNumber.isEmpty())
            return false;
        this.phoneNumber[index]=phoneNumber;
        return true;
    }

    /**
     * Removes the phone number at the specified index, given that it exists and the index is valid.
     * 
     * @pre index >= 0
     * @pre index < getPhoneNumberList().size()
     * @post getPhoneNumberList().size() == old.getPhoneNumberList().size() - 1
     * @post !getPhoneNumberList().contains(old.getPhoneNumberAtIndex(index))
     * @param[in] index the index of the phone number to remove
     * @return true if the phone number has been removed, false otherwise
     */
    public boolean removePhoneNumberAtIndex(int index) {
        String result=phoneNumber[index];
        if(!result.isEmpty()){
            for(int i=index;i<MAX_PHONENUMBERS-1;i++){
                phoneNumber[i]=phoneNumber[i+1];
            }
            phoneNumber[MAX_PHONENUMBERS-1]="";
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Adds the given tag to the contact, given that there's not another Tag with the same name.
     * 
     * @invariant tag != null
     * @post getTagsSet().contains(tag)
     * @post getTagsSet().size() == old.getTagsSet().size() + 1
     * @param[in] tag the {@link Tag} to add
     * @return true if the tag has been added (hence there was no Tag with the same name), false otherwise
     */
    public boolean addTag(String string) {
        Tag t=new Tag();
        if(!t.setName(string)){return false;}
        return this.tags.add(t);
    }

    
    /**
     * Removes the given tag from the contact, given that it is present.
     * 
     * @pre getTagsSet().contains(tag)
     * @post getTagsSet().size() == old.getTagsSet().size() - 1
     * @post !getTagsSet().contains(tag)
     * @param[in] tag the {@link Tag} to remove
     * @return true if the tag has been removed (hence it was present), false otherwise
     */
    public boolean removeTag(String string) {
        return this.tags.remove(string);
    }

    /**
     * Returns the maximum number of email addresses that can be associated with a contact.
     * @return the maximum number of email addresses that can be associated with a contact
     * @see #MAX_EMAILS
     */
    public static int getMaxEmails() {
        return MAX_EMAILS;
    }

    /**
     * Returns the maximum number of phone numbers that can be associated with a contact.
     * @return the maximum number of phone numbers that can be associated with a contact
     * @see #MAX_PHONENUMBERS
     */
    public static int getMaxPhonenumbers() {
        return MAX_PHONENUMBERS;
    }

    /**
     * Returns the set of tags associated with the contact.
     * @return the set of tags associated with the contact
     */
    public SetProperty<Tag> getTags() {
        return tags;
    }

    /**
     * Returns the full name of the Contact (in the form "surname name") as a StringProperty.
     * 
     * @invariant fullName!=null
     * @return the full name of the Contact, as a StringProperty
     */
    public StringProperty getFullName() {
        return fullName;
    }

    /**
     * Returns the full name of the Contact (in the form "surname name") as a String.
     * 
     * @pre getFullNameValue().equals(getFullName().get())
     * @invariant fullName!=null
     * @return the full name of the Contact, as a String
     */
    public String getFullNameValue() {
        return this.fullName.get();
    }

    /**
     * Checks wether the Contact contains the substring passed as argument in its full name.
     * @param str
     * @return true if the Contact contains the substring passed as argument in its full name
     *         false otherwise
     * @see #getFullNameValue()
     */
    public boolean containsName(String str) {
        return getFullNameValue().contains(str);
    }

    /**
     * Checks wether the Contact contains the substring passed as argument in at least one of the tags with which it is marked.
     * @param str
     * @return true if the Contact contains the substring passed as argument in at least one of the tags with which it is marked
     *         false otherwise
     */
    public boolean containsTag(String str) {
        for (Tag t : tags)
            if (t.getNameValue().contains(str)) 
                return true;
        return false;
    }

    /**
     * Compares this Contact with the specified Contact for order.
     * The contacts are ordered alfabetically by their {@link #fullName} (of the form "surname name"), but they will never be considered equal.
     * This way, omonyms are permitted in sets of Contacts.
     * 
     * @param other the Contact to be compared
     * @return a negative integer, zero, or a positive integer as this Contact is less than, equal to, or greater than the specified Contact
     */
    @Override
    public int compareTo(Contact other) {
        int result=getFullNameValue().compareTo(other.getFullNameValue());
        if(result==0){
            return -1; //This way, even if two contacts have the same name, they are considered different.
        }
        return result;
    }

    /**
     * Returns false, allowing for omonym Contacts to be considered different.
     */
    @Override
    public boolean equals(Object obj) {
        return false;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(name.get());
        out.writeUTF(surname.get());
        out.writeUTF(fullName.get());
        for (Tag tag : tags) {
            out.writeUTF(tag.getNameValue());
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        name = new SimpleStringProperty(in.readUTF());
        surname = new SimpleStringProperty(in.readUTF());
        fullName = new SimpleStringProperty(in.readUTF());
        fullName.bind(Bindings.concat(surname, " ", name));
        tags = new SimpleSetProperty<>(FXCollections.observableSet(new TreeSet<>()));
        Tag t;
        while (in.available() > 0) {
            t=new Tag();
            t.setName(in.readUTF());
            tags.add(t);
        }
    }

    public VCard toVCard() {
        VCard vCard = new VCard();

        StructuredName sn = new StructuredName();
        // Add name and surname
        if (name.get().isEmpty()) {
            sn.setFamily(surname.get());
            vCard.setStructuredName(sn);
        } else if (surname.get().isEmpty()) {
            sn.setGiven(name.get());
            vCard.setStructuredName(sn);
        } else{
            sn.setFamily(surname.get());
            sn.setGiven(name.get());
            vCard.setStructuredName(sn);
        }

        // Add email addresses
        for (String emailAddress : email)
            vCard.addEmail(new Email(emailAddress));

        // Add phone numbers
        for (String phone : phoneNumber)
            vCard.addTelephoneNumber(new Telephone(phone));

        // Add tags
        Categories categories = new Categories();
        for (Tag tag : tags)
            categories.getValues().add(tag.getNameValue());
        vCard.addCategories(categories);

        if(!picture.isEmpty()) {
            // Aggiungi la foto, se disponibile
            ImageType imageType;
            String path = picture.substring(picture.lastIndexOf('.') + 1);
            switch (path) {
                case "jpg":
                case "jpeg":
                    imageType = ImageType.JPEG;
                    break;
                case "png":
                    imageType = ImageType.PNG;
                    break;
                case "gif":
                    imageType = ImageType.GIF;
                    break;
                default:
                    imageType = ImageType.JPEG;
                    break;
            }
            vCard.addPhoto(new Photo(picture, imageType));
        }
        return vCard;
    }

    public static Contact fromVCard(VCard vCard) throws StreamCorruptedException{
        Contact result=new Contact();
        StructuredName sn = vCard.getStructuredName();
        String vName=sn.getGiven();
        String vSurname=sn.getFamily();
        if (vName.isEmpty()&&vSurname.isEmpty()) {throw new StreamCorruptedException("Invalid VCard format: name and surname are both empty");}
        if (!vName.isEmpty())
            result.name.set(sn.getGiven());
        if (!vSurname.isEmpty())
            result.surname.set(sn.getFamily());

        // Add email addresses
        int i = 0;
        for (Email email : vCard.getEmails()) {
            if (i < MAX_EMAILS)
                result.email[i++] = email.getValue();
        }

        // Add phone numbers
        i = 0;
        for (Telephone phone : vCard.getTelephoneNumbers()) {
            if (i < MAX_PHONENUMBERS)
                result.phoneNumber[i++] = phone.getText();
        }

        // Add tags
        Categories categories = vCard.getCategories();
        if (categories != null) {
            for (String tag : categories.getValues()) {
                Tag t = new Tag();
                t.setName(tag);
                result.tags.add(t);
            }
        }

        // Add picture
        List<Photo> photos = vCard.getPhotos();
        if (photos != null && !photos.isEmpty()) {
            String path;
            Photo photo = photos.get(0);
            ImageType type=photo.getContentType();
            if (type == ImageType.JPEG) {
                path = FileManager.generateProfilePicturePath("jpg");
            } else if (type == ImageType.PNG) {
                path = FileManager.generateProfilePicturePath("png");
            } else if (type == ImageType.GIF) {
                path = FileManager.generateProfilePicturePath("gif");
            } else {
                path = FileManager.generateProfilePicturePath("jpg");
            }
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(path))) {
                dos.write(photo.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }
            result.picture = path;
        }   
        return result;
    }
}
