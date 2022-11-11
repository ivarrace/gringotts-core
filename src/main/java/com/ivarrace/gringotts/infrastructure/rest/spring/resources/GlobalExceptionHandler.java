package com.ivarrace.gringotts.infrastructure.rest.spring.resources;

import com.ivarrace.gringotts.application.exception.*;
import com.ivarrace.gringotts.infrastructure.rest.spring.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(ObjectNotFoundException exception,
                                                           final HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), httpStatus, httpServletRequest);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(value = InvalidParameterException.class)
    public ResponseEntity<ErrorResponse> invalidParameterException(InvalidParameterException exception,
                                                                   final HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), httpStatus, httpServletRequest);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(value = ObjectAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> objectAlreadyRegisteredException(ObjectAlreadyRegisteredException exception,
                                                                          final HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), httpStatus, httpServletRequest);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> usernameNotFoundException(UsernameNotFoundException exception,
                                                                   final HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), httpStatus, httpServletRequest);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception,
                                                                             final HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), httpStatus, httpServletRequest);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> httpMessageNotReadableException(HttpMessageNotReadableException exception,
                                                                         final HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), httpStatus, httpServletRequest);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(value = InsufficientPrivilegesException.class)
    public ResponseEntity<ErrorResponse> insufficientPrivilegesException(InsufficientPrivilegesException exception,
                                                                         final HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.FORBIDDEN;
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), httpStatus, httpServletRequest);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }

    @ExceptionHandler(value = UserAlreadyRegisteredException.class)
    public ResponseEntity<ErrorResponse> userAlreadyRegisteredException(UserAlreadyRegisteredException exception,
                                                                        final HttpServletRequest httpServletRequest) {
        HttpStatus httpStatus = HttpStatus.CONFLICT;
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage(), httpStatus, httpServletRequest);
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
}
