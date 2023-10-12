package com.equal.b2csupport.controller;

import com.equal.b2csupport.exception.TicketNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User with given ID not found");
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Object> handleTicketNotFoundException(TicketNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket with given ID not found");
    }

    // todo: handle validation exceptions

}
