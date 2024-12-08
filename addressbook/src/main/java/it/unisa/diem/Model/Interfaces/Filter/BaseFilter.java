package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.StringProperty;

public class BaseFilter implements Filter {
    private StringProperty substring;

    public BaseFilter(StringProperty substring) {
        this.substring = substring;
    }

    @Override
    public StringProperty getSubstring() {
        return substring;
    }

    @Override
    public boolean test(Contact contact) {
        // TODO: Implement this method
        return false;
    }
}