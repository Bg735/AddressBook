package it.unisa.diem.Model.Interfaces.Checker;

/**
 * Classes implementing this interface will be used to check if a certain string has a valid format.
 */
@FunctionalInterface
public interface Checker {
    boolean check(String string);
}
