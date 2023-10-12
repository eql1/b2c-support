package com.equal.b2csupport.util.mappers;

import com.equal.b2csupport.dto.message.MessageResponse;
import com.equal.b2csupport.dto.ticket.TicketResponse;
import com.equal.b2csupport.dto.user.UserResponse;
import com.equal.b2csupport.model.Message;
import com.equal.b2csupport.model.Ticket;
import com.equal.b2csupport.model.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperToResponse {
    public TicketResponse mapToResponse(Ticket ticket) {
        List<Message> ticketMessages = ticket.getMessages();
        List<MessageResponse> ticketMessageResponses = ticketMessages == null ?
                Collections.emptyList() : ticketMessages.stream().map(this::mapToResponse).collect(Collectors.toList());
        return TicketResponse.builder()
                .id(ticket.getId())
                .name(ticket.getName())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .createdBy(ticket.getCreatedBy().getUsername())
                .isArchived(ticket.isArchived())
                .messages(ticketMessageResponses)
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

    public MessageResponse mapToResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .ticketId(message.getTicket().getId())
                .createdBy(message.getCreatedBy().getUsername())
                .createdAt(message.getCreatedAt())
                .build();
    }
}
