package com.api.API2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ThreadException extends RuntimeException {
    public ThreadException(String not_found) {
        super(not_found);
    }
}
