package it.unisa.diem.Model.Interfaces;

import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.RecentlyDeleted;

/**
 * Interface that grants methods for the management of a list of recently deleted contacts.
 */
public interface TrashCan {

    /**
     * Returns a list of recently deleted contacts.
     * 
     * @return a {@link RecentlyDeleted} containing the list of recently deleted contacts
     */
    RecentlyDeleted trashCan();

    /**
     * Permanently removes a deleted contact from the trash can.
     * 
     * @param[in] c the recently deleted contact to permanently remove
     */
    void delete(Contact c);

    /**
     * Restores a deleted contact from the trash can back to the active Collection of contacts.
     * 
     * @param[in] c the recently deleted contact to restore
     */
    void restore(Contact c);
}
