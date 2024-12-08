package it.unisa.diem.Model.Interfaces;

import it.unisa.diem.Model.Tag;

public interface Taggable {
    boolean addTag(Tag tag);
    boolean removeTag(Tag tag);
}
