package it.unisa.diem.Model.Interfaces.Filter;

import java.util.Set;
import it.unisa.diem.Model.Contact;

public class NameFilter extends FilterDecorator {
    public NameFilter(Filter filter) {
        super(filter);
    }

    @Override
    public Set<Contact> filter(Set<Contact> contacts, String string) {
        // Implement name filtering logic here
        return super.filter(contacts, string);
    }
}