package it.unisa.diem.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Wrapper for a tag that can be associated with a contact.
 * Tags are used to categorize contacts and filter them in the main view.
 * 
 * At the actual state, this class is a wrapper for a StringProperty, but it could be extended eventually to include more information about the tag.
 */
public class Tag {
    private transient StringProperty name; ///< The name of the tag

    /**
     * Constructs a Tag with an empty name.
     */
    public Tag() {
        name = new SimpleStringProperty();
    }

    /**
     * Constructs a Tag with the given name.
     *
     * @param name the name of the tag
     */
    public Tag(String name) {
        this();
        this.name.set(name);
    }

    /**
     * Returns the StringProperty containing the name of the tag.
     *
     * @return the StringProperty containing the name of the tag.
     */
    public StringProperty getName() {
        return name;
    }

    /**
     * Sets the name of the tag.
     *
     * @param name the new name of the tag
     */
    public void setName(StringProperty name) {
        this.name = name;
    }

    /**
     * Returns the name of the tag.
     *
     * @return the name of the tag.
     */
    public String getNameValue() {
        return name.get();
    }

    /**
     * Sets the name of the tag.
     *
     * @param name the new name of the tag
     */
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
