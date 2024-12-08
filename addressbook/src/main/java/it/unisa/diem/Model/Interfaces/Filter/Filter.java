package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.StringProperty;

/**
 * Passed as a Predicate<Contact> that verifies a condition based on the fact that a Contact contains a string.
 *  
 * @see FileDecorator, BaseFilter, NameFilter, EmailFilter, PhoneFilter, AddressFilter, TagFilter
 */
public interface Filter extends java.util.function.Predicate<Contact> {
    
    /**
     * Returns true if the given Contact satisfies the condition of the filter.
     * 
     * @param[in] t the Contact to test
     * @return true if the Contact satisfies the condition of the filter

     */
    @Override
    boolean test(Contact t);

    /**
     * Returns the substring to search for in the Contact.
     * 
     * @return the substring to search for in the Contact
     */
    StringProperty getSubstring();
}