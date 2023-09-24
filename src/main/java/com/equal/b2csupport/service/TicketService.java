package com.equal.b2csupport.service;



import com.equal.b2csupport.dto.TicketRequest;
import com.equal.b2csupport.dto.TicketResponse;
import com.equal.b2csupport.model.Ticket;
import com.equal.b2csupport.model.TicketStatus;
import com.equal.b2csupport.model.User;
import com.equal.b2csupport.repo.TicketRepository;
import com.equal.b2csupport.util.mappers.MapperToResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final MapperToResponse mapperToResponse;

    // todo: fix 'org.hibernate.LazyInitializationException' exception
    public List<TicketResponse> getUserTickets(String username) {
        User user = userService.getUserByUsername(username);
        return ticketRepository.findByCreatedBy(user)
                .map(tickets -> tickets.stream()
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



    @Transactional
    public TicketResponse createTicket(TicketRequest ticketRequest) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByUsername(username);
        Ticket ticket = Ticket.builder()
                .name(ticketRequest.getName())
                .description(ticketRequest.getDescription())
                .status(TicketStatus.OPEN)
                .createdBy(user)
                .build();
        Ticket savedTicket = ticketRepository.save(ticket);
        return TicketResponse.fromTicket(savedTicket)
                .createdBy(username)
                .build();
    }
}
