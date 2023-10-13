package com.equal.b2csupport.controller;

import com.equal.b2csupport.dto.message.MessageRequest;
import com.equal.b2csupport.dto.message.MessageResponse;
import com.equal.b2csupport.dto.ticket.TicketRequest;
import com.equal.b2csupport.dto.ticket.TicketResponse;
import com.equal.b2csupport.exception.TicketNotFoundException;
import com.equal.b2csupport.model.TicketStatus;
import com.equal.b2csupport.service.MessageService;
import com.equal.b2csupport.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/tickets")
@CrossOrigin(origins = "http://localhost:5173")
public class TicketController {
    private final TicketService ticketService;
    private final MessageService messageService;

    @GetMapping("/user")
    public ResponseEntity<List<TicketResponse>> getUserTickets() {

        return ResponseEntity.ok(ticketService.getUserTickets());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<?> changeStatus(
            @PathVariable Long id,
            @RequestParam TicketStatus status) throws TicketNotFoundException {
        return ResponseEntity.ok(ticketService.changeStatus(id, status));
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

    @PostMapping("/message/create")
    public ResponseEntity<MessageResponse> createMessage(@Valid @RequestBody MessageRequest messageRequest) throws TicketNotFoundException, AccessDeniedException {
        return ResponseEntity.ok(messageService.createMessage(messageRequest));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        return ResponseEntity.ok(ticketService.getAllTickets());
    }
}
