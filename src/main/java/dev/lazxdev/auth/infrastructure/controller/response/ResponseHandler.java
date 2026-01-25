package dev.lazxdev.auth.infrastructure.controller.response;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {

    public static <T> ResponseEntity<ApiResponse<T>> createResponse(
            HttpStatus status, String message, T data) {

        ApiResponse<T> response = new ApiResponse<>(
                status.value(),
                "SUCCESS",
                message,
                null,
                data,
                null
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(response, headers, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> createErrorResponse(
            HttpStatus status, String type, String origin, String message, T error) {

        ApiResponse<T> response = new ApiResponse<>(
                status.value(),
                type,
                message,
                origin,
                null,
                error
        );

        return new ResponseEntity<>(response, status);
    }
}