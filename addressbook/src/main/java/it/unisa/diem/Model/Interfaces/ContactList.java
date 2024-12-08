package it.unisa.diem.Model.Interfaces;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.SetProperty;

public interface ContactList { //Must grant safe access to a list of contacts, using checkers to grant no Contact is set to an illegal state.
    SetProperty<Contact> contacts();
    void add(Contact c);
    void delete(Contact c);
    Contact get(Contact c); //Should be readonly. To write use the other methods of this interface.
}
