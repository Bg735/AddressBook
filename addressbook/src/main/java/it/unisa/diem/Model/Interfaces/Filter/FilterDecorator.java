package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.StringProperty;

/**
 * Abstract decorator of the Filter pattern.
 * Its constructor is passed as an outer method of a decorator construction chain, taking as argument another FilterDecorator, or a {@link BaseFilter}.
 * @see BaseFilter
 * @see Filter
 */
public abstract class FilterDecorator implements Filter {
    protected Filter f;/**< The inner Filter */

    /**
     * Constructs a Filter decorator with the given inner Filter.
     * 
     * @param[in] f the inner Filter
     */
    public FilterDecorator(Filter f) {
        this.f = f;
    }

    /**
     * Returns the substring to search for in the Contact, obtained whith a nested call to the inner Filters of the chain, down to the BaseFilter.
     * @return the substring to search for in the Contact
     */
    @Override
    public StringProperty getSubstring() {
        return f.getSubstring();
    }

    /**
     * @copydoc Filter::test()
     */
    @Override
    public abstract boolean test(Contact contact);
}