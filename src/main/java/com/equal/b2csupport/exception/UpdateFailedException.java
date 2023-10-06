package com.equal.b2csupport.exception;

public class UpdateFailedException extends RuntimeException{
    public UpdateFailedException(String message) {
        super(message);
    }

    public UpdateFailedException() {}
}
