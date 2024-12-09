package it.unisa.diem.Model;

import it.unisa.diem.Model.Interfaces.ContactList;
import it.unisa.diem.Model.Interfaces.TaggableList;
import it.unisa.diem.Model.Interfaces.TrashCan;
import it.unisa.diem.Model.Interfaces.Filter.Filter;
import javafx.beans.property.SetProperty;
import javafx.beans.property.MapProperty;

/**
 * Main model for the address book view.
 * Contains fields and methods to enclose the functionalities of a {@link ContactList}, a {@link TaggableList} and a {@link TrashCan}.
 * In particular, it allows for the storage and management of an ordered list of taggable {@link SafeContact}s ammitting omonyms, which can be restored within {@value RecentlyDeleted#RETENTION_PERIOD_DAYS} days of their deletion.
 * It is created to grant full compatibility with a JavaFX UI
 * 
 * @invariant contactsList != null
 * @invariant tagMap != null
 * @invariant recentlyDeleted != null
 */
public class AddressBook implements ContactList, TaggableList, TrashCan {
    private SetProperty<SafeContact> contactsList; /**< The list of contacts to manage */
    private MapProperty<Tag, SetProperty<SafeContact>> tagMap; /**< The map that stores all the tags and the sets of contacts marked with them */
    private RecentlyDeleted recentlyDeleted; /** The list of contacts that have been deleted within {@value RecentlyDeleted#RETENTION_PERIOD_DAYS} days */
    private static Filter fullFilter = null; /**< The filter that is applied to the contacts list when using the search function*/
    private static Filter tagFilter = null; /**< The filter that is applied to the contacts list when visualizing the contacts marked with a certain {@link Tag}*/

    
    /**
     * Constructs an empty AddressBook.
     * @post contactsList != null
     * @post tagMap != null
     * @post recentlyDeleted != null
     */
    public AddressBook() {
        // Constructor implementation
    }

    /**
     * Constructs an AddressBook with the given path.
     * 
     * @param path the path to the file to read the AddressBook from
     * @invariant path != null
     * @pre the file at the given path is a valid AddressBook file
     * @post the AddressBook is read from the file at the given path
     * @see AddressBook#readFromFile(String)
     */
    public AddressBook(String path) {
        // Constructor implementation
    }

    /**
     * Returns the list of contacts.
     * @important The returned collection is intended to be read-only.
     * 
     * @invariant contactsList != null
     * @return the list of contacts
     */
    @Override
    public SetProperty<Contact> contacts() {
        // TODO: Implement this method (returns contactsList)
        return null;
    }
    
    /**
     * Returns the map of tags and the sets of contacts marked with them.
     * @invariant tagMap != null
     * @return the map of tags and the sets of contacts marked with them
     */
    @Override
    public MapProperty<Tag, SetProperty<Contact>> getTagMap() {
        // TODO: Implement this method (returns tagMap)
        return null;
    }

    /**
     * Returns the list of contacts that have been deleted within {@value RecentlyDeleted#RETENTION_PERIOD_DAYS} days.
     * 
     * @invariant recentlyDeleted != null
     * @return the list of contacts that have been deleted within {@value RecentlyDeleted#RETENTION_PERIOD_DAYS} days
     */
    @Override
    public RecentlyDeleted trashCan() {
        // TODO: Implement this method (returns recentlyDeleted)
        return null;
    }

    /**
     * Adds a contact to the list of contacts.
     * 
     * @param c the contact to add
     * @invariant c != null
     * @post contactsList.contains(c)
     * @post contactsList.size() == contactsList.size()@pre + 1
     * @return true, as specified by {@link ContactList#add(Contact)}
     */
    @Override
    public void add(Contact c) {
        // TODO: Implement this method
    }

    /**
     * Deletes a contact from the list of contacts.
     * 
     * @param c the contact to delete
     * @invariant c != null
     * @post !contactsList.contains(c)
     * @post contactsList.size() == contactsList.size()@pre - 1
     * @return true if the contact has been successfully deleted, false otherwise
     */
    @Override
    public void delete(Contact c) {
        // TODO: Implement this method, returns false if not found
    }

    

    /**
     * Restores a deleted contact from the trash can back to the active list of contacts.
     * 
     * @param c the recently deleted contact to restore
     * @invariant c != null
     * @pre recentlyDeleted.contains(c)
     * @post contactsList.contains(c)
     * @post contactsList.size() == contactsList.size()@pre + 1
     */
    @Override
    public void restore(Contact c) {
        // TODO: Implement this method
    }

    
    /**
     * Adds a tag to the specified contact and updates the tag map accordingly, creating the tag if it does not already exist.
     * 
     * @param t the tag to add
     * @param c the contact to tag
     * @invariant t != null
     * @invariant c != null
     * @post the tag map contains the tag and the contact is part of the set of contacts marked with such tag
     */
    @Override
    public void addTagToContact(Tag t, Contact c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addTagToContact'");
    }

    /**
     * Removes a tag from the specified contact and updates the tag map accordingly, deleting the tag if it is no longer associated with any contact.
     * 
     * @param t the tag to remove
     * @param c the contact to untag
     * @invariant t != null
     * @invariant c != null
     * @post the contact is not part of the set of contacts marked with such tag. If it was the last contact with that tag, the tag is removed from the tag map
     */
    @Override
    public void removeTagFromContact(Tag tag, Contact c) {
        // TODO: Implement this method
    }

    /**
     * Imports an AddressBook object from an internal file at the specified path.
     * @param path
     * @invariant path != null
     * @return the AddressBook object read from the file
     * @see FileManager#importFromFile(String)
     */
    
    public static AddressBook readFromFile(String path) {
        // TODO: Implement this method
        return null;
    }


    /**
     * Exports the AddressBook object to an internal file at the specified path.
     * @param path
     * @invariant path != null
     * @see FileManager#exportToFile(String)
     */
    public void writeToFile(String path) {
        // TODO: Implement this method
    }


    /**
     * Returns the specified contact retrieved from the list of contacts.
     * 
     * @param c the contact to get
     * @pre the contact is in the list
     * @invariant c != null
     * @return the contact from the list
     */
    @Override
    public Contact get(Contact c) {
        // TODO Auto-generated method stub
    }

}