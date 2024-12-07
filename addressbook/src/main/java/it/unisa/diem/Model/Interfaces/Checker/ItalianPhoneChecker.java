package it.unisa.diem.Model.Interfaces.Checker;

public class ItalianPhoneChecker implements PhoneChecker{
        
    @Override
    public boolean check(String string) {
        if (string == null || string.isEmpty()) {return false;}
        // Check if the length is either 9 or 10
        int length = string.length();
        return (length == 9 || length == 10) && PhoneChecker.super.check(string);

    }

}
