package it.unisa.diem.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tag {
    private transient StringProperty name;

    public Tag() {
        name = new SimpleStringProperty();
    }

    public Tag(String name) {
        this();
        this.name.set(name);
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(StringProperty name) {
        this.name = name;
    }

    public String getNameValue() {
        return name.get();
    }

    public void setNameValue(String name) {
        this.name.set(name);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(name.get());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        name = new SimpleStringProperty((String) in.readObject());
    }
}
