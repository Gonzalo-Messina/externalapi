package com.externalapi.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public class TelevisionExceptions extends RuntimeException{

    private final String message;
    private final HttpStatus httpstatus;

    public TelevisionExceptions(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpstatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpstatus;
    }


}