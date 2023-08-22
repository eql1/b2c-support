package com.equal.b2csupport.controller;

import com.equal.b2csupport.auth.AuthenticationService;
import com.equal.b2csupport.dto.TicketRequest;
import com.equal.b2csupport.dto.TicketResponse;
import com.equal.b2csupport.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final TicketService ticketService;
    private final AuthenticationService authService;


    // todo: input data validation
    // todo: read about integration tests,
    @GetMapping("/tickets")
    public ResponseEntity<List<TicketResponse>> getUserTickets() {
        String username = authService.getAuthenticatedUsername();
        return ResponseEntity.ok(ticketService.getUserTickets(username));
    }

    @PostMapping("/tickets")
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest ticketRequest) {
        ticketRequest.setUsername(authService.getAuthenticatedUsername());
        return ResponseEntity.ok(ticketService.createTicket(ticketRequest));

    }
}
