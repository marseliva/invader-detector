package com.example.demo.invadersdetector.exception;

public class ParseFileException extends RuntimeException {

    public ParseFileException(String message, Exception e) {
        super(message, e);
    }

    public ParseFileException(String message) {
        super(message);
    }
}
