package it.unisa.diem.Model.Interfaces.Checker;

/**
 * EmailChecker base implementation, used to check if a certain string is a valid email.
 */
public class SimpleEmailchecker implements EmailChecker {
    
    /**
     * @inheritDoc
     */
    public boolean check(String string) {
        return EmailChecker.super.check(string);
    }
}
