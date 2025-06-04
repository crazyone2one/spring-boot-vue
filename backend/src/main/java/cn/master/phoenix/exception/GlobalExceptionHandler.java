package cn.master.phoenix.exception;

import cn.master.phoenix.payload.response.ApiError;
import cn.master.phoenix.payload.response.ApiResponse;
import cn.master.phoenix.payload.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

/**
 * created by 11's papa at 2025/4/25-13:40 @version v1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ApiResponse.error(ApiError.BAD_REQUEST.getCode(), ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse("参数验证失败"));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication error", ex.getMessage(), request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<?> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access denied", ex);
        return ApiResponse.error(ApiError.FORBIDDEN);
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<?> handleBadCredentialsException(BadCredentialsException ex) {
        log.error("Bad credentials", ex);
        return ApiResponse.error(ApiError.UNAUTHORIZED);
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleDisabledException(DisabledException ex, WebRequest request) {
        log.error("Disabled", ex);
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Account disabled", "Your account is disabled", request);
    }

    @ExceptionHandler(LockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorResponse> handleLockedException(LockedException ex, WebRequest request) {
        log.error("Locked", ex);
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Locked account", "Your account is blocked", request);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", ex.getMessage(), request);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String title, String message, WebRequest request) {
        log.error("Error: {} - {}", title, message);
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                title,
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, status);
    }
}
