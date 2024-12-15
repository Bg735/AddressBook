package it.unisa.diem.Model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;


import org.junit.jupiter.api.Test;

import ezvcard.VCard;





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

    @Test
    public void testSetPicture() {
        contact=new Contact();
        try{
            contact.setPicture("src/test/java/it/unisa/diem/TestFiles/ContactTest/gattino.png");
        }catch(IOException e){
            e.printStackTrace();
            fail();
        }
        File directory=new File("src/assets/contact_pictures");
        assertTrue(directory.list().length > 0);
        
        
        // for (File file : directory.listFiles()) {
        //     file.delete();
        // }
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
    public void testCompareTo(){
        contact = new Contact("John", "Doe");
        Contact contact2 = new Contact("Jane", "Doe");
        Contact contact3 = new Contact("John", "Doe");
        assertTrue(contact.compareTo(contact2)>0);
        assertTrue(contact2.compareTo(contact)<0);
        assertTrue(contact.compareTo(contact3)<0);
    }

    @Test
    public void testReadWriteObject(){

        contact = new Contact("John", "Doe");
        contact.addEmail("john.doe@example.com", "john@example.com");
        contact.addPhoneNumber("1234567890", "0987654321");
        contact.addTag("friend");
        contact.addTag("colleague");

        try(ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/test/java/it/unisa/diem/TestFiles/ContactTest/ContactTest.obj")))){
            out.writeObject(contact);
        }catch (IOException e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
        try( ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("src/test/java/it/unisa/diem/TestFiles/ContactTest/ContactTest.obj")))){
            Contact readContact= (Contact) in.readObject();
            // Verify that the read contact is equal to the original contact
            assertEquals(contact.getNameValue(), readContact.getNameValue());
            assertEquals(contact.getSurnameValue(), readContact.getSurnameValue());
            assertArrayEquals(contact.getEmailList(), readContact.getEmailList());
            assertArrayEquals(contact.getPhoneNumberList(), readContact.getPhoneNumberList());
            assertEquals(contact.getTags().size(), readContact.getTags().size());
            for (Tag tag : contact.getTags()) {
                assertTrue(readContact.getTags().get().contains(tag));
            }
        }catch (IOException | ClassNotFoundException e) {
            fail("Exception should not have been thrown: " + e.getMessage());
        }
        new File("src/test/java/it/unisa/diem/TestFiles/ContactTest/ContactTest.obj").delete();
    }


    @Test
    public void testVCard(){
    
        contact = new Contact("John", "Doe");
        contact.addEmail("john.doe@example.com", "john@example.com");
        contact.addPhoneNumber("1234567890", "0987654321");
        contact.addTag("friend");
        contact.addTag("colleague");
        try{contact.setPicture("src/test/java/it/unisa/diem/TestFiles/ContactTest/gattino.png");}catch(IOException e){fail("file d'esempio non trovato");}

        VCard vCard= contact.toVCard();
        try{
            Contact contact2 = Contact.fromVCard(vCard);
            assertEquals(contact.getNameValue(), contact2.getNameValue());
            assertEquals(contact.getSurnameValue(), contact2.getSurnameValue());
            assertArrayEquals(contact.getEmailList(), contact2.getEmailList());
            assertArrayEquals(contact.getPhoneNumberList(), contact2.getPhoneNumberList());
            assertEquals(contact.getTags().size(), contact2.getTags().size());
            for (Tag tag : contact.getTags()) {
                assertTrue(contact2.getTags().contains(tag));
            }
        }catch(IOException e){fail();}
    }
}

