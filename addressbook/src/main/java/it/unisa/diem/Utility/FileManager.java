package it.unisa.diem.Utility;

import java.io.StreamCorruptedException;
import ezvcard.VCard;
import it.unisa.diem.Model.AddressBook;
public class FileManager {

    public static final String profileListPath = "addressbook/src/main/resources/it/unisa/diem/profile_list.obj";
    public static final String profilePictureDir = "addressbook/src/main/resources/it/unisa/diem/profile_pictures";
    public static final String addressBookDir = "addressbook/src/main/resources/it/unisa/diem/address_books";
    public static final String contactPictureDir = "addressbook/src/main/resources/it/unisa/diem/contact_pictures";

    public static String getProfileListPath() {
        return profileListPath;
    }

    public static String getAddressBookDir() {
        return addressBookDir;
    }

    public static String getContactPictureDir() {
        return contactPictureDir;
    }

    public static String getProfilePictureDir() {
        return profilePictureDir;
    }

    public static <T> T importFromFile(String path) throws StreamCorruptedException, ClassCastException {
        // method implementation
        return null;
    }

    public static <T> void exportToFile(String path, T data) throws StreamCorruptedException, ClassCastException {
        // method implementation
    }

    public static VCard importFromVCard(String path) throws StreamCorruptedException {
        // method implementation
        return null;
    }

    public static void exportAsVCard(String path, AddressBook ab) throws StreamCorruptedException {
        // method implementation
    }
}