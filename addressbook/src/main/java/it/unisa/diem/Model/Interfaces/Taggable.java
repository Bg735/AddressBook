package it.unisa.diem.Model.Interfaces;

import it.unisa.diem.Model.Tag;

/**
 * Interface for objects that can be marked with a {@link Tag}s.
 */
public interface Taggable {
    boolean addTag(Tag tag);
    boolean removeTag(Tag tag);
}
