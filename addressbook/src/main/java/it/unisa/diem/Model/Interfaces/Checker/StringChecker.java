package it.unisa.diem.Model.Interfaces.Checker;

/**
 * Classes implementing this interface will be used to check if a certain string is a valid word.
 */
public interface StringChecker extends Checker {
    
    /**
     * Checks if the String parameter is a valid word.
     * @param[in] string the word string to check
     * @return true if the word is valid, false otherwise
     */
    @Override
    default boolean check(String string){
        return string != null;
    }
    
}