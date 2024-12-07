package it.unisa.diem.Model.Interfaces;

import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.RecentlyDeleted;

public interface TrashCan {
    RecentlyDeleted trashCan();
    void restore(Contact c);
    void permaDelete(Contact c);
}
