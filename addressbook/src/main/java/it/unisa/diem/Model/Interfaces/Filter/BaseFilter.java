package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.StringProperty;

/**
 * Concrete component of the Filter Decorator pattern.
 * Its constructor is passed as the inner method of a decorator construction chain, taking as argument the substring to search for in the {@link Contact}.
 * @see Filter
 * @see FilterDecorator
 */
public class BaseFilter implements Filter {
    private StringProperty substring;

    /**
     * Constructs a BaseFilter with the given substring.
     * 
     * @param[in] substring the substring to search for in the Contact
     */
    public BaseFilter(StringProperty substring) {
        this.substring = substring;
    }

    /**
     * Returns the substring to search for in the Contact, which has been passed as argument to the constructor.
     */
    @Override
    public StringProperty getSubstring() {
        return substring;
    }

    /**
     * @copydoc Filter::test()
     * 
     */
    @Override
    public boolean test(Contact contact) {
        // TODO: Implement this method
        return false;
    }
}