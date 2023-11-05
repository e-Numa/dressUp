package com.enuma.dressUp.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class BlogApiException extends RuntimeException{
    private final HttpStatus status;
    private final String message;
}
