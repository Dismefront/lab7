package exceptions;

public class CommandDoesNotExistException extends Exception {

    @Override
    public String getMessage() {
        return "Command does not exist";
    }
}
