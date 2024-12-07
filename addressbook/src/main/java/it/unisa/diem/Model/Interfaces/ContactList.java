package it.unisa.diem.Model.Interfaces;

import java.util.TreeSet;

import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Interfaces.Filter.Filter;
import javafx.beans.property.SetProperty;

public interface ContactList { //Must grant safe access to a list of contacts, using checkers to grant no Contact is set to an illegal state.
    
    TreeSet<Contact> contacts();
    void add(Contact c);
    boolean delete(Contact c);
    Contact get(Contact c); //Should be readonly. To write use the other methods of this interface.
    SetProperty<Contact> search(String string, Filter filter);
}