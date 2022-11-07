package com.tinyurlexample.tiny.exception;

public class UrlNotFoundException extends RuntimeException {

    public UrlNotFoundException(String message) {
        super(message);
    }

}
