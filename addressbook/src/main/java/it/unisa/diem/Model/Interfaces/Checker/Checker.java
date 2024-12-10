package it.unisa.diem.Model.Interfaces.Checker;



/**
 * Classes implementing this interface will be used to check if a certain string has a valid format.
 */
@FunctionalInterface


public interface Checker {
    /**
     * Checks if the string has a valid format.
     * @param[in] string the string to check
     * @return true if the string has a valid format, false otherwise
     */
    boolean check(String string);
}
