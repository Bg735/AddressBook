package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.StringProperty;


/**
 * Passed as a Predicate<Contact> that verifies a condition based on the fact that a {@link Contact} contains a string.
 * Abstract component of the Decorator pattern.
 */
public interface Filter extends java.util.function.Predicate<Contact> {
    
    /**
     * Returns true if the given Contact satisfies the condition of the filter.
     * 
     * @param[in] contact the Contact on which to apply the filter, verifying if it satisfies the condition.
     * @return true if the Contact satisfies the condition of the filter, false otherwise

     */
    @Override
    boolean test(Contact contact);

    /**
     * Returns the substring to search for in the Contact.
     * 
     * @return the substring to search for in the Contact
     */
    String getSubstring();
}