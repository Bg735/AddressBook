package test.java.it.unisa.diem.Model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;

public class AddressBookTest {
    private AddressBook addressbook;
    private Addressbook readAddressBook;
    private AddressBook a;
    private String path1 = "/Users/mauriziomelillo/UNISA - FILE/PROGETTI IDS/AddressBook/addressbook/src/test/java/it/unisa/diem/TestAddressBook/ReadTry.txt";
    private String path2 = null;
    private SetProperty<SafeContact> contactsListTest;
    private MapProperty<Tag, SetProperty<SafeContact>> tagMapTest;
    private RecentlyDeleted recentlyDeletedTest;
    private SafeContact c;
    private Tag t;

    @BeforeEach
    public void setUp(){
        c = new SafeContact("Mario","Rossi","Mario Rossi","mariorossi@gmail.com","347584383","lavoro");
        t = new Tag("UNISA");
        readAddressBook = new AddressBook();
        addressbook = new AddressBook(path1);
        a = new AddressBook(path2);
    }

    @Test
    public void TestConstructor(){
        assertNotNull(addressbook.contactsList);
        assertNotNull(addressbook.tagMap);
        assertNotNull(addressbook.recentlyDeleted);
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
        contactsListTest = new SimpleSetProperty<>(FXCollections.observableSet(new TreeSet<SafeContact>()));
        assertNotNull(addressbook.contactsList);
        contactsListTest = addressbook.contacts;
        assertEquals(contactsListTest,addressbook.contactsList);
    }

    @Test
    public void testGetTagMap(){
        tagMapTest =  new SimpleMapProperty<>(FXCollections.observableMap(new TreeMap<Tag,SetProperty<SafeContact>>()));
        assertNotNull(addressbook.tagMap);
        tagMapTest = addressbook.getTagMap;
        assertEquals(tagMapTest,addressbook.tagMap);
    }

    @Test
    public void testTrashCan(){
        assertNotNull(addressbook.recentlyDeleted);
        recentlyDeletedTest = addressbook.trashCan;
        assertEquals(recentlyDeletedTest,addressbook.recentlyDeleted);
    }

    @Test
    public void testAdd(){
        assertNotNull(c);
        int preSize = addressbook.contactsList.size;
        addressbook.add(c);
        assertEquals(addressbook.contactsList.size,preSize+1);
        assertTrue(addressbook.contactsList.contains(c));
    }

    @Test
    public void testDelete(){
        assertNotNull(c);
        int preSize = addressbook.contactsList.size;
        addressbook.delete(c);
        assertEquals(addressbook.contactsList.size,preSize-1);
        assertFalse(addressbook.contactsList.contains(c));
    }

    @Test
    public void testRestore(){
        assertNotNull(c);
        assertTrue(addressbook.recentlyDeleted.contains(c));
        int preSize = addressbook.contactsList.size;
        addressbook.restore(c);
        assertTrue(addressbook.contactsList.contains(c));
        assertFalse(addressbook.recentlyDeleted.contains(c));
        assertEquals(addressbook.contactsList.size,preSize+1);
    }

    @Test
    public void testAddTagToContact(){
        assertNotNull(t);
        assertNotNull(c);
        addressbook.addTagToContact(t,c);
        assertTrue(addressbook.tagMap.contains(t));
        SetProperty<SafeContact> verifySet = addressbook.tagMap.get(t);
        assertTrue(verifySet.contains(c)); 
    }
    
    @Test
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
        assertDoesNotThrow(() -> addressBook.writeToFile(path1));
        
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

    @test
    public void testGet(){
        addressbook.add(c);
        assertEquals(addressbook.get(c),c);
    }
    
}








