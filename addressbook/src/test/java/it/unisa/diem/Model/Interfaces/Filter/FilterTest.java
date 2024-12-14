package it.unisa.diem.Model.Interfaces.Filter;


import static org.junit.jupiter.api.Assertions.*;

import it.unisa.diem.Model.Contact;
import it.unisa.diem.Model.Interfaces.Filter.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FilterTest {

    private Contact contact1;
    private Contact contact2;
    private StringProperty substring;

    @BeforeEach
    void setUp() {
        contact1 = new Contact("Elettra", "Palmisano", new String[]{"elettra.palminsano@unisa.it"}, new String[]{"1234567890"});
        contact2 = new Contact("ge", "landi", new String[]{"g.landi@unisa.it"}, new String[]{"334221933"});
        substring = new SimpleStringProperty("uni");
    }

    @Test
    void testBaseFilter() {
        BaseFilter baseFilter = new BaseFilter(substring);

        // Positive case
        assertTrue(baseFilter.test(contact1), "BaseFilter should match the contact's name.");
        assertTrue(baseFilter.test(contact2), "BaseFilter should match the contact's name.");
        
        substring.set("Maurizio");
        assertTrue(baseFilter.test(contact1), "BaseFilter should match the contact's name.");
        assertTrue(baseFilter.test(contact2), "BaseFilter should match the contact's name.");
        
        substring.set("");
        assertTrue(baseFilter.test(contact1), "BaseFilter should match the contact's name.");
        assertTrue(baseFilter.test(contact2), "BaseFilter should match the contact's name.");
        
        // Negative case
        substring.set(null);
        assertFalse(baseFilter.test(contact1), "BaseFilter should not match the contact's name.");
        assertFalse(baseFilter.test(contact2), "BaseFilter should not match the contact's name.");
    }

    @Test
    void testEmailFilter() {
        BaseFilter baseFilter = new BaseFilter(new SimpleStringProperty("unisa.it"));
        EmailFilter emailFilter = new EmailFilter(baseFilter);

        // Positive case
        assertTrue(emailFilter.test(contact1), "EmailFilter should match the contact's email.");
        assertTrue(emailFilter.test(contact2), "EmailFilter should match the contact's email.");
        
        baseFilter.getSubstring().set("sa");
        assertTrue(emailFilter.test(contact1), "EmailFilter should match the contact's email.");
        assertTrue(emailFilter.test(contact2), "EmailFilter should match the contact's email.");
        
        // Negative case
        baseFilter.getSubstring().set("polimi");
        assertFalse(emailFilter.test(contact1), "EmailFilter should not match the contact's email.");
        assertFalse(emailFilter.test(contact1), "EmailFilter should not match the contact's email.");
    }

    @Test
    void testPhoneFilter() {
        BaseFilter baseFilter = new BaseFilter(new SimpleStringProperty("123"));
        PhoneFilter phoneFilter = new PhoneFilter(baseFilter);

        // Positive case
        assertTrue(phoneFilter.test(contact1), "PhoneFilter should match the contact's phone number.");

        // Negative case
        baseFilter.getSubstring().set("999");
        assertFalse(phoneFilter.test(contact1), "PhoneFilter should not match the contact's phone number.");
    }

    @Test
    void testTagFilter() {
        contact.addTag(new Tag("Friend"));
        BaseFilter baseFilter = new BaseFilter(new SimpleStringProperty("Friend"));
        TagFilter tagFilter = new TagFilter(baseFilter);

        // Positive case
        assertTrue(tagFilter.test(contact1), "TagFilter should match the contact's tag.");

        // Negative case
        baseFilter.getSubstring().set("Colleague");
        assertFalse(tagFilter.test(contact1), "TagFilter should not match the contact's tag.");
    }

    @Test
    void testFilterChaining() {
        BaseFilter baseFilter = new BaseFilter(new SimpleStringProperty("John"));
        EmailFilter emailFilter = new EmailFilter(baseFilter);
        PhoneFilter phoneFilter = new PhoneFilter(emailFilter);

        // Positive case
        assertTrue(phoneFilter.test(contact1), "Filter chaining should match when all filters succeed.");

        // Negative case
        baseFilter.getSubstring().set("Jane");
        assertFalse(phoneFilter.test(contact1), "Filter chaining should fail when any filter fails.");
    }
}
