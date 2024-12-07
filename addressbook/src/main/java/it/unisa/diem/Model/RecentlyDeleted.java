package it.unisa.diem.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;


public class RecentlyDeleted implements Serializable {
    private transient TreeMap<LocalDateProperty, ArrayList<Contact>> recentlyDeleted;

    public RecentlyDeleted() {
        recentlyDeleted = new TreeMap<>((o1,o2)->o1.get().compareTo(o2.get()));
    }

    public void add(Contact contact) {}

    public Contact retrieveContact(Contact contact) {
        return null;
    }

    public void removePermanently(Contact contact) {}

    public void removeOlderThan(int days) {}

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        for(Map.Entry<LocalDateProperty, ArrayList<Contact>> entry : recentlyDeleted.entrySet()) {
            out.writeObject(entry.getKey());
            out.writeObject(entry.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        recentlyDeleted = new TreeMap<>((o1,o2)->o1.get().compareTo(o2.get()));
        try (in) {
            while (in.available() > 0) {
            recentlyDeleted.put((LocalDateProperty) in.readObject(),(ArrayList<Contact>) in.readObject());
            }
        }
    }
}