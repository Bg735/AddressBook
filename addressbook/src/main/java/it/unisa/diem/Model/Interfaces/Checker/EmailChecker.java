package it.unisa.diem.Model.Interfaces.Checker;

public interface EmailChecker extends Checker {
    /**
     * By default, checks if the string respects the format []@[].[]
     * @param string the email string to check
     * @return true if the email is valid, false otherwise
     */
    @Override
    default boolean check(String string){
        return string!=null && string.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}