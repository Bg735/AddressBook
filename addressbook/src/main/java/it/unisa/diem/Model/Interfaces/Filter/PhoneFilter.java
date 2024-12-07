package it.unisa.diem.Model.Interfaces.Filter;

import java.util.Set;
import it.unisa.diem.Model.Contact;

public class PhoneFilter extends FilterDecorator {
    public PhoneFilter(Filter filter) {
        super(filter);
    }

    @Override
    public Set<Contact> filter(Set<Contact> contacts, String string) {
        Set<Contact> result = super.filter(contacts, string);
        for (Contact contact : contacts)
            if (contact.containsPhone(string))
                result.add(contact);
        return result;
    }
}