package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Tag;
import java.util.Set;

/**
 * Concrete decorator of the Filter pattern that verifies if a Contact contains the substring passed to the BaseFilter in the construction chain, in its tags field.
 * Its constructor is passed as an outer method of a decorator construction chain, taking as argument another FilterDecorator, or a BaseFilter.
 * @see BaseFilter
 * @see Filter
 */
public class TagFilter extends FilterDecorator {
    
    /**
     * @copydoc FilterDecorator::FilterDecorator()
     */
    public TagFilter(Filter filter) {
        super(filter);
    }

    /**
     * @copydoc FilterDecorator::test()
     * For this class, the condition is that the Contact's tag field contains the substring passed to the BaseFilter in the construction chain.
     */
    @Override
    public boolean test(Contact contact) {
        String sub = getSubstring();
        Set<Tag> tags = contact.getTags().get();
        for (Tag tag: tags)
            if (tag.getNameValue().toLowerCase().contains(sub))
                return true;
        return false;
    }
}