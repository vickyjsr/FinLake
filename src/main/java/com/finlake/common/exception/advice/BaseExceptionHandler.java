package com.finlake.common.exception.advice;

import com.finlake.common.exception.InvalidJWTException;
import com.finlake.common.exception.UserAlreadyExistsForEmailException;
import com.finlake.common.exception.UserDoesNotExistsForEmailException;
import com.finlake.model.response.FinlakeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = {"com.finlake"})
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private BaseExceptionResponse baseExceptionResponse;
//
//    public BaseExceptionHandler(BaseExceptionResponse baseExceptionResponse) {
//        this.baseExceptionResponse = baseExceptionResponse;
//    }

//    @Override
//    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
//                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        log.error("Exception occurred {}", ex);
//        return baseExceptionResponse.getErrorResponse(null, ResponseCode.BAD_REQUEST.getCode(), ex.getMessage());
//    }
//
//    @Override
//    protected ResponseEntity<FinlakeResponse<?>> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
//                                                                           HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        String error = ex.getBindingResult()
//                .getFieldErrors().stream()
//                .map(error1 -> error1.getObjectName() + " : " + error1.getDefaultMessage())
//                .toList().stream().findFirst().orElse(null);
//        log.error("Exception occurred {}", error);
//        return baseExceptionResponse.getErrorResponse(null, ResponseCode.BAD_REQUEST.getCode(), error);
//    }

    @ExceptionHandler(UserDoesNotExistsForEmailException.class)
    protected ResponseEntity<FinlakeResponse<?>> handleUserDoesNotExistsForEmailException(UserDoesNotExistsForEmailException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(UserAlreadyExistsForEmailException.class)
    public ResponseEntity<FinlakeResponse<?>> handleUserAlreadyExistsForEmailException(UserAlreadyExistsForEmailException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(InvalidJWTException.class)
    public ResponseEntity<FinlakeResponse<?>> handleInvalidJWTException(InvalidJWTException exception) {
        log.error("Exception occurred {}", exception);
        return baseExceptionResponse.getErrorResponse(exception.getRequestId(), exception.getErrorCode(), exception.getLocalizedMessage());
    }
}
