package it.unisa.diem.Model;

import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Property that wraps a {@link LocalDate} object.
 */
public class LocalDateProperty extends SimpleObjectProperty<LocalDate> implements Comparable<LocalDateProperty>, Serializable {
    
    /**
     * Creates a new LocalDateProperty with the default value of an empty String.
     */
    public LocalDateProperty() {
        super();
    }

    /**
     * Creates a new LocalDateProperty with the given initial value.
     */
    public LocalDateProperty(LocalDate initialValue) {
        super(initialValue);
    }


    /**
     * Sets the wrapped LocalDate to the specified value.
     */
    @Override
    public void set(LocalDate newValue) {
        super.set(newValue);
    }

    /**
     * Returns the wrapped LocalDate.
     * @return the wrapped LocalDate
     */
    @Override
    public LocalDate get() {
        return super.get();
    }
    
    @Override
    public int compareTo(LocalDateProperty other) {
        return get().compareTo(other.get());
    }
}