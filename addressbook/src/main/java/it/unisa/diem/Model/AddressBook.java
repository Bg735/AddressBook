package it.unisa.diem.Model;

import java.util.TreeSet;

import it.unisa.diem.Model.Interfaces.ContactList;
import it.unisa.diem.Model.Interfaces.TaggableList;
import it.unisa.diem.Model.Interfaces.TrashCan;
import it.unisa.diem.Model.Interfaces.Filter.Filter;
import javafx.beans.property.SetProperty;

import java.util.Set;
import java.util.TreeMap;

public class AddressBook implements ContactList, TaggableList, TrashCan {
    private TreeSet<Contact> contactsList;
    private TreeMap<Tag, TreeSet<Contact>> tagMap;
    private RecentlyDeleted recentlyDeleted;
    private static Filter fullFilter=null;
    private static Filter tagFilter=null;

    public AddressBook(String path) {
        // Constructor implementation
    }

    public AddressBook() {
        // Constructor implementation
    }


    public TreeSet<Contact> contacts() { //Should be readonly. To write use methods of the ContactList interface
        // Method implementation (returns contactsList)
    }

    public TreeMap<Tag, TreeSet<Contact>> getTagMap() {
        // Method implementation (returns tagMap())
    }

    public RecentlyDeleted trashCan() {
        // Method implementation (returns recentlyDeleted)
    }

    @Override
    public void add(Contact c) {
        // Method implementation
    }
    @Override
    public boolean delete(Contact c) {
        // Method implementation, returns false if not found
    }
    @Override
    public Contact get(Contact c) {
        // Method implementation, returns null if not found
    }

    @Override
    public SetProperty<Contact> search(String string, Filter filter) {
        //Method implementation
    }

    @Override
    public void restore(Contact c){
        // Method implementation
    }

    @Override
    public void permaDelete(Contact c){
        // Method implementation (calls recentlyDeleted.removePermanently())
    }

    @Override
    public void addTagToContact(Tag tag, Contact c){
        // Method implementation
    }

    @Override
    public void removeTagFromContact(Tag tag, Contact c){
        // Method implementation
    }
}