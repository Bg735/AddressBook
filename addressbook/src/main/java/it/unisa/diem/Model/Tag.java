package it.unisa.diem.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import it.unisa.diem.Model.Interfaces.Checker.CharacterLimitStringChecker;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 * Wrapper for a tag that can be associated with a contact.
 * Tags are used to categorize contacts and filter them in the main view.
 * 
 * At the actual state, this class is a wrapper for a StringProperty, but it could be extended eventually to include more information about the tag.
 */
public class Tag implements Comparable<Tag> {
    public static final int MAX_TAGLENGTH = 20; /**< The maximum length of a tag */
    private transient StringProperty name; /**< The name of the tag */
    
    /**
     * Creates an empty tag.
     */
    public Tag() {
        name = new SimpleStringProperty();
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
     * Sets the StringProperty containing the name of the tag, given that it satisfies the condition of {@link CharacterLimitStringChecker} (the character limit is set to {@link #MAX_TAGLENGTH}).
     *
     * @param name the StringProperty containing the name of the tag
     */
    public boolean setName(String name) {
        if(new CharacterLimitStringChecker(MAX_TAGLENGTH).check(name)){
            this.name.set(name);
            return true;
        }
        return false;
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
     * Sets the name of the tag, given that it satisfies the condition of {@link CharacterLimitStringChecker} (the character limit is set to {@link #MAX_TAGLENGTH}).
     *
     * @param name the new name of the tag
     */
    public boolean setNameValue(String name) {
        if(new CharacterLimitStringChecker(MAX_TAGLENGTH).check(name)){
            this.name.set(name);
            return true;
        }
        return false;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(name.get());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        name = new SimpleStringProperty((String) in.readObject());
    }

    @Override
    public int compareTo(Tag o) {
        return name.get().compareTo(o.name.get());
    }

}