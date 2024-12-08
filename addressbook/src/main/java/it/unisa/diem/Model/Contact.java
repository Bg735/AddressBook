package it.unisa.diem.Model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import it.unisa.diem.Model.Interfaces.Taggable;
import javafx.beans.property.SetProperty;
import javafx.beans.property.StringProperty;

public class Contact implements Comparable<Contact>, Serializable, Taggable {
    public static final int MAXEMAILS = 3;
    public static final int MAXPHONENUMBERS = 3;
    public static final int MAXNAMELEN = 3;
    public static final int MAXSURNAMELEN = 3;

    private transient StringProperty name;
    private transient StringProperty surname;
    private transient StringProperty fullName;
    private String[] email;
    private String[] phoneNumber;
    private transient SetProperty<Tag> tags;
    private String picture;

    public Contact() {
        // TODO: Implement this method
    }

    public Contact(String name, String surname) {
        // TODO: Implement this method
    }

    public Contact(String name, String surname, String... phoneNumber) {
        // TODO: Implement this method
    }

    public void setName(String name) {
        // TODO: Implement this method
    }

    public StringProperty getName() {
        // TODO: Implement this method
        return null;
    }

    public String getNameValue() {
        // TODO: Implement this method
        return null;
    }

    public void setSurname(String surname) {
        // TODO: Implement this method
    }

    public StringProperty getSurname() {
        // TODO: Implement this method
        return null;
    }

    public String getSurnameValue() {
        // TODO: Implement this method
        return null;
    }

    public boolean setPicture(String picture) {
        // TODO: Implement this method
        return false;
    }

    public String getPicture() {
        // TODO: Implement this method
        return null;
    }

    public String[] getEmailList() {
        // TODO: Implement this method
        return null;
    }

    public String getEmailAtIndex(int index) {
        // TODO: Implement this method
        return null;
    }

    public String[] getPhoneNumberList() {
        // TODO: Implement this method
        return null;
    }

    public String getPhoneNumberAtIndex(int index) {
        // TODO: Implement this method
        return null;
    }

    public SetProperty<Tag> getTagsSet() {
        // TODO: Implement this method
        return null;
    }

    public boolean addEmail(String... email) {
        // TODO: Implement this method
        return false;
    }

    public boolean setEmail(String email, int index)  {
        // TODO: Implement this method
        return false;
    }

    public void removeEmailAtIndex(int index) {
        // TODO: Implement this method
    }

    public boolean addPhoneNumber(String... phoneNumber) {
        // TODO: Implement this method
        return false;
    }

    public boolean setPhoneNumber(String phoneNumber, int index) {
        // TODO: Implement this method
        return false;
    }

    public void removePhoneNumberAtIndex(int index) {
        // TODO: Implement this method
    }

    public boolean addTag(Tag tag) {
        // TODO: Implement this method
        return false;
    }

    public boolean removeTag(Tag tag) {
        // TODO: Implement this method
        return false;
    }

    public String[] getEmail() {
        // TODO: Implement this method
        return null;
    }

    public static int getMaxEmails() {
        // TODO: Implement this method
        return 0;
    }

    public String[] getPhoneNumber() {
        // TODO: Implement this method
        return null;
    }

    public static int getMaxPhonenumbers() {
        // TODO: Implement this method
        return 0;
    }

    public SetProperty<Tag> getTags() {
        // TODO: Implement this method
        return null;
    }

    public StringProperty getFullName() {
        // TODO: Implement this method
        return null;
    }

    public String getFullNameValue() {
        // TODO: Implement this method
        return null;
    }

    public boolean containsName(String str) {
        // TODO: Implement this method
        return false;
    }

    public boolean containsEmail(String str) {
        // TODO: Implement this method
        return false;
    }

    public boolean containsPhone(String str) {
        // TODO: Implement this method
        return false;
    }

    public boolean containsTag(String str) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public String toString() {
        // TODO: Implement this method
        return null;
    }

    @Override
    public int compareTo(Contact other) {
        // TODO: Implement this method
        return 0;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        // TODO: Implement this method
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // TODO: Implement this method
    }
}