package it.unisa.diem.Model.Interfaces;

import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.RecentlyDeleted;

public interface TrashCan {
    RecentlyDeleted trashCan();
    void delete(Contact c);
    void restore(Contact c);
}
