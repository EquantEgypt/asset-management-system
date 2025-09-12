package org.orange.oie.internship2025.assetmanagementsystem.api;

import jakarta.servlet.http.HttpServletRequest;
import org.orange.oie.internship2025.assetmanagementsystem.exception.BusinessException;
import org.orange.oie.internship2025.assetmanagementsystem.model.errors.ApiResponse;
import org.orange.oie.internship2025.assetmanagementsystem.model.errors.ApiReturnCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse> handleBusinessException(HttpServletRequest req, BusinessException ex) {
        ApiResponse response = new ApiResponse(
                ex.getCode(),
                ex.getCode().name(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, ex.getCode().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGenericException(HttpServletRequest req, Exception ex) {
        ApiResponse response = new ApiResponse(
                ApiReturnCode.INTERNAL_ERROR,
                ApiReturnCode.INTERNAL_ERROR.name(),
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, ApiReturnCode.INTERNAL_ERROR.getHttpStatus());
    }
}
