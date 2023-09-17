package com.equal.b2csupport.controller;

import com.equal.b2csupport.dto.TicketRequest;
import com.equal.b2csupport.dto.TicketResponse;
import com.equal.b2csupport.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// todo: for test, change mappings and realizations
@RestController
@RequiredArgsConstructor
@RequestMapping("/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping()
    public ResponseEntity<List<TicketResponse>> getUserTickets() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(ticketService.getUserTickets(username));
    }

    @PostMapping()
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(ticketService.createTicket(ticketRequest));
    }

    // todo: implement get All tickets @PreAuthorize or @Secured, think about necessity of SpEL and flexibility (check docs)
}
