package com.esprit.secondchanceserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST)
public class BadParametersException extends Exception {
    public BadParametersException(String msg) {
        super(msg);
    }
}
