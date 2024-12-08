package it.unisa.diem.Model.Interfaces;

import javafx.beans.property.MapProperty;
import javafx.beans.property.SetProperty;
import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Tag;


/**
 * Interface that grants methods to manage a set of {@link Tag}s, each associated with a set of {@link Taggable} elements marked with such tags. 
 */
public interface TaggableList<T> {
    /**
     * Returns a MapProperty that has {@link Tag}s for keys and a {@link SetProperty}<T> of the T objects marked with such tag for values.
     * 
     * @return a MapProperty that has {@link Tag}s for keys and a {@link SetProperty}<T> of the T objects marked with such tag for values
     */
    MapProperty<Tag, SetProperty<T>> getTagMap();

    /**
     * Adds a {@link Tag} to the list of tags.
     * 
     * @param[in] t the Tag to add
     * @return true, as specified by Collection.add(Tag)
     */
    void addTagToContact(Tag t, Contact c);

    /**
     * Removes a {@link Tag} from the list of tags.
     * 
     * @param[in] t the Tag to remove
     * @return true if the Tag has been successfully removed
     *         false otherwise
     */
    void removeTagFromContact(Tag t, Contact c);
}
