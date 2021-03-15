package com.udacity.jdnd.course3.critter.exception;

import com.udacity.jdnd.course3.critter.exception.custom.CouldNotBeNullException;
import com.udacity.jdnd.course3.critter.exception.custom.DuplicatedResourceException;
import javassist.NotFoundException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ApiCustomExceptionHandler extends ResponseEntityExceptionHandler {

    Log log = LogFactory.getLog(this.getClass());

    @Autowired
    private MessageSource messageSource; // Spring Object to get the messages from messages.properties file.

    private List<CustomError> createErrorList(BindingResult bindingResult) {
        List<CustomError> errors = new ArrayList();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String userMsg = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String detailedMsg = fieldError.toString();
            errors.add(new CustomError(userMsg, detailedMsg));
        }
        return errors;
    }

    // Send message for Invalid requisition( eg: invalid field) message.
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String userMsg = messageSource.getMessage("parameters.invalid", null, LocaleContextHolder.getLocale());
        String detailedMsg = messageSource.getMessage("pay.attention", null, LocaleContextHolder.getLocale())
                + ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<CustomError> errors = Arrays.asList(new CustomError(userMsg, detailedMsg));
        log.error(detailedMsg, ex);
        return handleExceptionInternal(ex, errors,
                headers, HttpStatus.BAD_REQUEST, request);
    }

    // Send message for Invalid field value.
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<CustomError> errors = createErrorList(ex.getBindingResult());
        log.error(errors, ex);
        return handleExceptionInternal(ex, errors, headers, HttpStatus.BAD_REQUEST, request);
    }

    // Send a message when is tried to delete a non existing resource
    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
        String userMsg = messageSource.getMessage("resource.not.found", null, LocaleContextHolder.getLocale());
        String detailedMsg = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<CustomError> errors = Arrays.asList(new CustomError(userMsg, detailedMsg));
        log.error(detailedMsg, ex);
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }


    // Exception when is tried to delete a non existing resource
    @ExceptionHandler({IllegalArgumentException.class,
            NotFoundException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        String userMsg = messageSource.getMessage("parameters.invalid", null, LocaleContextHolder.getLocale());
        String detailedMsg = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<CustomError> errors = Arrays.asList(new CustomError(userMsg, detailedMsg));
        log.error(detailedMsg, ex);
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    // Exception when a mandatory parameter is not valid
    @ExceptionHandler({CouldNotBeNullException.class})
    public ResponseEntity<Object> handleCouldNotBeNullException(CouldNotBeNullException ex, WebRequest request) {
        String userMsg = messageSource.getMessage("parameters.invalid", null, LocaleContextHolder.getLocale());
        String detailedMsg = ex.getCause() != null ? ex.getCause().toString() : ex.toString();
        List<CustomError> errors = Arrays.asList(new CustomError(userMsg, detailedMsg));
        log.error(detailedMsg, ex);
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    // Exception when is tried to delete a non existing resource
    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String userMsg = messageSource.getMessage("resource.nonexistent", null, LocaleContextHolder.getLocale());
        String detailedMsg = ex.toString();
        List<CustomError> errors = Arrays.asList(new CustomError(userMsg, detailedMsg));
        log.error(detailedMsg, ex);
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    // Exception when is tried to delete a non existing resource
    @ExceptionHandler({DuplicatedResourceException.class})
    public ResponseEntity<Object> handleDuplicatedResourceException(DuplicatedResourceException ex, WebRequest request) {
        String userMsg = messageSource.getMessage("resource.already.exist", null, LocaleContextHolder.getLocale());
        String detailedMsg = ex.toString();
        List<CustomError> errors = Arrays.asList(new CustomError(userMsg, detailedMsg));
        log.error(detailedMsg, ex);
        return handleExceptionInternal(ex, errors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    public static class CustomError {

        private String userMsg;
        private String detailedMsg;

        public CustomError(String userMsg, String detailedMsg) {
            this.userMsg = userMsg;
            this.detailedMsg = detailedMsg;
        }

        public String getUserMsg() {
            return userMsg;
        }

        public void setUserMsg(String userMsg) {
            this.userMsg = userMsg;
        }

        public String getDetailedMsg() {
            return detailedMsg;
        }

        public void setDetailedMsg(String detailedMsg) {
            this.detailedMsg = detailedMsg;
        }

    }

}