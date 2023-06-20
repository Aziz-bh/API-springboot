package com.api.API2.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReplyException extends RuntimeException {
    public ReplyException(String saving_error) {
        super(saving_error);
    }
}
