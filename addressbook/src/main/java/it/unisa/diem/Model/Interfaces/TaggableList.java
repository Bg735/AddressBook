package it.unisa.diem.Model.Interfaces;

import java.util.TreeSet;
import javafx.beans.property.MapProperty;

import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Tag;


public interface TaggableList {
    MapProperty<Tag, TreeSet<Contact>> getTagMap();
    void addTagToContact(Tag t, Contact c);
    void removeTagFromContact(Tag t, Contact c);
}