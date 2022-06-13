package com.setronica.intern.test.project.exception;

import org.apache.log4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler {
    private static final Logger log = Logger.getLogger("Exception logger");


    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponse> onMissingRequestParameter(MissingServletRequestParameterException ex) {
        log.info("Missing request parameter " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> onResourceNotFound(ResourceNotFoundException ex) {
        log.info("Resource not found " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceQueryNotFoundException.class)
    public ResponseEntity<ExceptionResponse> onResourceQueryNotFound(ResourceQueryNotFoundException ex) {
        log.info("Resource query is not correct " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(new ExceptionResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> BadRequestException(BadRequestException ex) {
        log.info(ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> onConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation violation : ex.getConstraintViolations()) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        log.warn("ConstraintViolationException " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> onMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.warn("Method argument not valid " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> onHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.warn("Http Message Not Readable " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<Object> onTypeMismatch(TypeMismatchException ex) {
        log.warn("Type Mismatch " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> onNoHandlerFound(Exception ex) {
        log.error("Unhandled exception " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> onDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.error("It looks like there are several identical currencies or languages in the product being created. Please fix this and resubmit your request. " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Object> onDataIntegrityViolation(InvalidDataAccessApiUsageException ex) {
        log.error("It looks like there are several identical currencies or languages in the product. Please fix this and resubmit your request. " + ex.getClass().getSimpleName() + " " + ex.getMessage());
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
