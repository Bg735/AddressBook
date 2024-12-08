package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;

public class EmailFilter extends FilterDecorator {
    public EmailFilter(Filter filter) {
        super(filter);
    }

    @Override
    public boolean test(Contact contact) {
        // TODO: Implement this method
        return false;
    }
}
