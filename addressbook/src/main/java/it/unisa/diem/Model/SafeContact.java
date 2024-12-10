package it.unisa.diem.Model;

import java.io.IOException;

import it.unisa.diem.Model.Interfaces.Checker.CharacterLimitStringChecker;
import it.unisa.diem.Model.Interfaces.Checker.Checker;
import it.unisa.diem.Model.Interfaces.Checker.ItalianPhoneChecker;
import it.unisa.diem.Model.Interfaces.Checker.SimpleEmailChecker;

/**
 * A safe version of the {@link Contact} class, which uses {@link Checker} instances to ensure that no illegal value is assigned to its fields.
 */
public class SafeContact extends Contact {
    public static final int MAX_NAME_LEN = 50;
    public static final int MAX_SURNAME_LEN = 50;
    
    /**
     * Creates a new SafeContact with default values. It is called by the {@link #safeContact()} for readability purpouses.
     * @see SafeContact#safeContact()
     */
    private SafeContact() {
        // TODO: Implement this method
    }


    /**
     * Creates a new SafeContact with default values.
     * @see SafeContact#SafeContact()
     */
    public SafeContact safeContact(){
        return new SafeContact();
    }


    /**
     * Creates a new SafeContact with the given name and surname. It is called by the {@link #safeContact(String, String)} method if the argument fields are valid, according to the {@link Checker} implementations.
     * 
     * @param[in] name the name of the new SafeContact
     * @param[in] surname the surname of the new SafeContact
     * @see SafeContact#safeContact(String, String)
     */
    private SafeContact(String name, String surname) {
        // TODO: Implement this method
    }

    /**
     * Creates a new SafeContact with the given name and surname, but only if they're valid, according to the {@link Checker} implementations.
     * 
     * @param[in] name the name of the new SafeContact
     * @param[in] surname the surname of the new SafeContact
     * @see SafeContact#SafeContact(String, String)
     */
    public SafeContact safeContact(String name, String surname){
        //TODO: Implement this method
    }

    /**
     * Creates a new SafeContact with the given name, surname and phone numbers. It is called by the {@link #safeContact(String, String, String[], String[])} method if the argument fields are valid, according to the {@link Checker} implementations.
     * 
     * @param[in] name the name of the new SafeContact
     * @param[in] surname the surname of the new SafeContact
     * @param[in] phoneNumber the phone numbers of the new SafeContact
     * @param[in] email the email addresses of the new SafeContact
     * @see SafeContact#safeContact(String, String, String[], String[])
     */
    public SafeContact(String name, String surname, String[] phoneNumber, String[] email) {
        // TODO: Implement this method
    }

    /**
     * Creates a new SafeContact with the given name, surname, phone numbers and email addresses, but only if they're valid, according to the {@link Checker} implementations.
     * 
     * @param[in] name the name of the new SafeContact
     * @param[in] surname the surname of the new SafeContact
     * @param[in] phoneNumber the phone numbers of the new SafeContact
     * @param[in] email the email addresses of the new SafeContact
     * @see SafeContact#SafeContact(String, String, String[], String[])
     */
    public SafeContact safeContact(String name, String surname, String[] phoneNumber, String[] email){
        //TODO: Implement this method
    }

    /**
     * @copydoc Contact::setName(String)
     * This method only acts if it verifies that the argument satisfies the condition of {@link CharacterLimitStringChecker} (character limit is set to {@link #MAX_NAMELEN}).
     * @pre The name satisfies the condition of {@link CharacterLimitStringChecker#check(String)} for {@link #MAX_NAMELEN}
     * @return true if the condition is met, false otherwise
     */
    @Override
    public boolean setName(String name) {
        // TODO: Implement this method
    }

    /**
     * @copydoc Contact::setSurname(String)
     * This method only acts if it verifies that the argument satisfies the condition of {@link CharacterLimitStringChecker} (character limit is set to {@link #MAX_SURNAMELEN}). 
     * @pre The surname satisfies the condition of {@link CharacterLimitStringChecker#check(String)} for {@link #MAX_SURNAMELEN}
     * @return true if the condition is met, false otherwise
     */
    @Override
    public boolean setSurname(String surname) {
        // TODO: Implement this method
    }

    /**
     * @copydoc Contact::setPicture(String)
     */
    @Override
    public void setPicture(String picture) throws IOException {
        // TODO: Implement this method
    }

    /**
     * @copydoc Contact::addEmail(String...)
     * This method only acts if it verifies that the argument satisfies the condition of {@link SimpleEmailChecker}. 
     * @pre All the email addresses satisfy the condition of {@link SimpleEmailChecker#check(String)}
     * @return true if the condition is met, false otherwise
     */
    @Override
    public boolean addEmail(String... email) {
        // TODO: Implement this method
        return false;
    }

    /**
     * @copydoc Contact::setEmail(String, int)
     * This method only acts if it verifies that the argument satisfies the condition of {@link SimpleEmailChecker}.
     * @pre The email address satisfies the condition of {@link SimpleEmailChecker#check(String)}
     * @return true if the condition is met, false otherwise
     */
    @Override
    public boolean setEmail(String email, int index) {
        // TODO: Implement this method
        return false;
    }

    /**
     * @copydoc Contact::addPhoneNumber(String...)
     * This method only acts if it verifies that the argument satisfies the condition of {@link ItalianPhoneChecker}.
     * @pre All the phone numbers satisfy the condition of {@link ItalianPhoneChecker#check(String)}
     * @return true if the condition is met, false otherwise
     */
    @Override
    public boolean addPhoneNumber(String... phoneNumber) {
        // TODO: Implement this method
        return false;
    }

    /**
     * @copydoc Contact::setPhoneNumber(String, int)
     * This method only acts if it verifies that the argument satisfies the condition of {@link ItalianPhoneChecker}.
     * @pre The phone number satisfies the condition of {@link ItalianPhoneChecker#check(String)}
     * @return true if the condition is met, false otherwise
     */
    @Override
    public boolean setPhoneNumber(String phoneNumber, int index) {
        // TODO: Implement this method
        return false;
    }
}