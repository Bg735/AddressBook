package it.unisa.diem.Model.Interfaces;

import it.unisa.diem.Model.Tag;

/**
 * Interface for objects that can be marked with a {@link Tag}s.
 */
public interface Taggable {

    /**
     * Adds a {@link Tag} to the list of tags.
     * 
     * @param[in] tag the Tag to add
     * @return true, as specified by Collection.add(Tag)
     */
    boolean addTag(String string);

    /**
     * Removes a {@link Tag} from the list of tags.
     * 
     * @param[in] tag the Tag to remove
     * @return true if the Tag has been successfully removed
     *         false otherwise
     */
    boolean removeTag(String string);
}
