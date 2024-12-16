package it.unisa.diem.Model;

import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import it.unisa.diem.Model.Interfaces.ContactList;
import it.unisa.diem.Model.Interfaces.TaggableList;
import it.unisa.diem.Model.Interfaces.TrashCan;
import it.unisa.diem.Utility.FileManager;
import java.io.Serializable;
import java.util.HashMap;
import javafx.beans.property.SetProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;

/**
 * Main model for the address book view.
 * Contains fields and methods to enclose the functionalities of a {@link ContactList}, a {@link TaggableList} and a {@link TrashCan}.
 * In particular, it allows for the storage and management of an ordered list of taggable {@link SafeContact}s ammitting omonyms, which can be restored within {@link RecentlyDeleted#RETENTION_PERIOD_DAYS} days of their deletion.
 * It is created to grant full compatibility with a JavaFX UI
 * 
 * @invariant contactsList != null
 * @invariant tagMap != null
 * @invariant recentlyDeleted != null
 */
public class AddressBook implements ContactList, TaggableList<Contact>, TrashCan, Serializable {
    private SetProperty<Contact> contactsList; /**< The list of contacts to manage */
    private MapProperty<Tag, SetProperty<Contact>> tagMap; /**< The map that stores all the tags and the sets of contacts marked with them */
    private RecentlyDeleted recentlyDeleted; /** The list of contacts that have been deleted within {@link RecentlyDeleted#RETENTION_PERIOD_DAYS} days */
    
    /**
     * Constructs an empty AddressBook.
     * @post contactsList != null 
     * @post tagMap != null 
     * @post recentlyDeleted != null 
     */
    public AddressBook() {
        // Constructor implementation
        this.contactsList = new SimpleSetProperty<Contact>(FXCollections.observableSet(new TreeSet<Contact>()));
        this.tagMap = new SimpleMapProperty<Tag,SetProperty<Contact>>(FXCollections.observableMap(new TreeMap<Tag,SetProperty<Contact>>()));
        this.recentlyDeleted = new RecentlyDeleted();
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
        this();
        if(path == null)
            throw new IllegalArgumentException("Path cannot be null");
        AddressBook loadedBook = readFromFile(path);
        if (loadedBook != null) {
            this.contactsList = loadedBook.contactsList;
            this.tagMap = loadedBook.tagMap;
            this.recentlyDeleted = loadedBook.recentlyDeleted;
        }
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
        return contactsList;
    }
    
    /**
     * Returns the map of tags and the sets of contacts marked with them.
     * @invariant tagMap != null
     * @return the map of tags and the sets of contacts marked with them
     */
    @Override
    public MapProperty<Tag, SetProperty<Contact>> getTagMap() {
        return tagMap;
    }

    /**
     * Returns the list of contacts that have been deleted within {@link RecentlyDeleted#RETENTION_PERIOD_DAYS} days.
     * 
     * @invariant recentlyDeleted != null
     * @return the list of contacts that have been deleted within {@link RecentlyDeleted#RETENTION_PERIOD_DAYS} days
     */
    @Override
    public RecentlyDeleted trashCan() {
        return recentlyDeleted;
    }

    /**
     * Adds a contact to the list of contacts.
     * 
     * @param c the contact to add
     * @invariant c != null
     * @post contactsList.contains(c)
     * @post contactsList.size() == contactsList.size()@pre + 1
     */
    @Override
    public void add(Contact c) {
        if (c == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if(contactsList.add(c)){
            addToTagMap(c);
        }
    }

    /**
     * Deletes a contact from the list of contacts.
     * 
     * @param c the contact to delete
     * @invariant c != null
     * @post !contactsList.contains(c)
     * @post contactsList.size() == contactsList.size()@pre - 1
     */
    @Override
    public void delete(Contact c) {
        if (c == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        if (contactsList.remove(c)) {
            removeFromTagMap(c);
            recentlyDeleted.put(c);
        }
    }

    /**
     * Adds a contact to the list of recently deleted contacts.
     * 
     * @param c the contact to add
     * @invariant c != null
     * @post recentlyDeleted.contains(c)
     * @post recentlyDeleted.size() == recentlyDeleted.size()@pre + 1
     */
    public void addToTagMap(Contact c) {
        for (Tag tag : c.getTags().get()) {
            if (!tagMap.containsKey(tag))
                tagMap.put(tag, new SimpleSetProperty<>(FXCollections.observableSet(new TreeSet<>())));
            tagMap.get(tag).add(c);
        }
    }

    /**
     * Removes a contact from the tag map.
     * 
     * @param c the contact to remove
     * @invariant c != null
     * @post the contact is not part of any set of contacts marked with a tag
     */
    public void removeFromTagMap(Contact c) {
        for (Tag tag : c.getTags().get()) {
            if (tagMap.containsKey(tag)) {
                tagMap.get(tag).remove(c);
                if (tagMap.get(tag).isEmpty()) {
                    tagMap.remove(tag);
                }
            }
        }
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
        if (c == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
        trashCan().remove(c);
        contactsList.add(c);
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
        if (t == null || c == null) {
            throw new IllegalArgumentException("Tag and contact cannot be null");
        } 
        c.addTag(t.getNameValue());
        addToTagMap(c);
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
    public void removeTagFromContact(Tag t, Contact c) {
        if (t == null || c == null) {
            throw new IllegalArgumentException("Tag and contact cannot be null");
        }
        if (tagMap.containsKey(t)) {
            tagMap.get(t).get().remove(c);
   
            if (tagMap.get(t).get().isEmpty()) {
                tagMap.remove(t);
            }
        }
        c.removeTag(t.getNameValue());
    }

    /**
     * Imports an AddressBook object from an internal file at the specified path.
     * @param path
     * @invariant path != null
     * @return the AddressBook object read from the file
     * @see FileManager#importFromFile(String)
     */
    
    public static AddressBook readFromFile(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        
        try {
            return FileManager.importFromFile(path);
        } catch (Exception e) {
            System.err.println("Error reading AddressBook from file: " + e.getMessage());
            return null;
        }
    }


    /**
     * Exports the AddressBook object to an internal file at the specified path.
     * @param path
     * @invariant path != null
     * @see FileManager#exportToFile(String)
     */
    public void writeToFile(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Path cannot be null");
        }
        
        try {
            FileManager.exportToFile(path,this);
        } catch (Exception e) {
            System.err.println("Error writing AddressBook to file: " + e.getMessage());
        }
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
        if (c == null) {
            throw new IllegalArgumentException("Contact cannot be null");
        }
  
        for (Contact contact : contactsList) {
            if (contact.equals(c)) {
                return contact;
            }
        }
        
        return null; 
    }

}