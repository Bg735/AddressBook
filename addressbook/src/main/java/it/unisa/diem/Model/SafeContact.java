package it.unisa.diem.Model;

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


    //!

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
        if ((name == null || name.isEmpty()) && (surname == null || surname.isEmpty())) {
            return null; 
        }
        if (name == null) name = " "; 
        if (surname == null) surname = " "; 
        CharacterLimitStringChecker nameStringChecker = new CharacterLimitStringChecker(MAX_NAME_LEN); 
        CharacterLimitStringChecker surnameStringChecker = new CharacterLimitStringChecker(MAX_SURNAME_LEN); 
        if(nameStringChecker.check(name) && surnameStringChecker.check(surname)) 
            return new SafeContact(name, surname); 
        else return null; 
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
     * @copydoc Contact::addEmail(String...)
     * This method only acts if it verifies that the argument satisfies the condition of {@link SimpleEmailChecker}. 
     * @pre All the email addresses satisfy the condition of {@link SimpleEmailChecker#check(String)}
     * @return true if the condition is met, false otherwise
     */
    @Override
    public boolean addEmail(String... email) {
        // TODO: Implement this method
        SimpleEmailChecker emailChecker = new SimpleEmailChecker(); 
        for(int i=0 ; i<email.length ; i++) {
            if(email[i] != null && !emailChecker.check(email[i])) return false; 
            email[i] = email[i].trim(); 
        }
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
            super.setEmail(email.trim(), index);
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
        for(int i=0 ; i<phoneNumber.length ; i++) {
            if(phoneNumber[i] != null && !phoneChecker.check(phoneNumber[i])) return false; 
            phoneNumber[i] = phoneNumber[i].trim(); 
        }
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
            super.setPhoneNumber(phoneNumber.trim(), index);
            return true; 
        } else return false; 
    }
}