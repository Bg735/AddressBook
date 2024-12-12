package it.unisa.diem.Model.Interfaces.Checker;

/**
 * PathChecker implementation used to check if a certain string is a valid path for an image.
 */
public class ImagePathChecker implements PathChecker{
    
    /**
     * Checks if the String parameter is a valid path for an image.
     * 
     * @param[in] string the image path string to check
     * @return true if the image path is valid, false otherwise
     */
    @Override
    public boolean check(String string) {
        if (string == null) return false;
        return PathChecker.super.check(string) && string.substring(string.lastIndexOf('.')+1).matches("jpg|jpeg|png|gif");
    }

}
