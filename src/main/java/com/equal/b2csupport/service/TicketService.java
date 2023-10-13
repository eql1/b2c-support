package com.equal.b2csupport.service;


import com.equal.b2csupport.auth.AuthenticationService;
import com.equal.b2csupport.dto.ticket.TicketRequest;
import com.equal.b2csupport.dto.ticket.TicketResponse;
import com.equal.b2csupport.exception.TicketNotFoundException;
import com.equal.b2csupport.model.Ticket;
import com.equal.b2csupport.model.TicketStatus;
import com.equal.b2csupport.model.User;
import com.equal.b2csupport.repo.TicketRepository;
import com.equal.b2csupport.util.mappers.MapperToResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final MapperToResponse mapperToResponse;
    private final AuthenticationService authService;
    private final AccessCheckService accessCheckService;

    public List<TicketResponse> getUserTickets() {
        String username = authService.getCurrentUsername();
        User user = userService.getUserByUsername(username);
        return ticketRepository.findByCreatedBy(user)
                .map(tickets -> tickets.stream()
                        .filter(ticket -> !ticket.isArchived())
                        .map(mapperToResponse::mapToResponse)
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPPORT')")
    public List<TicketResponse> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(mapperToResponse::mapToResponse)
                .collect(Collectors.toList());
    }

    public Ticket getTicketById(Long id) throws TicketNotFoundException {
         return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id " + id + "not found"));
    }

    // todo: changeStatus for admin and support roles
    @Transactional
    public TicketResponse changeStatus(Long id, TicketStatus changeTo) throws TicketNotFoundException {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id " + id + " not found"));
        accessCheckService.checkAccess(ticket);
        ticket.setStatus(changeTo);
        Ticket savedTicket = ticketRepository.save(ticket);
        return mapperToResponse.mapToResponse(savedTicket);
    }

    @Transactional
    public TicketResponse createTicket(TicketRequest ticketRequest) {
        String username = authService.getCurrentUsername();
        User user = userService.getUserByUsername(username);
        Ticket ticket = Ticket.builder()
                .name(ticketRequest.getName())
                .description(ticketRequest.getDescription())
                .status(TicketStatus.OPEN)
                .createdBy(user)
                .build();
        Ticket savedTicket = ticketRepository.save(ticket);
        return mapperToResponse.mapToResponse(savedTicket);
    }

    @Transactional
    public List<TicketResponse> archiveSelectedTickets(List<Long> ticketIds) {
        List<TicketResponse> tickets = new ArrayList<>();
        for (Long ticket: ticketIds) {
            try {
                TicketResponse response = archiveTicket(ticket);
                tickets.add(response);
            } catch (TicketNotFoundException ex) {
                System.err.println(ex.getMessage());
            }
        }
        return tickets;
    }

    @Transactional
    public TicketResponse archiveTicket(Long id) throws TicketNotFoundException {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket with id " + id + " not found"));
        accessCheckService.checkAccess(ticket);
        ticket.setStatus(TicketStatus.CLOSED);
        ticket.setArchived(true);
        Ticket savedTicket = ticketRepository.save(ticket);
        return mapperToResponse.mapToResponse(savedTicket);
    }
}
