package it.unisa.diem.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import it.unisa.diem.Model.Interfaces.Taggable;
import it.unisa.diem.Model.Interfaces.Checker.CharacterLimitStringChecker;
import it.unisa.diem.Model.Interfaces.Checker.ItalianPhoneChecker;
import it.unisa.diem.Model.Interfaces.Checker.SimpleEmailChecker;
import javafx.beans.property.SetProperty;
import javafx.beans.property.StringProperty;

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
    public static final int MAX_EMAILS = 3;
    public static final int MAX_PHONENUMBERS = 3;
    
    private transient StringProperty name;
    private transient StringProperty surname;
    private transient StringProperty fullName;
    private String[] email;
    private String[] phoneNumber;
    private transient SetProperty<Tag> tags;
    private String picture;

    /**
     * Creates a new Contact with default values.
     */
    public Contact() {
        // TODO: Implement this method
    }

    /**
     * Creates a new Contact with the given name and surname.
     * 
     * @param[in] name the name of the new Contact
     * @param[in] surname the surname of the new Contact
     */
    public Contact(String name, String surname) {
        // TODO: Implement this method
    }

    /**
     * Creates a new Contact with the given name, surname, phone numbers and email addresses.
     * 
     * @param[in] name the name of the new Contact
     * @param[in] surname the surname of the new Contact
     * @param[in] phoneNumber the phone numbers of the new Contact
     * @param[in] email the email addresses of the new Contact
     */
    public Contact(String name, String surname, String[] phoneNumber, String[] email) {
        // TODO: Implement this method
    }

    /**
     * Sets the name of the Contact to the given value.
     * 
     * @invariant name != null
     * @pre !name.isEmpty()
     * @post getNameValue().equals(name)
     * @param[in] name the new name of the contact
     * @return true (allowing for possible constrains to this class' extensions' version of the method)
     */
    public boolean setName(String name) {
        // TODO: Implement this method
        return false;
    }


    /**
     * Returns the name of the Contact as a StringProperty.
     * 
     * @invariant name!=null
     * @return the name of the Contact
     */
    public StringProperty getName() {
        // TODO: Implement this method
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
        // TODO: Implement this method
        return nameValue;
    }

    /**
     * Sets the surname of the Contact to the given value.
     * 
     * @invariant surname != null
     * @pre !surname.isEmpty()
     * @post getSurnameValue().equals(surname)
     * @param[in] surname the new surname of the contact
     * @return true (allowing for possible constrains to this class' extensions' version of the method)
     */
    public boolean setSurname(String surname) {
        // TODO: Implement this method
    }

    /**
     * Returns the surname of the Contact as a StringProperty.
     * 
     * @invariant surname!=null
     * @return the surname of the Contact
     */
    public StringProperty getSurname() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Returns the surname of the Contact as a String.
     * 
     * @pre getSurnameValue().equals(getSurname().get())
     * @invariant surname!=null
     * @return the surname of the Contact
     */
    public String getSurnameValue() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Takes the picture at the specified path and copies it in the application's assets folder, then assigns it to the contact as a profile picture.
     * 
     * @param[in] picture the path of the picture to assign to the contact
     * @throws IOException if the picture cannot be copied in the assets folder, or the specified path is not valid
     */
    public void setPicture(String picture) throws IOException {
        // TODO: Implement this method
    }

    /**
     * Returns the internal path of the picture of the Contact.
     * 
     * @invariant picture!=null
     * @return internal relative path to the picture of the Contact
     */
    public String getPicture() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Returns the list of the email addresses associated to the Contact.
     * 
     * @invariant email!=null
     * @return the list of the email addresses associated to the Contact
     */
    public String[] getEmailList() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Returns the email address at the specified index.
     * 
     * @pre 0 <= index < email.length
     * @param[in] index the index of the email address to return
     * @return the email address at the specified index
     */
    public String getEmailAtIndex(int index) {
        // TODO: Implement this method
        return null;
    }

    /**
     * Returns the list of phone numbers associated to the Contact.
     * 
     * @invariant phoneNumber!=null
     * @return the list of phone numbers associated to the Contact
     */
    public String[] getPhoneNumberList() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Returns the phone number at the specified index.
     * 
     * @pre 0 <= index < phoneNumber.length
     * @param[in] index the index of the phone number to return
     * @return the phone number at the specified index
     */
    public String getPhoneNumberAtIndex(int index) {
        // TODO: Implement this method
        return null;
    }

/**
     * Adds the given email addresses to the contact.
     * 
     * @invariant email != null
     * @pre email.length > 0
     * @pre email.length <= {@link Contact#MAX_EMAILS}
     * @post getEmailList().containsAll(email)
     * @post getEmailList().size() == old.getEmailList().size() + email.length
     * @post getEmailList().containsAll(old.getEmailList())
     * @param[in] email the email addresses to add to the contact
     * @return true if the email addresses have been added, false otherwise
     */
    public boolean addEmail(String... email) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Sets the email address at the specified index to the given value.
     * 
     * @invariant email != null
     * @pre email.length > 0
     * @pre index >= 0
     * @pre index < getEmailList().size()
     * @post getEmailAtIndex(index).equals(email)
     * @param[in] email the new email address
     * @param[in] index the index of the email address to set
     * @return true if the email address has been set, false otherwise
     */
    public boolean setEmail(String email, int index)  {
        // TODO: Implement this method
        return false;
    }

    /**
     * Removes the email address at the specified index, given that it exists and the index is valid.
     * 
     * @pre index >= 0
     * @pre index < getEmailList().size()
     * @post getEmailList().size() == old.getEmailList().size() - 1
     * @post !getEmailList().contains(old.getEmailAtIndex(index))
     * @param[in] index the index of the email address to remove
     * @return true if the email address has been removed, false otherwise
     */    
    public boolean removeEmailAtIndex(int index) {
        // TODO: Implement this method
    }

    /**
     * Adds the given phone numbers to the contact.
     * 
     * @invariant phoneNumber != null
     * @pre phoneNumber.length > 0
     * @post getPhoneNumberList().containsAll(phoneNumber)
     * @post getPhoneNumberList().size() == old.getPhoneNumberList().size() + phoneNumber.length
     * @post getPhoneNumberList().containsAll(old.getPhoneNumberList())
     * @param[in] phoneNumber the phone numbers to add to the contact
     * @return true if the phone numbers have been added, false otherwise
     */
    public boolean addPhoneNumber(String... phoneNumber) {
        // TODO: Implement this method
        return false;
    }


    /**
     * Sets the phone number at the specified index to the given value, but only if it verifies the condition of {@link ItalianPhoneChecker}.
     * 
     * @pre phoneNumber 
     * @pre phoneNumber.length > 0
     * @pre index >= 0
     * @pre index < getPhoneNumberList().size()
     * @post getPhoneNumberAtIndex(index).equals(phoneNumber)
     * @param[in] phoneNumber the new phone number
     * @param[in] index the index of the phone number to set
     * @return true if the phone number has been set, false otherwise
     */
    public boolean setPhoneNumber(String phoneNumber, int index) {
        // TODO: Implement this method
        return false;
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
        // TODO: Implement this method
    }

    /**
     * Adds the given tag to the contact.
     * 
     * @invariant tag != null
     * @post getTagsSet().contains(tag)
     * @post getTagsSet().size() == old.getTagsSet().size() + 1
     * @param[in] tag the {@link Tag} to add
     * @return true if the tag has been added, false otherwise
     */
    public boolean addTag(Tag tag) {
        // TODO: Implement this method
        return false;
    }

    
    /**
     * Removes the given tag from the contact, given that it is present.
     * 
     * @pre getTagsSet().contains(tag)
     * @post getTagsSet().size() == old.getTagsSet().size() - 1
     * @post !getTagsSet().contains(tag)
     * @param[in] tag the {@link Tag} to remove
     * @return true if the tag has been removed, false otherwise
     */
    public boolean removeTag(Tag tag) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Returns the maximum number of email addresses that can be associated with a contact.
     * @return the maximum number of email addresses that can be associated with a contact
     * @see #MAX_EMAILS
     */
    public static int getMaxEmails() {
        // TODO: Implement this method
        return 0;
    }

    /**
     * Returns the maximum number of phone numbers that can be associated with a contact.
     * @return the maximum number of phone numbers that can be associated with a contact
     * @see #MAX_PHONENUMBERS
     */
    public static int getMaxPhonenumbers() {
        // TODO: Implement this method
        return 0;
    }

    /**
     * Returns the set of tags associated with the contact.
     * @return the set of tags associated with the contact
     */
    public SetProperty<Tag> getTags() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Returns the full name of the Contact (in the form "surname name") as a StringProperty.
     * 
     * @invariant fullName!=null
     * @return the full name of the Contact, as a StringProperty
     */
    public StringProperty getFullName() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Returns the full name of the Contact (in the form "surname name") as a String.
     * 
     * @pre getFullNameValue().equals(getFullName().get())
     * @invariant fullName!=null
     * @return the full name of the Contact, as a String
     */
    public String getFullNameValue() {
        // TODO: Implement this method
        return null;
    }

    /**
     * Checks wether the Contact contains the substring passed as argument in its full name.
     * @param str
     * @return true if the Contact contains the substring passed as argument in its full name
     *         false otherwise
     * @see #getFullNameValue()
     */
    public boolean containsName(String str) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Checks wether the Contact contains the substring passed as argument in at least one of its email addresses.
     * @param str
     * @return true if the Contact contains the substring passed as argument in at least one of its email addresses
     *         false otherwise
     */
    public boolean containsEmail(String str) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Checks wether the Contact contains the substring passed as argument in at least one of its phone numbers.
     * @param str
     * @return true if the Contact contains the substring passed as argument in at least one of its phone numbers
     *         false otherwise
     */
    public boolean containsPhone(String str) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Checks wether the Contact contains the substring passed as argument in at least one of the tags with which it is marked.
     * @param str
     * @return true if the Contact contains the substring passed as argument in at least one of the tags with which it is marked
     *         false otherwise
     */
    public boolean containsTag(String str) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public String toString() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public int compareTo(Contact other) {
        // TODO: Implement this method
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        // TODO: Implement this method
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // TODO: Implement this method
    }
}