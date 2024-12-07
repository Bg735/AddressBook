package it.unisa.diem.Model.Interfaces.Filter;

import java.util.Set;
import it.unisa.diem.Model.Contact;

public class TagFilter extends FilterDecorator {
    public TagFilter(Filter filter) {
        super(filter);
    }

    @Override
    public Set<Contact> filter(Set<Contact> contacts, String string) {
        // Implement tag filtering logic here
        return super.filter(contacts, string);
    }
}