package com.equal.b2csupport.controller;

import com.equal.b2csupport.dto.TicketRequest;
import com.equal.b2csupport.dto.TicketResponse;
import com.equal.b2csupport.exception.TicketNotFoundException;
import com.equal.b2csupport.model.TicketStatus;
import com.equal.b2csupport.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/user")
    public ResponseEntity<List<TicketResponse>> getUserTickets() {

        return ResponseEntity.ok(ticketService.getUserTickets());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(
            @PathVariable Long id,
            @RequestParam TicketStatus status) {
        try {
            return ResponseEntity.ok(ticketService.changeStatus(id, status));
        } catch (TicketNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket not found");
        }
    }

    @PatchMapping("/{id}/archive")
    public ResponseEntity<?> archiveTicket(@PathVariable Long id) throws TicketNotFoundException {
        return ResponseEntity.ok(ticketService.archiveTicket(id));
    }

    @PatchMapping("/archive")
    public ResponseEntity<List<TicketResponse>> archiveSelectedTickets(@RequestBody List<Long> ticketIds) {
            return ResponseEntity.ok(ticketService.archiveSelectedTickets(ticketIds));
    }

    @PostMapping("/create")
    public ResponseEntity<TicketResponse> createTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        return ResponseEntity.ok(ticketService.createTicket(ticketRequest));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
}
