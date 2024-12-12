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
    }
    
    @Test 
    public void setNameInvalid1Test() {
        assertTrue(!tag.setName("abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"), "Tag name is invalid."); 
    }
    
    @Test 
    public void setNameInvalid2Test() {
        assertTrue(!tag.setName("  "), "Tag name is invalid."); 
    }
    
}
