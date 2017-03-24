package exception;

/**
 * Created by Kelvin on 23/03/2017.
 */
public class NonexistingCarryException extends Exception {
    public NonexistingCarryException() {
    }

    public NonexistingCarryException(String message) {
        super(message);
    }

    public NonexistingCarryException(String message, Throwable cause) {
        super(message, cause);
    }

    public NonexistingCarryException(Throwable cause) {
        super(cause);
    }

    public NonexistingCarryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
