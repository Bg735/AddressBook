package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.StringProperty;

public abstract class FilterDecorator implements Filter {
    protected Filter f;

    public FilterDecorator(Filter f) {
        this.f = f;
    }

    @Override
    public StringProperty getSubstring() {
        return f.getSubstring();
    }

    @Override
    public abstract boolean test(Contact t);
}