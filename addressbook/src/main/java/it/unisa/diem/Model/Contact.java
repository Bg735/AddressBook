package it.unisa.diem.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedHashSet;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class Contact implements Serializable, Comparable<Contact> {
    private static final int MAX_EMAILS = 3;
    private static final int MAX_PHONENUMBERS = 3;

    private transient StringProperty name;
    private transient StringProperty surname;
    private transient StringProperty fullName;
    private String[] email;
    private String[] phoneNumber;
    private transient SetProperty<Tag> tags;
    private String picture;

    public Contact() {
        // Method Implementation
    }

    public Contact(String name, String surname) {
        // Method Implementation
    }

    public Contact(String name, String surname, String... phoneNumber) {
        // Method Implementation
    }

    public void setName(String name) {
        // Method Implementation
    }

    public StringProperty getName() {
        // Method Implementation
        return null;
    }

    public String getNameValue() {
        // Method Implementation
        return null;
    }

    public void setSurname(String surname) {
        // Method Implementation
    }

    public StringProperty getSurname() {
        // Method Implementation
        return null;
    }

    public String getSurnameValue() {
        // Method Implementation
        return null;
    }

    public void setPicture(String picture) {
        // Method Implementation
    }

    public String getPicture() {
        // Method Implementation
        return null;
    }

    public String[] getEmailList() {
        // Method Implementation
        return null;
    }

    public String getEmailAtIndex(int index) {
        // Method Implementation
        return null;
    }

    public String[] getPhoneNumberList() {
        // Method Implementation
        return null;
    }

    public String getPhoneNumberAtIndex(int index) {
        // Method Implementation
        return null;
    }

    public SetProperty<Tag> getTagsSet() {
        // Method Implementation
        return null;
    }

    public boolean addEmail(String... email) {
        // Method Implementation
        return false;
    }

    public void setEmail(String email, int index) {
        // Method Implementation
    }

    public void removeEmailAtIndex(int index) {
        // Method Implementation
    }

    public boolean addPhoneNumber(String... phoneNumber) {
        // Method Implementation
        return false;
    }

    public void setPhoneNumber(String phoneNumber, int index) {
        // Method Implementation
    }

    public void removePhoneNumberAtIndex(int index) {
        // Method Implementation
    }

    public void addTag(Tag tag) {
        // Method Implementation
    }

    public void removeTag(Tag tag) {
        // Method Implementation
    }

    public String[] getEmail() {
        // Method Implementation
        return null;
    }

    public static int getMaxEmails() {
        // Method Implementation
        return 0;
    }

    public String[] getPhoneNumber() {
        // Method Implementation
        return null;
    }

    public static int getMaxPhonenumbers() {
        // Method Implementation
        return 0;
    }

    public SetProperty<Tag> getTags() {
        // Method Implementation
        return null;
    }

    public StringProperty getFullName() {
        // Method Implementation
        return null;
    }

    public String getFullNameValue() {
        // Method Implementation
        return null;
    }

    public boolean containsName(String str) {
        // Method Implementation
        return false;
    }

    public boolean containsEmail(String str) {
        // Method Implementation
        return false;
    }

    public boolean containsPhone(String str) {
        // Method Implementation
        return false;
    }

    public boolean containsTag(String str) {
        // Method Implementation
        return false;
    }

    @Override
    public String toString() {
        // Method Implementation
        return null;
    }

    @Override
    public int compareTo(Contact other) {
        // Method Implementation
        return 0;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        // Method Implementation
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Method Implementation
    }
}