package it.unisa.diem.Model.Interfaces.Checker;

public interface PhoneChecker extends Checker {
    @Override
    default boolean check(String string){
        return string != null && !string.isEmpty() && string.matches("\\d+");
    }
}