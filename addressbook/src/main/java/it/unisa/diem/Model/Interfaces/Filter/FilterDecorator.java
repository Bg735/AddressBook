package it.unisa.diem.Model.Interfaces.Filter;

import java.util.Set;
import it.unisa.diem.Model.Contact;

public abstract class FilterDecorator implements Filter {
    protected Filter filter;

    public FilterDecorator(Filter filter) {
        this.filter = filter;
    }

    @Override
    public Set<Contact> filter(Set<Contact> contacts, String string) {
        return filter.filter(contacts, string);
    }
}