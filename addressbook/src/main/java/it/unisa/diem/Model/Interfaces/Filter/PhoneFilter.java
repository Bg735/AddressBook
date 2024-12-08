package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;

public class PhoneFilter extends FilterDecorator {
    public PhoneFilter(Filter filter) {
        super(filter);
    }

    @Override
    public boolean test(Contact t) {
        // TODO: Implement this method
        return false;
    }
}
