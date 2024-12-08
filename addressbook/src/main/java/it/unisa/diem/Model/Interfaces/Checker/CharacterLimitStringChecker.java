package it.unisa.diem.Model.Interfaces.Checker;

/**
 * StringChecker implementation used to check if a certain string has at most a limit number of characters.
 */
public class CharacterLimitStringChecker implements StringChecker {
    private int limit;

    /**
     * Creates a new CharacterLimitStringChecker with the specified limit.
     * @param[in] limit the maximum number of characters allowed
     */
    public CharacterLimitStringChecker(int limit) {
        this.limit = limit;
    }

    /**
     * Checks if the String parameter has at most the number of characters specified by {@link #limit}.
     * @param[in] string the string to check
     * @return true if the string has at most the limit number of characters, false otherwise
     */
    @Override
    public boolean check(String string) {
        return string != null && string.length() <= limit;
    }
}
