package it.unisa.diem.Model.Interfaces;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.SetProperty;

/**
 * Interface that grants methods for the management of a list of Contacts mantained in alphanumerical order as a Setproperty<Contact>.
 * Despite being a set, the list of contacts allows for omonyms.
 *
 */
public interface ContactList {
    
    /**
     * Returns an ordered set of Contacts that allows for omonyms.
     * 
     * @invariant the list is ordered
     * @return a SetProperty<Contact> containing the list of Contacts
     */
    SetProperty<Contact> contacts();

    /**
     * Adds a Contact to the list.
     * 
     * @param[in] c the Contact to add
     * @return true, as specified by Collection.add(Contact)
     */
    boolean add(Contact c);

    /**
     * Deletes a Contact from the list.
     * 
     * @pre the Contact is in the list
     * @param[in] c the Contact to delete
     * @return true if the Contact has been successfully deleted
     *         false otherwise
     */
    boolean delete(Contact c);

    /**
     * Returns a Contact from the list.
     * 
     * @param[in] c the Contact to get
     * @return the Contact from the list
     *         null if the Contact is not in the list
     */
    Contact get(Contact c);
}
