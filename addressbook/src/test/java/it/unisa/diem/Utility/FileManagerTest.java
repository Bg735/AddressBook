package it.unisa.diem.Utility;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import ezvcard.VCard;
import ezvcard.property.StructuredName;
import it.unisa.diem.Model.Contact;

public class FileManagerTest {

    private AddressBook addressBook;

    @BeforeEach
    void setUp() {
        // Initialize AddressBook and add some dummy contacts
        addressBook = new AddressBook();

        Contact contact1 = new Contact("Elli", "Palmi", new String[]{"elli.palm@gmail.com"}, new String[]{"123456789"}, new HashSet<>(), "");
        Contact contact2 = new Contact("Debbi", "Villano", new String[]{"debbi.rosa@hotmail.com"}, new String[]{"987654321"}, new HashSet<>(), "");

        addressBook.add(contact1);
        addressBook.add(contact2);
    }

    @Test
    void testExportAsVCard_Success() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testAddressBook", ".vcf");

            // Call the method to test
            AddressBook.exportAsVCard(tempFile.toString(), addressBook);

            // Validate the exported file
            assertTrue(Files.exists(tempFile));

            List<String> fileContents = Files.readAllLines(tempFile);
            assertFalse(fileContents.isEmpty(), "File should not be empty");
            assertTrue(fileContents.get(0).startsWith("BEGIN:VCARD"), "File should start with a VCARD entry");
        } catch (IOException e) {
            fail("IOException during test: " + e.getMessage());
        } finally {
            if (tempFile != null) {
                try {
                    Files.delete(tempFile);
                } catch (IOException ignored) {
                }
            }
        }
    }

    @Test
    void testExportAsVCard_EmptyAddressBook() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testAddressBook", ".vcf");

            // Create an empty AddressBook
            AddressBook emptyAddressBook = new AddressBook();

            // Call the method to test
            AddressBook.exportAsVCard(tempFile.toString(), emptyAddressBook);

            // Validate the exported file
            assertTrue(Files.exists(tempFile));

            List<String> fileContents = Files.readAllLines(tempFile);
            assertTrue(fileContents.isEmpty(), "File should be empty for an empty address book");
        } catch (IOException e) {
            fail("IOException during test: " + e.getMessage());
        } finally {
            if (tempFile != null) {
                try {
                    Files.delete(tempFile);
                } catch (IOException ignored) {
                }
            }
        }
    }
    
    @Test
    void testImportFromFile_Success() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testImport", ".obj");

            // Export AddressBook to file
            FileManager.exportToFile(tempFile.toString(), addressBook);

            // Import AddressBook from file
            AddressBook importedAddressBook = FileManager.importFromFile(tempFile.toString());

            assertNotNull(importedAddressBook);
            assertEquals(addressBook.contacts().size(), importedAddressBook.contacts().size());
        } catch (IOException | ClassCastException e) {
            fail("Exception during test: " + e.getMessage());
        } finally {
            if (tempFile != null) {
                try {
                    Files.delete(tempFile);
                } catch (IOException ignored) {
                }
            }
        }
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
}
