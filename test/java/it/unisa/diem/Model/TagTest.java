package it.unisa.diem.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class TagTest {
    
    public Tag tag; 
    
    @BeforeEach
    public void setUp() {
        tag = new Tag(); 
    }
    
    
    @Test 
    public void setNameValidTest() {
        assertTrue(tag.setName("ValidName"), "Tag name is valid."); 
        assertTrue(tag.setName("1244"), "Tag name is valid."); 
        assertTrue(tag.setName(" Work"), "Tag name is valid."); 
        assertTrue(tag.setName("Family "), "Tag name is valid.");
        assertTrue(tag.setName("        Univeristà       "), "Tag name is invalid.");
    }
    
    @Test 
    public void setNameInvalidTest() {
        assertFalse(tag.setName("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"), "Tag name is invalid.");
        assertFalse(tag.setName("    abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz       "), "Tag name is invalid."); 
    }
    
}
