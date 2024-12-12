package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;


/**
 * Concrete decorator of the Filter pattern that verifies if a Contact contains the substring passed to the BaseFilter in the construction chain, in its fullName field.
 * Its constructor is passed as an outer method of a decorator construction chain, taking as argument another FilterDecorator, or a BaseFilter.
 * @see BaseFilter
 * @see Filter
 */
public class NameFilter extends FilterDecorator {
    
    /**
     * @copydoc FilterDecorator::FilterDecorator()
     */
    public NameFilter(Filter filter) {
        super(filter);
    }

    /**
     * @copydoc FilterDecorator::test()
     * For this class, the condition is that the Contact's fullName field contains the substring passed to the BaseFilter in the construction chain.
     */
    @Override
    public boolean test(Contact contact) {
        // TODO: Implement this method
        return contact.containsName(getSubstring().get());
    }
}