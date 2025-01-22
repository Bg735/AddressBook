package it.unisa.diem.Utility;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import ezvcard.VCard;
import ezvcard.io.text.VCardReader;
import it.unisa.diem.Model.AddressBook;
import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Profile;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleStringProperty;

public class FileManagerTest {

    private AddressBook addressBook;

    @BeforeEach
    void setUp() {
        // Initialize AddressBook and add some dummy contacts
        addressBook = new AddressBook();

        Contact contact1 = new Contact("Elli", "Palmi");
        contact1.addEmail("elli.palm@gmail.com");
        contact1.addPhoneNumber("1234567");

        Contact contact2 = new Contact("Debbi", "Villanio");
        contact2.addEmail("debbi.debbi@gmail.com");
        contact2.addPhoneNumber("99881262");

        addressBook.add(contact1);
        addressBook.add(contact2);
    }

    @Test
    void testGenerateAddressBookPath() {
        String path = FileManager.generateAddressBookPath();
        assertNotNull(path);
        assertTrue(path.contains("address_book_"));
        assertTrue(path.endsWith(".obj"));
    }

    @Test
    void testGenerateContactPicturePath() {
        String path = FileManager.generateContactPicturePath("jpg");
        assertNotNull(path);
        assertTrue(path.contains("contactPicture_"));
        assertTrue(path.endsWith(".jpg"));
    }

   

    @Test
    void testGenerateProfilePicturePath() {
        String path = FileManager.generateProfilePicturePath("png");
        assertNotNull(path);
        assertTrue(path.contains("profilePicture_"));
        assertTrue(path.endsWith(".png"));
    }

    

    @Test
    void testImportFromFile_Success() throws IOException {
        Path tempFile = Files.createTempFile("testImport", ".obj");
        List<String> data = Arrays.asList("Test1", "Test2", "Test3");
        FileManager.exportToFile(tempFile.toString(), data);

        List<String> importedData = FileManager.importFromFile(tempFile.toString());
        assertNotNull(importedData);
        assertEquals(data, importedData);

        Files.delete(tempFile);
    }

    @Test
    void testImportFromFile_FileNotFound() {
        assertThrows(IOException.class, () -> {
            FileManager.importFromFile("nonexistent.obj");
        });
    }

    

    @Test
    void testExportAsVCard_InvalidPath() {
        assertThrows(IOException.class, () -> {
            FileManager.exportAsVCard("/invalid/path.vcf", addressBook);
        });

    }
    
    
    @Test
    void testExportAndImportAsVCard() {
        String validPath = "testExport.vcf";
        SetProperty<Contact> pre = addressBook.contacts();
        int preSize = pre.getSize();
        assertDoesNotThrow(() -> FileManager.exportAsVCard(validPath, addressBook));
        AddressBook imported = null;
        try{
            imported = FileManager.importFromVCard(validPath);
        } catch (StreamCorruptedException fail) {
            fail();
        }
        SetProperty<Contact> post = imported.contacts();
        int postSize = post.getSize();
        assertEquals(preSize, postSize);
    }
}
