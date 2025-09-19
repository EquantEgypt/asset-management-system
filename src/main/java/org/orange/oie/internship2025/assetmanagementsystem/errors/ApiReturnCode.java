package org.orange.oie.internship2025.assetmanagementsystem.errors;

import org.springframework.http.HttpStatus;
import org.slf4j.event.Level;

public enum ApiReturnCode {

    // Generic errors
    INTERNAL_ERROR(1, HttpStatus.INTERNAL_SERVER_ERROR, Level.ERROR),

    // Validation errors
    BAD_REQUEST(20, HttpStatus.BAD_REQUEST, Level.WARN),
    MISSING_BODY(21, HttpStatus.BAD_REQUEST, Level.WARN),

    // Domain-specific errors
    ASSET_ALREADY_EXISTS(100, HttpStatus.CONFLICT, Level.WARN),
    INVALID_ASSET_STATUS(101, HttpStatus.BAD_REQUEST, Level.WARN),
    ASSET_NOT_FOUND(102, HttpStatus.NOT_FOUND, Level.WARN),
    USER_NOT_EXISTS(103, HttpStatus.UNAUTHORIZED, Level.WARN);

    private final int code;
    private final HttpStatus httpStatus;
    private final Level logLevel;

    ApiReturnCode(int code, HttpStatus httpStatus, Level logLevel) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.logLevel = logLevel;
    }

    public int getCode() {
        return code;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Level getLogLevel() {
        return logLevel;
    }
}
