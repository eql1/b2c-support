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
@RequestMapping("api/v1/tickets")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/user")
    public ResponseEntity<List<TicketResponse>> getUserTickets() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(ticketService.getUserTickets(username));
    }

    @PostMapping("/create")
    public ResponseEntity<TicketResponse> createTicket(@RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(ticketService.createTicket(ticketRequest));
    }

    // done: implement get All tickets @PreAuthorize or @Secured in service, todo: read about SpEL and flexibility (check docs)
    @GetMapping("/all")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }

}
