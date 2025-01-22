package it.unisa.diem.Model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.function.Executable;

public class AddressBookTest {
    private AddressBook addressbookWithPath;
    private AddressBook addressbook;
    private AddressBook readAddressBook;
    private String path1 = "/Users/mauriziomelillo/UNISA - FILE/PROGETTI IDS/AddressBook/addressbook/src/test/java/it/unisa/diem/TestAddressBook/ReadTry.txt";
    private Contact c1;
    private Contact c2;
    private Tag t;

    @BeforeEach
    public void setUp(){
        c1 = new Contact("Mario","Rossi");
        c2 = new Contact("Mario","Bianchi");
        t = new Tag();
        t.setName("Unisa");
        readAddressBook = new AddressBook();
        addressbookWithPath = new AddressBook(path1);
        addressbook = new AddressBook();
    }

    @Test
    public void TestConstructor(){
        assertNotNull(addressbook.contacts());
        assertNotNull(addressbook.getTagMap());
        assertNotNull(addressbook.trashCan());
    }

    @Test
    public void TestConstructorFromPath(){
        assertNotNull(path1);
        assertNotNull(addressbook);
    }

    @Test
    public void testContacts(){
        assertNotNull(addressbook.contacts());
    }

    @Test
    public void testGetTagMap(){
        assertNotNull(addressbook.getTagMap());
    }

    @Test
    public void testTrashCan(){
        assertNotNull(addressbook.trashCan());
    }

    @Test
    public void testAdd(){
        assertNotNull(c1);
        int preSize = addressbook.contacts().size();
        addressbook.add(c1);
        assertEquals(addressbook.contacts().size(),preSize+1);
        assertTrue(addressbook.contacts().contains(c1));
    }

    
    @Test
    public void testAddToTagMap(){
        assertNotNull(c1);
        assertNotNull(t);
        addressbook.add(c1);
        addressbook.addTagToContact(t,c1);
        assertTrue(addressbook.getTagMap().get(t).get().contains(c1));
    }
    
    @Test
    public void testRemoveFromTagMap() {
        assertNotNull(c1);
        assertNotNull(t);
        assertNotNull(c2);
        addressbook.add(c1);
        addressbook.add(c2);
        addressbook.addTagToContact(t,c1);
        addressbook.addTagToContact(t,c2);
        addressbook.removeFromTagMap(c1);
        assertFalse(addressbook.getTagMap().get(t).get().contains(c1));
    }
    
    
    @Test
    public void testRestore(){
        assertNotNull(c1);
        addressbook.trashCan().put(c1);
        int preSize = addressbook.contacts().size();
        addressbook.restore(c1);
        assertTrue(addressbook.contacts().contains(c1));
        assertEquals(addressbook.contacts().size(),preSize+1);
    }

    @Test
    public void testAddTagToContact(){
        assertNotNull(t);
        assertNotNull(c1);
        addressbook.addTagToContact(t,c1);
        assertTrue(addressbook.getTagMap().containsKey(t));
        SetProperty<Contact> verifySet = addressbook.getTagMap().get(t);
        assertTrue(verifySet.contains(c1)); 
    }
    
    @Test
    public void testRemoveTagFromContact(){
        assertNotNull(t);
        assertNotNull(c1);
        assertNotNull(c2);
        addressbook.addTagToContact(t,c1);
        addressbook.addTagToContact(t,c2);
        addressbook.removeTagFromContact(t,c1);
        assertFalse(addressbook.getTagMap().get(t).get().contains(c1));
    }



    @Test
    public void testGet(){
        addressbook.add(c1);
        assertEquals(addressbook.get(c1),c1);
    }
    
}








