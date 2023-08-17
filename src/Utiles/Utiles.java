package Utiles;

public class Utiles {

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }
    public static boolean isAlphabetic(String str) {
        return str.matches("[a-zA-Z]+");
    }
}
