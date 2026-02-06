package dev.lazxdev.auth.infrastructure.exceptions;

public enum ApplicationErrorMessage {

    EMAIL_ALREADY_REGISTERED(409, "EMAIL_ALREADY_REGISTERED", "The email is already registered: "),
    ACCOUNT_LOCKED(409, "ACCOUNT_LOCKED", "The account is blocked: "),
    ACCOUNT_DISABLED(409, "ACCOUNT_DISABLED", "The account is disabled: "),

    INVALID_EMAIL_FORMAT(400, "INVALID_EMAIL_FORMAT", "Invalid email format: "),
    PASSWORD_TOO_SHORT(400, "PASSWORD_TOO_SHORT", "The password must be at least 6 characters long"),
    INVALID_CREDENTIALS(400, "INVALID_CREDENTIALS", "Invalid credentials: "),
    INVALID_TOKEN(400, "INVALID_TOKEN", "Invalid or expired token"),
    REFRESH_TOKEN_EXPIRED(400, "REFRESH_TOKEN_EXPIRED", "Refresh expired token"),

    USER_NOT_FOUND(404, "USER_NOT_FOUND", "User not found: "),
    REFRESH_TOKEN_NOT_FOUND(404, "REFRESH_TOKEN_NOT_FOUND", "Refresh token not found: "),

    INSUFFICIENT_PERMISSIONS(403, "INSUFFICIENT_PERMISSIONS", "Insufficient permits for this operation"),

    PASSWORD_HASHING_ERROR(500, "PASSWORD_HASHING_ERROR", "Error encrypting password"),
    TOKEN_GENERATION_ERROR(500, "TOKEN_GENERATION_ERROR", "Error generating token"),
    AUDIT_LOG_FAILED(500, "AUDIT_LOG_FAILED", "Error logging to audit log"),

    UNEXPECTED_ERROR(500, "UNEXPECTED_ERROR", "Unexpected system error");

    private final int status;
    private final String type;
    private final String message;

    ApplicationErrorMessage(int status, String type, String message) {
        this.status = status;
        this.type = type;
        this.message = message;
    }

    public int getStatus() { return status; }
    public String getType() { return type; }
    public String getMessage() { return message; }
}
