package it.unisa.diem.Model.Interfaces.Checker;

/**
 * PhoneChecker implementation used to check if a certain string is a valid phone number for the italian standard.
 */
public class ItalianPhoneChecker implements PhoneChecker{
    
    /**
     * Checks if the String parameter is a valid phone number for the italian standard.
     * 
     * @param[in] string the phone number string to check
     * @return true if the phone number is valid, false otherwise
     */
    @Override
    public boolean check(String string) {
        if (string == null) {return false;}
        string.trim().replace(" ", "");
        if (string.startsWith("+39")) {
            string = string.substring(3);
        }
        int length = string.length();
        return PhoneChecker.super.check(string) && (length == 9 || length == 10);
        }
    }
