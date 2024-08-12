package com.finlake.common.exception.advice;

import com.finlake.common.enums.ResponseCode;
import com.finlake.common.exception.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = {"com.finlake"})
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    private final BaseExceptionResponse baseExceptionResponse;

    public BaseExceptionHandler(BaseExceptionResponse baseExceptionResponse) {
        this.baseExceptionResponse = baseExceptionResponse;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("Exception occurred {}", ex);
        return baseExceptionResponse.getErrorResponse(null, ResponseCode.BAD_REQUEST.getCode(), ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(error1 -> error1.getObjectName() + " : " + error1.getDefaultMessage())
                .toList().stream().findFirst().orElse(null);
        log.error("Exception occurred {}", error);
        return baseExceptionResponse.getErrorResponse(null, ResponseCode.BAD_REQUEST.getCode(), error);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleException(Exception exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(null, ResponseCode.UNKNOWN.getCode(), exception.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccessException(DataAccessException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(null, ResponseCode.INTERNAL_SERVER_EXCEPTION.getCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(null, ResponseCode.BAD_REQUEST.getCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(UserDoesNotExistsForEmailException.class)
    protected ResponseEntity<Object> handleUserDoesNotExistsForEmailException(UserDoesNotExistsForEmailException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(UserAlreadyExistsForEmailException.class)
    protected ResponseEntity<Object> handleUserAlreadyExistsForEmailException(UserAlreadyExistsForEmailException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidJWTException.class)
    protected ResponseEntity<Object> handleInvalidJWTException(InvalidJWTException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(DataConversionError.class)
    protected ResponseEntity<Object> handleDataConversionErrorException(DataConversionError exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    protected ResponseEntity<Object> handleInternalServerException(InternalServerException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<Object> handleBadRequestException(BadRequestException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidFieldFactoryException.class)
    protected ResponseEntity<Object> handleInvalidFieldFactoryException(InvalidFieldFactoryException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(UserAuthenticationFailedException.class)
    protected ResponseEntity<Object> handleUserAuthenticationFailedException(UserAuthenticationFailedException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }
}
