package electonic.document.management.model;

public class RequestParametersException extends Exception {

    private static final long serialVersionUID = 1L;

    public RequestParametersException(String message) {
        super(message);
    }

    public RequestParametersException(String message, Throwable cause) {
        super(message, cause);
    }
}
