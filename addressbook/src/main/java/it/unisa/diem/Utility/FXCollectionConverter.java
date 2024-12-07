package it.unisa.diem.Utility;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.LocalDateProperty;

public class FXCollectionConverter {
    /**
     * Sorted collections returning from this method use the natural ordering of the elements.
     * @param <C>
     * @param <E>
     * @param collection
     * @return
     */
    @SuppressWarnings("unchecked")
    private static <C extends Collection<?>,E> C propertize(C collection) {
        if (collection instanceof List) {
            List<E> list = (List<E>) collection;
            List<Property<E>> result = new ArrayList<>();
            if (!list.isEmpty()) {
                if (list.get(0) instanceof String)
                    for (E e : list)
                        result.add((Property<E>) new SimpleStringProperty((String) e));
                else if (list.get(0) instanceof Integer)
                    for (E e : list)
                        result.add((Property<E>) new SimpleIntegerProperty((Integer) e));
                else if (list.get(0) instanceof Double)
                    for (E e : list)
                        result.add((Property<E>) new SimpleDoubleProperty((Double) e));
                else if (list.get(0) instanceof Float)
                    for (E e : list)
                        result.add((Property<E>) new SimpleFloatProperty((Float) e));
                else if (list.get(0) instanceof Boolean)
                    for (E e : list)
                        result.add((Property<E>) new SimpleBooleanProperty((Boolean) e));
            }
            return (C) result;
        }
        else if (collection instanceof TreeSet) {
            TreeSet<E> set = (TreeSet<E>) collection;
            TreeSet<Property<E>> result = new TreeSet<>();
            if (!set.isEmpty()) {
                if (set.first() instanceof String)
                    for (E e : set)
                    result.add((Property<E>) new SimpleStringProperty((String) e));
                else if (set.first() instanceof Integer)
                    for (E e : set)
                    result.add((Property<E>) new SimpleIntegerProperty((Integer) e));
                else if (set.first() instanceof Double)
                    for (E e : set)
                    result.add((Property<E>) new SimpleDoubleProperty((Double) e));
                else if (set.first() instanceof Float)
                    for (E e : set)
                    result.add((Property<E>) new SimpleFloatProperty((Float) e));
                else if (set.first() instanceof Boolean)
                    for (E e : set)
                    result.add((Property<E>) new SimpleBooleanProperty((Boolean) e));
            }
            return (C) result;
        }
        throw new UnsupportedOperationException("Unsupported collection type");
    }

    private static Map<LocalDateProperty,Contact> propertize(Map<LocalDate,Contact> map) {
        Map<LocalDateProperty,Contact> result = new TreeMap<>();
        for (Map.Entry<LocalDate, Contact> entry : map.entrySet())
            result.put(new LocalDateProperty(entry.getKey()) , entry.getValue());
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <E> ListProperty<Property<E>> packList(List<E> list) {
        ListProperty<Property<E>> result = new SimpleListProperty<>();
        result.get().addAll((List<Property<E>>) propertize(list));
        return result;
    }

    public static <E> List<E> unPackList(ListProperty<Property<E>> lp){
      List<E> result = new ArrayList<>();
      for (Property<E> property : lp.get()) {
        result.add(property.getValue());
      }
      return result;
    }

    @SuppressWarnings("unchecked")
    public static <E> SetProperty<Property<E>> packSortedSet(TreeSet<E> set) {
        SetProperty<Property<E>> result = new SimpleSetProperty<>(FXCollections.observableSet(new TreeSet<>()));
        result.get().addAll((TreeSet<Property<E>>) propertize(set));
        return result;
    }

    public static <E> TreeSet<E> unPackSortedSet(SetProperty<Property<E>> set) {
        TreeSet<E> result = new TreeSet<>();
        for (Property<E> property : set) {
            result.add(property.getValue());
        }
        return result;
    }

    public static MapProperty<LocalDateProperty,Contact> packSortedMap(TreeMap<LocalDate,Contact> map) {
        MapProperty<LocalDateProperty,Contact> result = new SimpleMapProperty<>(FXCollections.observableMap(new TreeMap<>()));
        result.get().putAll(propertize(map));
        return result;
    }

    public static TreeMap<LocalDate,Contact> unPackMap(MapProperty<LocalDateProperty,Contact> map) {
        TreeMap<LocalDate,Contact> result = new TreeMap<>();
        for (Map.Entry<LocalDateProperty,Contact> entry : map.entrySet()) {
            result.put(entry.getKey().get(), entry.getValue());
        }
        return result;
    }
}
