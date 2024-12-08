package it.unisa.diem.Model;

public class SafeContact extends Contact {

    public SafeContact() {
        // TODO: Implement this method
    }

    public SafeContact(String name, String surname) {
        // TODO: Implement this method
    }

    public SafeContact(String name, String surname, String... phoneNumber) {
        // TODO: Implement this method
    }

    @Override
    public void setName(String name) {
        // TODO: Implement this method
    }

    @Override
    public void setSurname(String surname) {
        // TODO: Implement this method
    }

    @Override
    public boolean setPicture(String picture) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public boolean addEmail(String... email) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public boolean setEmail(String email, int index) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public void removeEmailAtIndex(int index) {
        // TODO: Implement this method
    }

    @Override
    public boolean addPhoneNumber(String... phoneNumber) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public boolean setPhoneNumber(String phoneNumber, int index) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public void removePhoneNumberAtIndex(int index) {
        // TODO: Implement this method
    }

    @Override
    public boolean addTag(Tag tag) {
        // TODO: Implement this method
        return false;
    }

    @Override
    public boolean removeTag(Tag tag) {
        // TODO: Implement this method
        return false;
    }
}