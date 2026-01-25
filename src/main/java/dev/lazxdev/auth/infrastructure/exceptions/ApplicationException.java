package dev.lazxdev.auth.infrastructure.exceptions;

public class ApplicationException extends RuntimeException {

    private final String type;
    private final int statusCode;
    private final String origin;
    private final Object data;
    private final Object error;

    public ApplicationException(ApplicationErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.type = errorMessage.getType();
        this.statusCode = errorMessage.getStatus();
        this.origin = detectOriginClass();
        this.data = null;
        this.error = null;
    }

    public ApplicationException(ApplicationErrorMessage errorMessage, String details) {
        super(errorMessage.getMessage() + details);
        this.type = errorMessage.getType();
        this.statusCode = errorMessage.getStatus();
        this.origin = detectOriginClass();
        this.data = null;
        this.error = null;
    }

    public ApplicationException(ApplicationErrorMessage errorMessage, Object data, String details) {
        super(errorMessage.getMessage() + details);
        this.type = errorMessage.getType();
        this.statusCode = errorMessage.getStatus();
        this.origin = detectOriginClass();
        this.data = data;
        this.error = null;
    }

    public ApplicationException(ApplicationErrorMessage errorMessage, Throwable cause) {
        super(errorMessage.getMessage(), cause);
        this.type = errorMessage.getType();
        this.statusCode = errorMessage.getStatus();
        this.origin = detectOriginClass();
        this.data = null;
        this.error = cause != null ? cause.getMessage() : null;
    }

    private String detectOriginClass() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : stack) {
            String className = element.getClassName();
            if (!className.equals(Thread.class.getName()) &&
                    !className.equals(ApplicationException.class.getName())) {
                return element.getFileName() != null ?
                        element.getFileName() : element.getClassName();
            }
        }
        return "UnknownOrigin";
    }

    // Getters
    public String getType() { return type; }
    public int getStatusCode() { return statusCode; }
    public String getOrigin() { return origin; }
    public Object getData() { return data; }
    public Object getError() { return error; }
}