package exceptions;

public class RecursionException extends Exception {
    @Override
    public String getMessage() {
        return "Recursion found";
    }
}
