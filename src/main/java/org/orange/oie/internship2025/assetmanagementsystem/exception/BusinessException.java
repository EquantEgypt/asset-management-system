package org.orange.oie.internship2025.assetmanagementsystem.exception;

import org.orange.oie.internship2025.assetmanagementsystem.model.errors.ApiReturnCode;

public class BusinessException extends RuntimeException {

    private final ApiReturnCode code;
    private final boolean printStackTrace;

    public BusinessException(ApiReturnCode code, String description) {
        super(description);
        this.code = code;
        this.printStackTrace = true;
    }

    public ApiReturnCode getCode() {
        return code;
    }

    public boolean isPrintStackTrace() {
        return printStackTrace;
    }
}
