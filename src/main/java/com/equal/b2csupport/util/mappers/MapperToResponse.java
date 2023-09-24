package com.equal.b2csupport.util.mappers;

import com.equal.b2csupport.dto.TicketResponse;
import com.equal.b2csupport.dto.UserResponse;
import com.equal.b2csupport.model.Ticket;
import com.equal.b2csupport.model.User;
import org.springframework.stereotype.Component;

@Component
public class MapperToResponse {
    public TicketResponse mapToResponse(Ticket ticket) {
        return TicketResponse.builder()
                .name(ticket.getName())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .createdBy(ticket.getCreatedBy().getUsername())
                .build();
    }

    public UserResponse mapToResponse(User user) {
        return UserResponse.builder()
                .name(user.getUsername())
                .role(user.getRole())
                .tickets(
                        user.getTickets()
                                .stream()
                                .map(this::mapToResponse)
                                .toList()
                )
                .build();
    }
}
