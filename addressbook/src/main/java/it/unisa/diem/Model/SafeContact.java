package it.unisa.diem.Model;

import java.io.IOException;

import it.unisa.diem.Model.Interfaces.Checker.CharacterLimitStringChecker;
import it.unisa.diem.Model.Interfaces.Checker.Checker;
import it.unisa.diem.Model.Interfaces.Checker.ItalianPhoneChecker;
import it.unisa.diem.Model.Interfaces.Checker.SimpleEmailchecker;

/**
 * A safe version of the {@link Contact} class, which uses {@link Checker} instances to ensure that no illegal value is assigned to its fields.
 */
public class SafeContact extends Contact {
    public static final int MAXNAMELEN = 50;
    public static final int MAXSURNAMELEN = 50;
    /**
     * Creates a new SafeContact with default values.
     */
    public SafeContact() {
        // TODO: Implement this method
    }

    /**
     * Creates a new SafeContact with the given name and surname.
     * 
     * @param[in] name the name of the new SafeContact
     * @param[in] surname the surname of the new SafeContact
     */
    public SafeContact(String name, String surname) {
        // TODO: Implement this method
    }

    /**
     * Creates a new SafeContact with the given name, surname and phone numbers.
     * 
     * @param[in] name the name of the new SafeContact
     * @param[in] surname the surname of the new SafeContact
     * @param[in] phoneNumber the phone numbers of the new SafeContact
     */
    public SafeContact(String name, String surname, String... phoneNumber) {
        // TODO: Implement this method
    }

    /**
     * Sets the name of the SafeContact to the given value, but only if it verifies the condition of {@link CharacterLimitStringChecker} (character limit is ise to {@value #MAXNAMELEN}).
     * 
     * @pre name != null
     * @pre name.length() <= {@value #MAXNAMELEN}
     * @pre !name.isEmpty()
     * @pre The name satisfies the condition of {@link CharacterLimitStringChecker#check(String)}
     * @post getNameValue().equals(name)
     * @param[in] name the new name of the contact
     * @return true if the name has been set, false otherwise
     */
    @Override
    public boolean setName(String name) {
        // TODO: Implement this method
    }

    /**
     * Sets the surname of the SafeContact to the given value, but only if it verifies the condition of {@link CharacterLimitStringChecker} (character limit is ise to {@value #MAXSURNAMELEN}).
     * 
     * @pre surname != null
     * @pre surname.length() <= {@value #MAXSURNAMELEN}
     * @pre !surname.isEmpty()
     * @pre The surname satisfies the condition of {@link CharacterLimitStringChecker#check(String)}
     * @post getSurnameValue().equals(surname)
     * @param[in] surname the new surname of the contact
     * @return true if the surname has been set, false otherwise
     */
    @Override
    public boolean setSurname(String surname) {
        // TODO: Implement this method
    }

    /**
     * Takes the picture at the specified path and copies it in the application's assets folder, then assigns it to the contact as a profile picture.
     * 
     * @param[in] picture the path of the picture to assign to the contact
     */
    @Override
    public void setPicture(String picture) throws IOException {
        // TODO: Implement this method
        return false;
    }

    /**
     * Adds the given email addresses to the contact, but only if they verify the condition of {@link SimpleEmailchecker}.
     * 
     * @pre email != null
     * @pre email.length > 0
     * @pre All the email addresses satisfy the condition of {@link SimpleEmailchecker#check(String)}
     * @post getEmailList().containsAll(email)
     * @post getEmailList().size() == old.getEmailList().size() + email.length
     * @post getEmailList().containsAll(old.getEmailList())
     * @param[in] email the email addresses to add to the contact
     * @return true if the email addresses have been added, false otherwise
     */
    @Override
    public boolean addEmail(String... email) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Sets the email address at the specified index to the given value, but only if it verifies the condition of {@link SimpleEmailchecker}.
     * 
     * @pre email != null
     * @pre email.length > 0
     * @pre index >= 0
     * @pre index < getEmailList().size()
     * @pre The email address satisfies the condition of {@link SimpleEmailchecker#check(String)}
     * @post getEmailAtIndex(index).equals(email)
     * @param[in] email the new email address
     * @param[in] index the index of the email address to set
     * @return true if the email address has been set, false otherwise
     */
    @Override
    public boolean setEmail(String email, int index) {
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
    @Override
    public boolean removeEmailAtIndex(int index) {
        // TODO: Implement this method
    }

    /**
     * Adds the given phone numbers to the contact, but only if they verify the condition of {@link ItalianPhoneChecker}.
     * 
     * @pre phoneNumber != null
     * @pre phoneNumber.length > 0
     * @pre All the phone numbers satisfy the condition of {@link ItalianPhoneChecker#check(String)}
     * @post getPhoneNumberList().containsAll(phoneNumber)
     * @post getPhoneNumberList().size() == old.getPhoneNumberList().size() + phoneNumber.length
     * @post getPhoneNumberList().containsAll(old.getPhoneNumberList())
     * @param[in] phoneNumber the phone numbers to add to the contact
     * @return true if the phone numbers have been added, false otherwise
     */
    @Override
    public boolean addPhoneNumber(String... phoneNumber) {
        // TODO: Implement this method
        return false;
    }

    /**
     * Sets the phone number at the specified index to the given value, but only if it verifies the condition of {@link ItalianPhoneChecker}.
     * 
     * @pre phoneNumber != null
     * @pre phoneNumber.length > 0
     * @pre index >= 0
     * @pre index < getPhoneNumberList().size()
     * @pre The phone number satisfies the condition of {@link ItalianPhoneChecker#check(String)}
     * @post getPhoneNumberAtIndex(index).equals(phoneNumber)
     * @param[in] phoneNumber the new phone number
     * @param[in] index the index of the phone number to set
     * @return true if the phone number has been set, false otherwise
     */
    @Override
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
    @Override
    public boolean removePhoneNumberAtIndex(int index) {
        // TODO: Implement this method
    }

    /**
     * Adds the given tag to the contact, but only if it is not already present and it verifies the condition of {@link CharacterLimitStringChecker} (character limit is set to {@value Tag#MAXTAGLENGTH}).
     * 
     * @pre tag != null
     * @post getTagsSet().contains(tag)
     * @post getTagsSet().size() == old.getTagsSet().size() + 1
     * @param[in] tag the {@link Tag} to add
     * @return true if the tag has been added, false otherwise
     */
    @Override
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
    @Override
    public boolean removeTag(Tag tag) {
        // TODO: Implement this method
        return false;
    }
}