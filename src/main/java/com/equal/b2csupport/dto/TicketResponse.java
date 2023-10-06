package com.equal.b2csupport.dto;

import com.equal.b2csupport.model.Ticket;
import com.equal.b2csupport.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long id;
    private String name;
    private String description;
    private TicketStatus status;
    private String createdBy;

    public static TicketResponseBuilder fromTicket(Ticket ticket) {
        return TicketResponse.builder()
                .id(ticket.getId())
                .name(ticket.getName())
                .description(ticket.getDescription())
                .status(ticket.getStatus());
    }
}
