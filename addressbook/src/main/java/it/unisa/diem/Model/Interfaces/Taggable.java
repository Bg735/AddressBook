package it.unisa.diem.Model.Interfaces;

import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Tag;

public interface Taggable {
    void addTag(Tag tag, Contact contact);
    void removeTag(Tag tag, Contact contact);
}
