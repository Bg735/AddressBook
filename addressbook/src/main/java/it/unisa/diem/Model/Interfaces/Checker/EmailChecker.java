package it.unisa.diem.Model.Interfaces.Checker;

/**
 * Classes implementing this interface will be used to check if a certain string is a valid email.
 */
public interface EmailChecker extends Checker {
    static final String regex = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    /**
     * Checks if the String parameter is a valid email.
     * By default, checks if the string respects the format defined by the regular expression: {@value #regex}.
     * @param[in] string the email string to check
     * @return true if the email is valid, false otherwise
     */
    @Override
    default boolean check(String string){
        return string!=null && string.matches(regex);
    }
}