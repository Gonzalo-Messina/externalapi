package com.externalapi.exceptions;

import com.externalapi.reponse.ResponseHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {TelevisionExceptions.class })
    protected ResponseEntity<Object> handleConflict(TelevisionExceptions ex, WebRequest request) {
        ResponseEntity<Object> body = ResponseHandler.generateResponse(ex.getMessage(), ex.getHttpStatus(),null);
        return handleExceptionInternal(ex, body, new HttpHeaders(), ex.getHttpStatus(), request);
    }
}