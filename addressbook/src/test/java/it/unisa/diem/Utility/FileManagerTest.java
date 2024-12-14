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
import javafx.beans.property.SimpleStringProperty;

public class FileManagerTest {

    private AddressBook addressBook;

    @BeforeEach
    void setUp() {
        // Initialize AddressBook and add some dummy contacts
        addressBook = new AddressBook();

        Contact contact1 = new Contact("Elli", "Palmi", new String[]{"elli.palm@gmail.com"}, new String[]{"123456789"});
        Contact contact2 = new Contact("Debbi", "Villano", new String[]{"debbi.rosa@hotmail.com"}, new String[]{"987654321"});

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
    void testGenerateContactPicturePath_InvalidExtension() {
        assertThrows(NullPointerException.class, () -> {
            FileManager.generateContactPicturePath(null);
        });
        String path = FileManager.generateContactPicturePath("");
        assertTrue(path.endsWith("."));
    }

    @Test
    void testGenerateProfilePicturePath() {
        String path = FileManager.generateProfilePicturePath("png");
        assertNotNull(path);
        assertTrue(path.contains("profilePicture_"));
        assertTrue(path.endsWith(".png"));
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
    void testImportFromFile_InvalidPath() {
        assertThrows(StreamCorruptedException.class, () -> {
            FileManager.importFromFile("invalid_path.obj");
        });
    }

    @Test
    void testExportToFile_Success() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testExport", ".obj");

            // Export AddressBook to file
            FileManager.exportToFile(tempFile.toString(), addressBook);

            assertTrue(Files.exists(tempFile));
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
    void testExportToFile_NullData() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testExport", ".obj");

            // Attempt to export null data
            FileManager.exportToFile(tempFile.toString(), null);
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
            FileManager.exportAsVCard(tempFile.toString(), emptyAddressBook);

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
    void testExportAndImportVCard_AddressBookIntegrity() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testAddressBook", ".vcf");

            // Export AddressBook to vCard
            FileManager.exportAsVCard(tempFile.toString(), addressBook);

            // Import vCard back into a new AddressBook
            AddressBook importedAddressBook = FileManager.importFromVCard(tempFile.toString());

            assertEquals(addressBook.contacts().size(), importedAddressBook.contacts().size(), "The number of contacts should match after export and import.");
            assertTrue(importedAddressBook.contacts().containsAll(addressBook.contacts()), "The imported AddressBook should contain all the original contacts.");
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
    void testImportVCardIntoNonEmptyAddressBook() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testAddressBook", ".vcf");

            // Create a vCard file with additional contacts
            String vCardData = "BEGIN:VCARD\nVERSION:3.0\nFN:John Doe\nEMAIL:johndoe@example.com\nEND:VCARD\n" +
                                "BEGIN:VCARD\nVERSION:3.0\nFN:Jane Smith\nEMAIL:janesmith@example.com\nEND:VCARD";
            Files.writeString(tempFile, vCardData);

            // Import vCard into existing AddressBook
            int initialSize = addressBook.contacts().size();
            AddressBook importedAddressBook = FileManager.importFromVCard(tempFile.toString());

            for (Contact contact : importedAddressBook.contacts()) {
                addressBook.add(contact);
            }

            assertEquals(initialSize + 2, addressBook.contacts().size(), "The AddressBook should contain the sum of original and imported contacts.");
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
    void testImportAddressBookFromObjFile() {
        Path tempFile = null;
        try {
            tempFile = Files.createTempFile("testImportAddressBook", ".obj");

            // Export AddressBook to file
            FileManager.exportToFile(tempFile.toString(), addressBook);

            // Import AddressBook from the file
            AddressBook importedAddressBook = FileManager.importFromFile(tempFile.toString());

            assertNotNull(importedAddressBook, "AddressBook should not be null");
            assertEquals(addressBook.contacts().size(), importedAddressBook.contacts().size(), "The number of contacts should match.");
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
    void testExportAndImportProfileList() {
        Path tempFile = null;
        try {
            // Crea una lista di profili
            List<Profile> profileList = new ArrayList<>();

            Profile profile1 = new Profile(
                    new SimpleStringProperty("Elli Palmi"),
                    new SimpleStringProperty("123456789"),
                    new SimpleStringProperty("profile1.jpg"),
                    new SimpleStringProperty("addressBook1.obj")
            );

            Profile profile2 = new Profile(
                    new SimpleStringProperty("Debbi Villano"),
                    new SimpleStringProperty("987654321"),
                    new SimpleStringProperty("profile2.jpg"),
                    new SimpleStringProperty("addressBook2.obj")
            );

            profileList.add(profile1);
            profileList.add(profile2);

            // Crea un file temporaneo per esportare la lista di profili
            tempFile = Files.createTempFile("testProfileList", ".obj");

            // Esporta la lista di profili nel file
            FileManager.exportToFile(tempFile.toString(), profileList);

            // Importa la lista di profili dal file
            List<Profile> importedProfileList = FileManager.importFromFile(tempFile.toString());

            // Verifica che la lista di profili importata contenga lo stesso numero di profili
            assertNotNull(importedProfileList);
            assertEquals(profileList.size(), importedProfileList.size(), "The number of profiles should match after export and import.");

            // Verifica che i profili siano uguali
            for (int i = 0; i < profileList.size(); i++) {
                Profile originalProfile = profileList.get(i);
                Profile importedProfile = importedProfileList.get(i);

                assertEquals(originalProfile.getName().get(), importedProfile.getName().get(), "Profile names should match.");
                assertEquals(originalProfile.getPhone().get(), importedProfile.getPhone().get(), "Profile phone numbers should match.");
                assertEquals(originalProfile.getProfilePicture(), importedProfile.getProfilePicture(), "Profile pictures should match.");
                assertEquals(originalProfile.getAddressBookPath(), importedProfile.getAddressBookPath(), "Address book paths should match.");
            }
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

}