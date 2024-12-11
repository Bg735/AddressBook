package it.unisa.diem.Model;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.beans.property.SimpleStringProperty;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 */
public class ProfileTest {
    
    public Profile profile; 
    
    @BeforeEach
    public void setUp() throws IOException {
        profile = new Profile(new SimpleStringProperty("profileName"), new SimpleStringProperty("1234567890"), new SimpleStringProperty("pic/path"), new SimpleStringProperty("address/book/path"));
    }
    
    @Test
    public void setNameValidTest() {
        assertTrue(profile.setName("ValidName"), "Name is valid."); 
    }
    
    @Test
    public void setNameInvalidTest() {
        assertTrue(!profile.setName("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"), "Name is invalid."); 
    }
    
    
    @Test
    public void setPhoneValid1Test() {
        assertTrue(profile.setName("1234567890"), "Phone is valid."); 
    }
    
    @Test
    public void setPhoneValid2Test() {
        assertTrue(profile.setName("123456789"), "Phone is valid."); 
    }
    
    @Test
    public void setPhoneInvalid1Test() {
        assertTrue(profile.setName("12345"), "Phone is invalid."); 
    }
    
    @Test
    public void setPhoneInvalid2Test() {
        assertTrue(profile.setName("1234567891011"), "Phone is invalid."); 
    }
    
    
    @Test
    public void setAddressBookPathValidTest() {
        assertThrows(IOException.class, () -> profile.setAddressBookPath("src/test/resources/address_books/address_book_1.obj"),"AddressBook path is valid. "); 
    }
    
    @Test
    public void setAddressBookPathInvalidTest() {
        assertThrows(IOException.class, () -> profile.setAddressBookPath("src/test/resources/address_books/address_book_55.obj"),"AddressBook path is valid. "); 
    }
    
    
    @Test
    public void setProfilePictureValidTest() throws FileNotFoundException {
        assertThrows(FileNotFoundException.class, () -> profile.setProfilePicture("src/test/resources/profile_pictures/briciola.JPEG"), "Profile picture is valid. ");
    }
    
    @Test
    public void setProfilePictureInvalidTest() {
        assertThrows(IOException.class, () -> profile.setProfilePicture("src/test/resources/profile_pictures/doesNotExist.JPEG"), "Profile picture is invalid. ");
    }
    
}
