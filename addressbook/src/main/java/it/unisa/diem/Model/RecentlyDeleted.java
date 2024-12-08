package it.unisa.diem.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.collections.FXCollections;

public class RecentlyDeleted implements Serializable {
    private transient MapProperty<LocalDateProperty, SetProperty<Contact>> trashCan;

    public RecentlyDeleted() {
        trashCan = new SimpleMapProperty<>(FXCollections.observableMap(new TreeMap<>((o1, o2) -> o1.get().compareTo(o2.get()))));
    }

    public MapProperty<LocalDateProperty, SetProperty<Contact>> get() {
        return trashCan;
    }

    public void removeOlderThan(int days) {
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
