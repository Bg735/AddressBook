package it.unisa.diem.Model.Interfaces.Checker;

/**
 * Classes implementing this interface will be used to check if a certain string is a valid phone number.
 */
public interface PhoneChecker extends Checker {
    
    /**
     * Checks if the String parameter is a valid phone number.
     * By default, checks if the string is a not empty, only numbers string.
     * @param[in] string the phone number string to check
     * @return true if the phone number is valid, false otherwise
     */
    @Override
    default boolean check(String string){
        return string != null && !string.isEmpty() && string.matches("\\d+");
    }
}