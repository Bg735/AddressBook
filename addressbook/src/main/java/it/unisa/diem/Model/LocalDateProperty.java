package it.unisa.diem.Model;

import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.time.LocalDate;

public class LocalDateProperty extends SimpleObjectProperty<LocalDate> implements Comparable<LocalDateProperty>, Serializable {
    public LocalDateProperty() {
        super();
    }

    public LocalDateProperty(LocalDate initialValue) {
        super(initialValue);
    }

    @Override
    public void set(LocalDate newValue) {
        super.set(newValue);
    }

    @Override
    public LocalDate get() {
        return super.get();
    }
    
    @Override
    public int compareTo(LocalDateProperty other) {
        return get().compareTo(other.get());
    }
}