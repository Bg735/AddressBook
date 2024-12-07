package it.unisa.diem.Model.Interfaces.Filter;

import java.util.Set;
import it.unisa.diem.Model.Contact;

public interface Filter {
    Set<Contact> filter(Set<Contact> contacts, String string);
}