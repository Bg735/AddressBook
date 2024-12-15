package it.unisa.diem.Model.Interfaces.Checker;

/**
 * Classes implementing this interface will be used to check if a certain string is a valid file system path.
 */
public interface PathChecker extends Checker {
    
    /**
     * Checks if the String parameter is a valid path.
     * @param[in] string the path string to check
     * @return true if the path is valid, false otherwise
     */
    @Override
    default boolean check(String string){
        if(string == null) return false;
        if(string.matches("^[a-zA-Z]:\\\\(?:[^\\\\/:*?\"<>|\\r\\n]+\\\\)*[^\\\\/:*?\"<>|\\r\\n]*$")) return true;
        else return false;
    }
    
}