package service;
public class DuplicateUsernameException extends RuntimeException {

    public DuplicateUsernameException(String message) {
        super(message);
    }
}
