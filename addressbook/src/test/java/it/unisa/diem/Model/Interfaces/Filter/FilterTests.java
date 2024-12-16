package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import javafx.beans.property.StringProperty;

import static org.junit.jupiter.api.Assertions.*;

class FilterTests {

    private FilteredList<Contact> filteredList;
    private List<Contact> contactList;

    @BeforeEach
    void setUp() {
        contactList = FXCollections.observableArrayList(
            new Contact("Elettra", "Palmisano"),
            new Contact("Gennaro Francesco", "Landi"),
            new Contact("Maurizio", "Melillo"),
            new Contact("Francesco", "Lanzara"),
            new Contact("Luigi", "Gentile")
        );
        filteredList = new FilteredList<>(FXCollections.observableArrayList(contactList));
    }

    @Test
    void testBaseFilter() {
        SimpleStringProperty substring = new SimpleStringProperty("  Francesco   ");
        BaseFilter baseFilter = new BaseFilter(substring);

        // Test substring retrieval
        assertEquals("francesco", baseFilter.getSubstring());

        // Test filtering logic
        Contact contact = new Contact("Gennaro Francesco", "Landi");
        assertTrue(baseFilter.test(contact));

        Contact nullContact = null;
        assertFalse(baseFilter.test(nullContact));
    }
    
    @Test
    void testBaseFilterWithEmptySubstring() {
        StringProperty substring = new SimpleStringProperty("");
        BaseFilter baseFilter = new BaseFilter(substring);

        Contact contact = new Contact("Mario", "Rossi");
        assertFalse(baseFilter.test(contact)); 
    }
    
    @Test
    void testBaseFilterWithNullContact() {
        StringProperty substring = new SimpleStringProperty("Mario");
        BaseFilter baseFilter = new BaseFilter(substring);

        assertFalse(baseFilter.test(null)); 
    }


    @Test
    void testNameFilterLogic() {
        StringProperty substring = new SimpleStringProperty("francesco");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);

        // Controlla logica di filtraggio
        Contact contactMatch = new Contact("Gennaro Francesco", "Landi");
        Contact contactNoMatch = new Contact("Luigi", "Gentile");

        assertTrue(nameFilter.test(contactMatch));
        assertFalse(nameFilter.test(contactNoMatch));
}


    @Test
    void testNameFilterWithFullNameMatch() {
        StringProperty substring = new SimpleStringProperty("Francesco");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);

        Contact contact = new Contact("Francesco", "Lanzara");
        assertTrue(nameFilter.test(contact)); // Francesco dovrebbe corrispondere
    }
    
    @Test
    void testNameFilterWithoutMatch() {
        StringProperty substring = new SimpleStringProperty("NonEsistente");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);

        Contact contact = new Contact("Francesco", "Lanzara");
        assertFalse(nameFilter.test(contact)); // Nessuna corrispondenza
    }
    
    @Test
    void testPhoneFilterWithMatch() {
        StringProperty substring = new SimpleStringProperty("123");
        BaseFilter baseFilter = new BaseFilter(substring);
        PhoneFilter phoneFilter = new PhoneFilter(baseFilter);

        Contact contact = new Contact("Francesco", "Lanzara");
        contact.setPhoneNumber("123456789", 0);
        assertTrue(phoneFilter.test(contact)); // La sottostringa "123" dovrebbe corrispondere al primo numero
    }
    
    @Test
    void testPhoneFilterWithoutMatch() {
        StringProperty substring = new SimpleStringProperty("000");
        BaseFilter baseFilter = new BaseFilter(substring);
        PhoneFilter phoneFilter = new PhoneFilter(baseFilter);

        Contact contact = new Contact("Francesco", "Lanzara");
        contact.setPhoneNumber("123456789",0);
        assertFalse(phoneFilter.test(contact));
    }
    
    @Test
    void testPhoneFilterWithMultipleNumbers() {
        StringProperty substring = new SimpleStringProperty("987");
        BaseFilter baseFilter = new BaseFilter(substring);
        PhoneFilter phoneFilter = new PhoneFilter(baseFilter);

        Contact contact = new Contact("Francesco", "Lanzara");
        contact.setPhoneNumber("123456789", 0);
        contact.setPhoneNumber("987654321", 1);
        assertTrue(phoneFilter.test(contact));
        substring.set("321");
        assertTrue(phoneFilter.test(contact));
    }

    @Test
    void testNameFilterWithLowercase() {
        StringProperty substring = new SimpleStringProperty("lanzara");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);

        Contact contact = new Contact("Francesco", "Lanzara");
        assertTrue(nameFilter.test(contact));
        substring.set("francesco lanzara");
        assertTrue(nameFilter.test(contact));
    }
    
    @Test
    void testNameFilterWithFullName() {
        StringProperty substring = new SimpleStringProperty("elettra palmisano");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);

        Contact contact = new Contact("Elettra", "Palmisano");
        assertTrue(nameFilter.test(contact));
        substring.set("palmisano elettra");
        assertTrue(nameFilter.test(contact));
        substring.set("Palmisano Elettra");
        assertTrue(nameFilter.test(contact));
    }
    
    @Test
    void testNameFilterWithFalseFullName() {
        StringProperty substring = new SimpleStringProperty("gennaro landi");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);

        Contact contact = new Contact("Elettra", "Palmisano");
        assertFalse(nameFilter.test(contact));
        substring.set("landi");
        assertFalse(nameFilter.test(contact));
        substring.set("gennaro");
        assertFalse(nameFilter.test(contact));
    }
    
    @Test
    void testCombinationFilter() {
        StringProperty substring = new SimpleStringProperty("lan");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);
        EmailFilter emailFilter = new EmailFilter(nameFilter);
        
        Contact contact = new Contact("gennaro", "landi");
        contact.addEmail("g.landi@studenti.unisa.it", "landigf.eng@gmail.com");
        contact.addPhoneNumber("3345647741");
        assertTrue(emailFilter.test(contact));
        substring.set("unisa");
        assertTrue(emailFilter.test(contact));
        substring.set("gmail");
        assertTrue(emailFilter.test(contact));
    }
    
    @Test
    void testTagFilter(){
        StringProperty substring = new SimpleStringProperty("f");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);
        EmailFilter emailFilter = new EmailFilter(nameFilter);
        TagFilter tagFilter = new TagFilter(emailFilter);
        
        Contact contact = new Contact("Gennaro", "landi");
        contact.addEmail("g.landi@studenti.unisa.it", "landigf.eng@gmail.com");
        contact.addPhoneNumber("3345647741");
        contact.addTag("f");
        contact.addTag("Work");
        assertFalse(nameFilter.test(contact));
        assertTrue(emailFilter.test(contact));
        substring.set("wo");
        assertFalse(emailFilter.test(contact));
        assertTrue(tagFilter.test(contact));
    }
    

}