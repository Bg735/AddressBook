package it.unisa.diem.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import it.unisa.diem.Model.Interfaces.ContactList;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;


/**
 * Represents a trash can with {@link Contact}s deleted less than {@value #RETENTION_PERIOD_DAYS} days from a {@link ContactList}.
 * 
 * The trash can is implemented as a map of {@link LocalDateProperty} keys and {@link SetProperty} values.
 * The map is ordered by the date of deletion of the contacts.
 * @invariant trashCan != null
 */
public class RecentlyDeleted implements Serializable {
    public static final int RETENTION_PERIOD_DAYS = 30; /**< The number of days a contact can be restored after its deletion */
    private transient MapProperty<LocalDateProperty, SetProperty<Contact>> trashCan; /**< The map of deleted contacts */

    /**
     * Creates a new RecentlyDeleted with an empty trash can.
     */
    public RecentlyDeleted() {
        trashCan = new SimpleMapProperty<>(FXCollections.observableMap(new TreeMap<>((o1, o2) -> o1.get().compareTo(o2.get()))));
    }

    /**
     * Returns the trash can.
     * 
     * @invariant trashCan != null
     * @return the trash can
     */
    public MapProperty<LocalDateProperty, SetProperty<Contact>> get() {
        return trashCan;
    }

    /**
     * Permanently removes all the contacts moved to the trash can more than {@value #RETENTION_PERIOD_DAYS} days ago.
     * 
     * @invariant trashCan != null
     * @post trashCan.get().size() <= trashCan.get().size()@pre
     */
    public void removeExpired() {
        // TODO: Implement this method
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        for (Map.Entry<LocalDateProperty, SetProperty<Contact>> entry : trashCan.entrySet()) {
            out.writeObject(entry.getKey());
            out.writeObject(entry.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        trashCan = new SimpleMapProperty<>(FXCollections.observableMap(new TreeMap<>((o1, o2) -> o1.get().compareTo(o2.get()))));
        while (in.available() > 0) {
            trashCan.put((LocalDateProperty) in.readObject(), (SetProperty<Contact>) in.readObject());
        }
    }
}
