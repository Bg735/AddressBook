package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;

/**
 * Concrete decorator of the Filter pattern that verifies if a Contact contains the substring passed to the BaseFilter in the construction chain, in its phoneNumber field.
 * Its constructor is passed as an outer method of a decorator construction chain, taking as argument another FilterDecorator, or a BaseFilter.
 * @see BaseFilter
 * @see Filter
 */
public class PhoneFilter extends FilterDecorator {
    
    /**
     * @copydoc FilterDecorator::FilterDecorator()
     */
    public PhoneFilter(Filter filter) {
        super(filter);
    }

    /**
     * @copydoc FilterDecorator::test()
     * For this class, the condition is that the Contact's phoneNumber field contains the substring passed to the BaseFilter in the construction chain.
     */
    @Override
    public boolean test(Contact contact) {
        String sub = getSubstring();
        String[] phones = contact.getPhoneNumberList();
        for (String p: phones)
            if (p.toLowerCase().contains(sub))
                return true;
        return false;
    }
}
