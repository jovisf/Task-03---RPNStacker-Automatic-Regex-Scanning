public class Regex {
    
    public static boolean isInteger(String input) {
        if (input == null)
            return false;

        return input.matches("(\\d)+");
    }

    public static boolean isOperation(String input) {
        if (input == null)
            return false;

        return input.matches("(\\+|-|\\*|/)");
    }
    
    
}
