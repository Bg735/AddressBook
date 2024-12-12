package it.unisa.diem.Model.Interfaces.Filter;

import it.unisa.diem.Model.Contact;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilterTests {

    private FilteredList<Contact> filteredList;
    private List<Contact> contactList;

    @BeforeEach
    void setUp() {
        contactList = FXCollections.observableArrayList(
            new Contact("Elettra", "Palmisano", new String[]{"12345"}, new String[]{"elettra@example.com"}),
            new Contact("Gennaro Francesco", "Landi", new String[]{"67890"}, new String[]{"gennaro@example.com"}),
            new Contact("Maurizio", "Melillo", new String[]{"11223"}, new String[]{"maurizio@example.com"}),
            new Contact("Francesco", "Lanzara", new String[]{"44556"}, new String[]{"francesco@example.com"}),
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
    void testNameFilter() {
        SimpleStringProperty substring = new SimpleStringProperty("Francesco");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);

        // Test filtering logic
        filteredList.setPredicate(nameFilter);
        
        // Verify that only matching contacts are in the filtered list
        assertEquals(2, filteredList.size());
        assertTrue(filteredList.contains(new Contact("Gennaro Francesco", "Landi")));
        assertTrue(filteredList.contains(new Contact("Francesco", "Lanzara")));
    }

    @Test
    void testNameFilterNoMatches() {
        SimpleStringProperty substring = new SimpleStringProperty("NonEsistente");
        BaseFilter baseFilter = new BaseFilter(substring);
        NameFilter nameFilter = new NameFilter(baseFilter);

        // Test filtering logic
        filteredList.setPredicate(nameFilter);

        // Verify that no contacts match
        assertTrue(filteredList.isEmpty());
    }
}