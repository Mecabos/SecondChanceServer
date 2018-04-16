package com.esprit.secondchanceserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT)
public class AlreadyExistsException extends Exception {

    public AlreadyExistsException(String msg) {
        super(msg);
    }
}