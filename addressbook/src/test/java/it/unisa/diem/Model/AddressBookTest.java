package test.java.it.unisa.diem.Model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;

public class AddressBookTest {
    private AddressBook addressbook;
    private String path1 = "/Users/mauriziomelillo/UNISA - FILE/PROGETTI IDS/TestAddressBook/ReadTry.txt";
    private String path2 = null;
    private SetProperty<SafeContact> contactsListTest;
    private MapProperty<Tag, SetProperty<SafeContact>> tagMapTest;
    private RecentlyDeleted recentlyDeletedTest;
    private SafeContact c;
    private Tag t;

    public void setUp(){
        c = new Contact("Mario","Rossi","Mario Rossi","mariorossi@gmail.com","347584383","lavoro");
        t = new Tag("UNISA");
    }
    
    public void TestConstructor(){
        addressbook = new AddressBook();
        assertNull(addressbook.contactsList);
        assertNull(addressbook.tagMap);
        assertNull(addressbook.recentlyDeleted);
    }

    public void TestConstructorFromPath(){
        addressbook = new AddressBook(path1);
        assertNotNull(path1);
        assertNotNull(addressbook);

        AddressBook a = new AddressBook(path2);
        assertNull(path2);
        assertNull(a);
    }

    public void testContacts(){
        contactsListTest = new SimpleSetProperty<>(FXCollections.observableSet(new TreeSet<SafeContact>()));
        assertNotNull(addressbook.contactsList);
        contactsListTest = addressbook.contacts;
        assertEquals(contactsListTest,addressbook.contactsList);
    }

    public void testGetTagMap(){
        tagMapTest =  new SimpleMapProperty<>(FXCollections.observableMap(new TreeMap<Tag,SetProperty<SafeContact>>()));
        assertNotNull(addressbook.tagMap);
        tagMapTest = addressbook.getTagMap;
        assertEquals(tagMapTest,addressbook.tagMap);
    }

    public void testTrashCan(){
        assertNotNull(addressbook.recentlyDeleted);
        recentlyDeletedTest = addressbook.trashCan;
        assertEquals(recentlyDeletedTest,addressbook.recentlyDeleted);
    }

    public void testAdd(){
        assertNotNull(c);
        int preSize = addressbook.contactsList.size;
        addressbook.add(c);
        assertEquals(addressbook.contactsList.size,preSize+1);
        assertTrue(addressbook.contactsList.contains(c));
    }

    public void testDelete(){
        assertNotNull(c);
        int preSize = addressbook.contactsList.size;
        addressbook.delete(c);
        assertEquals(addressbook.contactsList.size,preSize-1);
        assertFalse(addressbook.contactsList.contains(c));
    }

    public void testRestore(){
        assertNotNull(c);
        assertTrue(addressbook.recentlyDeleted.contains(c));
        int preSize = addressbook.contactsList.size;
        addressbook.restore(c);
        assertTrue(addressbook.contactsList.contains(c));
        assertFalse(addressbook.recentlyDeleted.contains(c));
        assertEquals(addressbook.contactsList.size,preSize+1);
    }

    public void testAddTagToContact(){
        assertNotNull(t);
        assertNotNull(c);
        addressbook.addTagToContact(t,c);
        assertTrue(addressbook.tagMap.contains(t));
        SetProperty<SafeContact> verifySet = addressbook.tagMap.get(t);
        assertTrue(verifySet.contains(c)); 
    }
    
    public void testRemoveTagFromContact(){
        assertNotNull(t);
        assertNotNull(c);
        addressbook.removeTagFromContact(t,c);
        SetProperty<SafeContact> verifySet = addressbook.tagMap.get(t);
        assertFalse(verifySet.contains(c)); 
        if(verifySet == null){
            assertFalse(addressbook.tagMap.contains(t));
        }
    }

    public void testReadFromFile(){
        addressbook.add(c);
        addressbook.writeToFile(path1);
        addressbook.readFromFile(path1);

    }

    public void testwriteToFile(){

    }

    public void testGet(){

    }
    
}




