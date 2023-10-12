package com.equal.b2csupport.dto.ticket;

import com.equal.b2csupport.dto.message.MessageResponse;
import com.equal.b2csupport.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private boolean isArchived;
    private List<MessageResponse> messages;
}
