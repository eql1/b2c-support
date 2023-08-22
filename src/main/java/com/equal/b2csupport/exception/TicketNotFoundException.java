package com.equal.b2csupport.exception;

public class TicketNotFoundException extends Exception {
    public TicketNotFoundException(String message) {
        super(message);
    }

    public TicketNotFoundException() {
        super();
    }
}
