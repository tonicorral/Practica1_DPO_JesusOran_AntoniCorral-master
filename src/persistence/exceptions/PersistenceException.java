package persistence.exceptions;

public class PersistenceException extends Exception{
    public PersistenceException(String message, Exception cause){
            super(message, cause);
        }
}
