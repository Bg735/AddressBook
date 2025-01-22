/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.Model;



import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author mauriziomelillo
 */
public class RecentlyDeletedTest {

    private RecentlyDeleted recentlyDeleted;
    private Contact c1;
    private Contact c2;

    @BeforeEach
    public void setUp() {
        recentlyDeleted = new RecentlyDeleted();
        c1 = new Contact("John Doe", "123456789");
        c2 = new Contact("Jane Smith", "987654321");
    }

    @Test
    public void testPut() {
        // Add a contact to the trash can
        recentlyDeleted.put(c1);

        // Verify the contact is in the trash can
        List<Contact> contacts = recentlyDeleted.contacts().get();
        assertEquals(1, contacts.size());
    }

    @Test
    public void testRemove() {
        // Add two contacts to the trash can
        recentlyDeleted.put(c1);
        recentlyDeleted.put(c2);

        // Remove one contact
        recentlyDeleted.remove(c1);

        // Verify the remaining contacts
        List<Contact> contacts = recentlyDeleted.contacts();
        assertEquals(1, contacts.size());
    }

    @Test
    public void testRemoveExpired() {
        // Add a contact with a deletion date outside the retention period
        LocalDate oldDate = LocalDate.now().minusDays(RecentlyDeleted.RETENTION_PERIOD_DAYS + 1);
        LocalDateProperty oldDateProperty = new LocalDateProperty(oldDate);
        recentlyDeleted.get().put(oldDateProperty, new SimpleSetProperty<>(FXCollections.observableSet(Collections.singleton(c1))));


        // Add a contact within the retention period
        recentlyDeleted.put(c2);

        // Remove expired contacts
        recentlyDeleted.removeExpired();

        // Verify the remaining contacts
        List<Contact> contacts = recentlyDeleted.contacts();
        assertEquals(1, contacts.size());
    }

    @Test
    public void testContacts() {
        // Add multiple contacts on different dates
        recentlyDeleted.put(c1);
        recentlyDeleted.put(c2);

        // Verify the list of contacts
        List<Contact> contacts = recentlyDeleted.contacts();
        assertEquals(2, contacts.size());
    }

}
