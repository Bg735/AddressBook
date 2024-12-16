package it.unisa.diem.Model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SafeContactTest {

    @Test
    void testSafeContactDefaultCreation() {
        SafeContact contact = SafeContact.safeContact();
        assertNotNull(contact);
    }

    @Test
    void testSafeContactWithValidNameAndSurname() {
        SafeContact contact = SafeContact.safeContact("Mario", "Rossi");
        assertNotNull(contact);
        assertEquals("Mario", contact.getNameValue());
        assertEquals("Rossi", contact.getSurnameValue());
    }

    @Test
    void testSafeContactWithInvalidNameAndSurname() {
        SafeContact contact1 = SafeContact.safeContact("", "");
        assertNull(contact1);

        SafeContact contact2 = SafeContact.safeContact(null, null);
        assertNull(contact2);
    }

    @Test
    void testSetValidName() {
        SafeContact contact = SafeContact.safeContact();
        assertTrue(contact.setName("Mario"));
    }

    @Test
    void testSetInvalidName() {
        SafeContact contact = SafeContact.safeContact();
        assertFalse(contact.setName(""));
        assertFalse(contact.setName(null));
    }

    @Test
    void testAddValidEmail() {
        SafeContact contact = SafeContact.safeContact();
        assertTrue(contact.addEmail("test@example.com"));
    }

    @Test
    void testAddInvalidEmail() {
        SafeContact contact = SafeContact.safeContact();
        assertFalse(contact.addEmail("invalid-email"));
    }

    @Test
    void testAddValidPhoneNumber() {
        SafeContact contact = SafeContact.safeContact();
        assertTrue(contact.addPhoneNumber("+393349807765"));
    }

    @Test
    void testAddInvalidPhoneNumber() {
        SafeContact contact = SafeContact.safeContact();
        assertFalse(contact.addPhoneNumber("123"));
    }
}