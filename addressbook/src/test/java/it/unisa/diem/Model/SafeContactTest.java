
package it.unisa.diem.Model;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SafeContactTest {
    
    private SafeContact contact;

    @BeforeEach
    void setUp() {
        contact = contact.safeContact();
    }

    @Test
    void safeContactNameSurnameTestValid1() {
        contact = contact.safeContact("Mario", "Rossi");
        assertNotNull(contact);
        assertEquals("Mario", contact.getNameValue());
        assertEquals("Rossi", contact.getSurnameValue());
    }
    
    @Test 
    void safeContactNameSurnameTestValid2() {
        contact = contact.safeContact("", "Rossi");
        assertNotNull(contact);
        assertEquals("", contact.getNameValue());
        assertEquals("Rossi", contact.getSurnameValue());
    }
        
    @Test
    void safeContactNameSurnameTestInvalid() {
        contact = contact.safeContact("", "");
        assertNull(contact);
        
        contact = contact.safeContact(null, null);
        assertNull(contact);
        
        contact = contact.safeContact("A very very long name that exceeds the character limit.", "Rossi");
        assertNull(contact);
    }

     @Test
    void setNameTestValid() {
        assertTrue(contact.setName("Mario"));
        assertEquals("Mario", contact.getNameValue());
    }

    @Test
    void setNameTestInvalid() {
        assertFalse(contact.setName(null)); 
        
        contact = contact.safeContact("Mario", ""); 
        assertFalse(contact.setName(""));
        
        assertFalse(contact.setName("A very very long name that exceeds the character limit."));
    }
    
    @Test
    void setSurnameTestValid() {
        assertTrue(contact.setSurname("Rossi"));
        assertEquals("Rossi", contact.getSurnameValue());
    }

    @Test
    void setSurnameTestInvalid() {
        assertFalse(contact.setSurname(null));

        contact = contact.safeContact("", "Rossi"); 
        assertFalse(contact.setSurname(""));

        assertFalse(contact.setSurname("A very very long surname that exceeds the character limit."));
    }
    
    @Test
    void addEmailTestValid() {
        assertTrue(contact.addEmail("test@example.com", "another@example.com"));
        assertArrayEquals(new String[] {"test@example.com", "another@example.com"}, contact.getEmailList());
    }

    @Test
    void addEmailTestInvalid() {
        assertFalse(contact.addEmail("invalidEmail", "another@example.com"));
    }

    @Test
    void addPhoneNumberTestValid() {
        assertTrue(contact.addPhoneNumber("3331234567", "3347654321", "123456789"));
        assertArrayEquals(new String[] {"3331234567", "3347654321", "123456789"}, contact.getPhoneNumberList());
    }

    @Test
    void addPhoneNumberTestInvalid() {
        assertFalse(contact.addPhoneNumber("invalidPhone", "3347654321")); 
    }
    
    @Test
    void setPictureTestValid() throws Exception {
        assertTrue(contact.setPicture("test/resources/profile_pictures.briciola.JPEG"));
        assertEquals("test/resources/profile_pictures.briciola.JPEG", contact.getPicture());
    }

    @Test
    void setPictureTestInvalid() throws IOException {
        assertFalse(contact.setPicture("invalid/path/to/picture"));
    }
    
    @Test
    void setPhoneNumberTestValid() {
        assertTrue(contact.setPhoneNumber("123456789", 1)); 
        assertEquals("123456789", contact.getPhoneNumberAtIndex(1));
    }
    
    @Test
    void setPhoneNumberTestInvalid() {
        assertFalse(contact.setPhoneNumber("123456789101112", 0)); 
    }
    
    @Test
    void setEmailTestValid() {
        assertTrue(contact.setEmail("valid@email", 0)); 
        assertEquals("valid@email", contact.getEmailAtIndex(0));
    }
    
    @Test
    void setEmailTestInvalid() {
        assertFalse(contact.setEmail("invalidemail", 0)); 
    }
    

}
