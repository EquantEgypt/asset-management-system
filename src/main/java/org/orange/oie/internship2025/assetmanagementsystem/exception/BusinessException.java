package org.orange.oie.internship2025.assetmanagementsystem.exception;

import org.orange.oie.internship2025.assetmanagementsystem.errors.ApiReturnCode;

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

}
