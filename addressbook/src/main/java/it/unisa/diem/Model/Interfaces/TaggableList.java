package it.unisa.diem.Model.Interfaces;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SetProperty;
import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Tag;

public interface TaggableList {
    MapProperty<Tag, SetProperty<Contact>> getTagMap();
    void addTagToContact(Tag t, Contact c);
    void removeTagFromContact(Tag t, Contact c);
}
