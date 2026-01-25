package dev.lazxdev.auth.infrastructure.exceptions;

public enum ApplicationErrorMessage {

    // Conflict errors (409)
    EMAIL_ALREADY_REGISTERED(409, "EMAIL_ALREADY_REGISTERED", "El email ya está registrado: "),
    ACCOUNT_LOCKED(409, "ACCOUNT_LOCKED", "La cuenta está bloqueada: "),
    ACCOUNT_DISABLED(409, "ACCOUNT_DISABLED", "La cuenta está deshabilitada: "),

    // Validation errors (400)
    INVALID_EMAIL_FORMAT(400, "INVALID_EMAIL_FORMAT", "Formato de email inválido: "),
    PASSWORD_TOO_SHORT(400, "PASSWORD_TOO_SHORT", "La contraseña debe tener al menos 6 caracteres"),
    INVALID_CREDENTIALS(400, "INVALID_CREDENTIALS", "Credenciales inválidas"),

    // Not found errors (404)
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "Usuario no encontrado: "),

    // Eliminado: ROLE_NOT_FOUND

    // Forbidden errors (403)
    INSUFFICIENT_PERMISSIONS(403, "INSUFFICIENT_PERMISSIONS", "Permisos insuficientes para esta operación"),

    // Internal errors (500)
    PASSWORD_HASHING_ERROR(500, "PASSWORD_HASHING_ERROR", "Error al encriptar la contraseña"),
    AUDIT_LOG_FAILED(500, "AUDIT_LOG_FAILED", "Error al registrar en el log de auditoría"),

    // Default
    UNEXPECTED_ERROR(500, "UNEXPECTED_ERROR", "Error inesperado del sistema");

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
