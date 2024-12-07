package it.unisa.diem.Model.Interfaces.Filter;

import java.util.Set;
import java.util.TreeSet;

import it.unisa.diem.Model.Contact;

public class BaseFilter implements Filter {
    @Override
    public Set<Contact> filter(Set<Contact> contacts, String string) {
        return new TreeSet<>();
    }
}