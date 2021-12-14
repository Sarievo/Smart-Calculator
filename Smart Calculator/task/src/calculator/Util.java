package calculator;

public class Util {
    static long power(long a, long b) {
        // cast to long power
        return (long) Math.pow(a, b);
    }

    static long mal(long a, long b) {
        return a * b;
    }

    static long divide(long a, long b) {
        // integer division
        return a / b;
    }

    static long add(long a, long b) {
        return a + b;
    }

    static long subtract(long a, long b) {
        return a - b;
    }
}
