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
    private AddressBook addressbook;
    private AddressBook readAddressBook;
    private AddressBook a;
    private String path1 = "/Users/mauriziomelillo/UNISA - FILE/PROGETTI IDS/AddressBook/addressbook/src/test/java/it/unisa/diem/TestAddressBook/ReadTry.txt";
    private String path2 = null;
    private SetProperty<SafeContact> contactsListTest;
    private MapProperty<Tag, SetProperty<SafeContact>> tagMapTest;
    private RecentlyDeleted recentlyDeletedTest;
    private Contact c;
    private Tag t;

    @BeforeEach
    public void setUp(){
        c = new Contact("Mario","Rossi");
        t = new Tag();
        t.setName("Unisa");
        readAddressBook = new AddressBook();
        addressbook = new AddressBook(path1);
        a = new AddressBook(path2);
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
        assertNotNull(path2);
        assertNotNull(a);
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
        assertNotNull(c);
        int preSize = addressbook.contacts().size();
        addressbook.add(c);
        assertEquals(addressbook.contacts().size(),preSize+1);
        assertTrue(addressbook.contacts().contains(c));
    }

    @Test
    public void testDelete(){
        assertNotNull(c);
        int preSize = addressbook.contacts().size();
        addressbook.delete(c);
        assertEquals(addressbook.contacts().size(),preSize-1);
        assertFalse(addressbook.contacts().contains(c));
    }

    @Test
    public void testRestore(){
        assertNotNull(c);
        assertTrue(addressbook.trashCan().get().values().contains(c));
        int preSize = addressbook.contacts().size();
        addressbook.restore(c);
        assertTrue(addressbook.contacts().contains(c));
        assertFalse(addressbook.trashCan().get().values().contains(c));
        assertEquals(addressbook.contacts().size(),preSize+1);
    }

    @Test
    public void testAddTagToContact(){
        assertNotNull(t);
        assertNotNull(c);
        addressbook.addTagToContact(t,c);
        assertTrue(addressbook.getTagMap().containsKey(t));
        SetProperty<Contact> verifySet = addressbook.getTagMap().get(t);
        assertTrue(verifySet.contains(c)); 
    }
    
    @Test
    public void testRemoveTagFromContact(){
        assertNotNull(t);
        assertNotNull(c);
        addressbook.removeTagFromContact(t,c);
        SetProperty<Contact> verifySet = addressbook.getTagMap().get(t);
        assertFalse(verifySet.contains(c)); 
        if(verifySet == null){
            assertFalse(addressbook.getTagMap().containsKey(t));
        }
    }

    @Test
    public void testReadFromFile(){
        addressbook.add(c);
        addressbook.writeToFile(path1);
        readAddressBook = addressbook.readFromFile(path1);

        assertNotNull(readAddressBook.contacts(), "Contacts list shouldn't be null.");
        assertTrue(readAddressBook.contacts().contains(c), "Contacts list should contains the added contact.");

    }

    @Test
    public void testwriteToFile(){
        addressbook.add(c);
        assertDoesNotThrow((Executable) () -> addressbook.writeToFile(path1));
        
        File file = new File(path1);
        assertTrue(file.exists(), "Creation of file failed.");

        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(path1))) {

            Object readObject = objectIn.readObject();
            assertNotNull(readObject, "ReadObject is null.");
            assertTrue(readObject instanceof AddressBook, "ReadObject is not an AddressBook type.");

        } catch (IOException | ClassNotFoundException e) {
            fail("Reading from file failed." + e.getMessage());
        }
    }

    @Test
    public void testGet(){
        addressbook.add(c);
        assertEquals(addressbook.get(c),c);
    }
    
}








