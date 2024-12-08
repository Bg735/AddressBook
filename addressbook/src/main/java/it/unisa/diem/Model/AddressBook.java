package it.unisa.diem.Model;

import it.unisa.diem.Model.Interfaces.ContactList;
import it.unisa.diem.Model.Interfaces.TaggableList;
import it.unisa.diem.Model.Interfaces.TrashCan;
import it.unisa.diem.Model.Interfaces.Filter.Filter;
import javafx.beans.property.SetProperty;
import javafx.beans.property.MapProperty;

public class AddressBook implements ContactList, TaggableList, TrashCan {
    private SetProperty<Contact> contactsList;
    private MapProperty<Tag, SetProperty<Contact>> tagMap;
    private RecentlyDeleted recentlyDeleted;
    private static Filter fullFilter = null;
    private static Filter tagFilter = null;

    public AddressBook(String path) {
        // Constructor implementation
    }

    public AddressBook() {
        // Constructor implementation
    }

    @Override
    public SetProperty<Contact> contacts() {
        // TODO: Implement this method (returns contactsList)
        return null;
    }
    
    @Override
    public MapProperty<Tag, SetProperty<Contact>> getTagMap() {
        // TODO: Implement this method (returns tagMap)
        return null;
    }

    @Override
    public RecentlyDeleted trashCan() {
        // TODO: Implement this method (returns recentlyDeleted)
        return null;
    }

    @Override
    public void add(Contact c) {
        // TODO: Implement this method
    }

    @Override
    public void delete(Contact c) {
        // TODO: Implement this method, returns false if not found
    }

    @Override
    public Contact get(Contact c) {
        // TODO: Implement this method, returns null if not found
        return null;
    }

    @Override
    public void restore(Contact c) {
        // TODO: Implement this method
    }

    @Override
    public void addTagToContact(Tag tag, Contact c) {
        // TODO: Implement this method
    }

    @Override
    public void removeTagFromContact(Tag tag, Contact c) {
        // TODO: Implement this method
    }

    //uses FileManger.importFromFile
    public static AddressBook readFromFile(String path) {
        // TODO: Implement this method
        return null;
    }

    //uses FileManger.exportToFile
    public void writeToFile(String path) {
        // TODO: Implement this method
    }

}