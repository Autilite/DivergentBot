package exception;

/**
 * Created by Kelvin on 23/03/2017.
 */
public class NonexistentCarryException extends Exception {
    public NonexistentCarryException() {
    }

    public NonexistentCarryException(String message) {
        super(message);
    }

    public NonexistentCarryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistentCarryException(Throwable cause) {
        super(cause);
    }

    public NonexistentCarryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
