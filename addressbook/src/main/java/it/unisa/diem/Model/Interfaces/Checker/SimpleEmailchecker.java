package it.unisa.diem.Model.Interfaces.Checker;

public class SimpleEmailchecker implements EmailChecker {
    public boolean check(String string) {
        return EmailChecker.super.check(string);
    }
}
