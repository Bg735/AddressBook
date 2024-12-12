package it.unisa.diem.Model;

import static org.junit.jupiter.api.Assertions.*;
import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;





public class ContactTest {

    private Contact contact;

    private String[] exampleEmails(int i){
        String[] emails = {"john.doe@example.com", "john@example.com", "john_doe@personal.org", "john@home.it"};
        return Arrays.copyOfRange(emails, 0, i);
    }

    private String[] examplePhones(int i){
        String[] phones = {"1234567890", "0987654321", "5432167890", "0987654323"};
        return Arrays.copyOfRange(phones, 0, i);
    }

    private Contact defaultContact(){
        contact = new Contact("John", "Doe");
        assertEquals("John", contact.getNameValue());
        assertEquals("Doe", contact.getSurnameValue());
        return contact;
    }

    private Contact noNameContact(){
        contact = new Contact("", "Doe");
        assertEquals("", contact.getNameValue());
        assertEquals("Doe", contact.getSurnameValue());
        return contact;
    }

    private Contact noSurnameContact(){
        contact = new Contact("John", "");
        assertEquals("John", contact.getNameValue());
        assertEquals("", contact.getSurnameValue());
        return contact;
    }

    private Contact noNameNoSurnameContact(){
        contact = new Contact("", "");
        assertEquals("", contact.getNameValue());
        assertEquals("", contact.getSurnameValue());
        return contact;
    }


    @Test
    public void testDefaultConstructor() {
        contact = new Contact();
        assertNotNull(contact.getName());
        assertNotNull(contact.getSurname());
        assertNotNull(contact.getFullName());
        for (String string : contact.getEmailList()) {
            assertEquals("", string);
            
        }
        for (String string : contact.getPhoneNumberList()) {
            assertEquals("", string);
        }
        assertNotNull(contact.getTags());
        assertEquals("", contact.getPicture());
    }

    @Test
    public void testConstructor_Name_Surname() {
        contact = new Contact("John", "Doe");
        assertEquals("John", contact.getNameValue());
        assertEquals("Doe", contact.getSurnameValue());

        contact = new Contact("John", "");
        assertEquals("John", contact.getNameValue());
        assertEquals("", contact.getSurnameValue());

        contact = new Contact("", "Doe");
        assertEquals("", contact.getNameValue());
        assertEquals("Doe", contact.getSurnameValue());

        contact = new Contact("", "");
        assertEquals("", contact.getNameValue());
        assertEquals("", contact.getSurnameValue());
    }

    @Test
    public void testConstructor_Name_Surname_Email_Phone() {
        String[] emails = {"john.doe@example.com", "john@example.com"};
        String[] phones = {"1234567890", "0987654321", "5432167890", "0987654321"};
        contact = new Contact("John", "Doe", phones, emails);
        assertEquals("John", contact.getNameValue());
        assertEquals("Doe", contact.getSurnameValue());
        assertArrayEquals(emails, contact.getEmailList());
        assertArrayEquals(phones, contact.getPhoneNumberList());
    }

    @Test
    public void testSetName() {
        contact=new Contact();
        assertTrue(contact.setName("John"));
        assertEquals("John", contact.getNameValue());
    }

    @Test
    public void testSetSurname() {
        contact=new Contact();
        assertTrue(contact.setSurname("Doe"));
        assertEquals("Doe", contact.getSurnameValue());
    }

    @Test //TODO completare quando implementata generateProfilePicturePath
    public void testSetPicture() {
        contact=new Contact();
        contact.setPicture("path/to/picture.png");
        //assertTrue();
        assertEquals("path/to/picture", contact.getPicture());
    }

    @Test
    public void testAddEmailAndPhones() {
        String[] emails = exampleEmails(2);
        String[] phones = examplePhones(4);
        contact = new Contact("John", "Doe");
        assertTrue(contact.addEmail(emails));
        assertFalse(contact.addPhoneNumber(phones));
        assertArrayEquals(new String[]{"","",""}, contact.getPhoneNumberList());
        assertArrayEquals(new String[]{exampleEmails(2)[0], exampleEmails(2)[1],""}, contact.getEmailList());

        String[] emails2 = {"john.doe@personal.com"};
        String[] phones2 = {"1234567890"};
        assertTrue(contact.addEmail(emails2));
        assertTrue(contact.addPhoneNumber(phones2));
        assertArrayEquals(new String[]{phones2[0],"",""}, contact.getPhoneNumberList());
        String[] tmp = {emails[0], emails[1], emails2[0]};
        assertArrayEquals(tmp, contact.getEmailList());
    }

    @Test
    public void testSetEmailAndPhones() {
        String[] emails = exampleEmails(2);
        String[] phones = examplePhones(3);
        contact = new Contact("John", "Doe");
        contact.addEmail(emails);
        contact.addPhoneNumber(phones);
        assertTrue(contact.setEmail("jane.doe@example.fr", 1));
        assertEquals("jane.doe@example.fr", contact.getEmailAtIndex(1));
        assertFalse(contact.setPhoneNumber("", 1));
        assertArrayEquals(phones, contact.getPhoneNumberList());
    }

    @Test
    public void testRemoveAtIndex(){
        String[] emails = exampleEmails(2);
        String[] phones = examplePhones(3);
        contact = new Contact("John", "Doe");

        contact.addEmail(emails);
        contact.addPhoneNumber(phones);
        contact.removeEmailAtIndex(1);
        contact.removePhoneNumberAtIndex(0);
        assertEquals(emails[0], contact.getEmailAtIndex(0));
        assertEquals("", contact.getEmailAtIndex(1));
        assertEquals("", contact.getEmailAtIndex(2));
        assertEquals(phones[1], contact.getPhoneNumberAtIndex(0));
        assertEquals(phones[2], contact.getPhoneNumberAtIndex(1));
        assertEquals("", contact.getPhoneNumberAtIndex(2));
    }

    @Test
    public void testAddTag(){
        contact = new Contact("John", "Doe");
        contact.addTag("tag1");
        contact.addTag("tag2");
        contact.addTag("tag3");
        Tag[] tags= new Tag[3];
        Tag tag1 = new Tag();
        tag1.setName("tag1");
        tags[0] = tag1;
        Tag tag2 = new Tag();
        tag2.setName("tag2");
        tags[1] = tag2;
        Tag tag3 = new Tag();
        tag3.setName("tag3");
        tags[2] = tag3;
        
        assertTrue(contact.getTags().contains(tag1));
        assertTrue(contact.getTags().contains(tag2));
        assertTrue(contact.getTags().contains(tag3));
    }

    @Test
    public void testContains(){
    
        contact = new Contact("John", "Doe", examplePhones(3), exampleEmails(3));
        contact.addTag("friend");
        contact.addTag("colleague");

        // Test containsName
        assertTrue(contact.containsName("John"));
        assertTrue(contact.containsName("Doe"));
        assertFalse(contact.containsName("Jane"));

        // Test containsEmail
        assertTrue(contact.containsEmail("john.doe@example.com"));
        assertTrue(contact.containsEmail("john@example.com"));
        assertFalse(contact.containsEmail("jane.doe@example.com"));

        // Test containsPhone
        assertTrue(contact.containsPhone("1234567890"));
        assertTrue(contact.containsPhone("0987654321"));
        assertFalse(contact.containsPhone("1111111111"));

        // Test containsTag
        assertTrue(contact.containsTag("friend"));
        assertTrue(contact.containsTag("colleague"));
        assertFalse(contact.containsTag("family"));
    }

    @Test
    public void testCompareTo(){}

    @Test
    public void testWriteObject(){}

    @Test
    public void testReadObject(){}

    @Test
    public void testToVCard(){}

    @Test
    public void testFromVCard(){}
    //TODO
}