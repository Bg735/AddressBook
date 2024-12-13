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
    public static final int MAX_EMAILS = 3;
    public static final int MAX_PHONENUMBERS = 3;
    
    /**
     * Creates a new SafeContact with default values. It is called by the {@link #safeContact()} for readability purpouses.
     * @see SafeContact#safeContact()
     */
    private SafeContact() {
        // TODO: Implement this method
        super(); 
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
        super(name ,surname);  
    }

    /**
     * Creates a new SafeContact with the given name and surname, but only if they're valid, according to the {@link Checker} implementations.
     * 
     * @param[in] name the name of the new SafeContact
     * @param[in] surname the surname of the new SafeContact
     * @see SafeContact#SafeContact(String, String)
     * @return SafeContact if name and surname are valid, null otherwise
     */
    public SafeContact safeContact(String name, String surname){
        //TODO: Implement this method
        if(name == null && surname == null) return null; 
        if(name.isEmpty() && surname.isEmpty()) return null; 
        if(name == null) name = " "; 
        if(surname == null) surname = " "; 
        CharacterLimitStringChecker nameStringChecker = new CharacterLimitStringChecker(MAX_NAME_LEN); 
        CharacterLimitStringChecker surnameStringChecker = new CharacterLimitStringChecker(MAX_SURNAME_LEN); 
        if(nameStringChecker.check(name) && surnameStringChecker.check(surname)) 
            return new SafeContact(name, surname); 
        else return null; 
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
    public SafeContact(String name, String surname, String[] phoneNumbers, String[] emailAddresses) {
        // TODO: Implement this method
        super(name, surname, phoneNumbers, emailAddresses); 
    }

    /**
     * Creates a new SafeContact with the given name, surname, phone numbers and email addresses, but only if they're valid, according to the {@link Checker} implementations.
     * 
     * @param[in] name the name of the new SafeContact
     * @param[in] surname the surname of the new SafeContact
     * @param[in] phoneNumber the phone numbers of the new SafeContact
     * @param[in] email the email addresses of the new SafeContact
     * @see SafeContact#SafeContact(String, String, String[], String[])
     * @return SafeContact if name, surname, phone numbers and email addresses are valid, null otherwise
     */
    public SafeContact safeContact(String name, String surname, String[] phoneNumbers, String[] emailAddresses){
        //TODO: Implement this method
        
        if(safeContact(name, surname) != null) { 
        ItalianPhoneChecker phoneChecker = new ItalianPhoneChecker(); 
        SimpleEmailChecker emailChecker = new SimpleEmailChecker();  
        
        for(String phone : phoneNumbers) 
            if(phone!=null && !phoneChecker.check(phone)) return null;  
        for(String email : emailAddresses)
            if(email!=null && !emailChecker.check(email)) return null;  
        
        
        return new SafeContact(name, surname, phoneNumbers, emailAddresses); 
        } else return null; 
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
        if(name == null) return false;  
        if(name.trim().isEmpty() && super.getSurnameValue().isEmpty()) return false; 
        
        CharacterLimitStringChecker nameStringChecker = new CharacterLimitStringChecker(MAX_NAME_LEN); 
        if(nameStringChecker.check(name)) {
            super.setName(name);
            return true; 
        } else return false; 
        
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
        if(surname == null) return false;  
        if(surname.trim().isEmpty() && super.getNameValue().isEmpty()) return false; 
        
        CharacterLimitStringChecker surnameStringChecker = new CharacterLimitStringChecker(MAX_SURNAME_LEN); 
        if(surnameStringChecker.check(surname)) {
            super.setSurname(surname);
            return true; 
        } else return false; 
    }

    /**
     * @copydoc Contact::setPicture(String)
     * This method only acts if it verifies that the argument satisfies the condition of {@link PictureChecker}
     * @return true if the condition is met, false otherwise
     */
    @Override
    public boolean setPicture(String picture) throws IOException {
        // TODO: Implement this method
        ImagePathChecker pictureChecker = new ImagePathChecker(); 
        if(pictureChecker.check(picture)) {
            super.setPicture(picture);
            return true; 
        } else return false; 
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
        SimpleEmailChecker emailChecker = new SimpleEmailChecker(); 
        for(String e : email)
            if(e != null && !emailChecker.check(e)) return false; 
        
        super.addEmail(email); 
        return true; 
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
        SimpleEmailChecker emailChecker = new SimpleEmailChecker(); 
        if(emailChecker.check(email)) {
            super.setEmail(email, index);
            return true; 
        } else return false; 
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
        ItalianPhoneChecker phoneChecker = new ItalianPhoneChecker(); 
        for(String p : phoneNumber)
            if(p != null && !phoneChecker.check(p)) return false; 
        
        super.addPhoneNumber(phoneNumber); 
        return true; 
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
        ItalianPhoneChecker phoneChecker = new ItalianPhoneChecker(); 
        if(phoneChecker.check(phoneNumber)) {
            super.setPhoneNumber(phoneNumber, index);
            return true; 
        } else return false; 
    }
}