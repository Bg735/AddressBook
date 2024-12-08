package it.unisa.diem.Model;

import java.io.IOException;
import java.io.Serializable;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Profile implements Serializable{
    public static final int MAXNAMELEN = 3;
    private transient StringProperty name;
    private transient StringProperty phone;
    private String profilePicture; // path della foto profilo
    private String addressBookPath;

    public Profile(StringProperty name, StringProperty phone, StringProperty profilePicture, StringProperty addressBookPath) {
        // Constructor implementation
    }

    public StringProperty getName() {
        // TODO: Implement this method
    }

    public void setName(String name) {
        // TODO: Implement this method
    }

    public StringProperty getPhone() {
        // TODO: Implement this method
    }

    public void setPhone(int phone) {
        // TODO: Implement this method
    }

    public String getProfilePicture() {
        // TODO: Implement this method
    }

    public String getAddressBookPath() {
        return addressBookPath;
    }

    public void setAddressBookPath(String addressBookPath) {
        this.addressBookPath = addressBookPath;
    }

    public void setProfilePicture(String profilePicture) {
        // TODO: Implement this method
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(name.get());
        out.writeObject(phone.get());
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        name = new SimpleStringProperty((String) in.readObject());
        phone = new SimpleStringProperty((String) in.readObject());
    }
}