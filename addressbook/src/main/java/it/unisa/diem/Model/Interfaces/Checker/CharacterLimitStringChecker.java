package it.unisa.diem.Model.Interfaces.Checker;

class CharacterLimitStringChecker implements StringChecker {
    private int limit;

    public CharacterLimitStringChecker(int limit) {
        this.limit = limit;
    }

    @Override
    public boolean check(String string) {
        return string != null && string.length() <= limit;
    }
}
