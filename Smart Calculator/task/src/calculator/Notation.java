package calculator;

public class Notation {
    long value;
    char operator;
    boolean isVariable;

    Notation(long value) {
        this.value = value;
        isVariable = true;
    }

    Notation(char operator) {
        this.operator = operator;
        isVariable = false;
    }

    @Override
    public String toString() {
        return isVariable ? String.valueOf(value) : String.valueOf(operator);
    }
}
