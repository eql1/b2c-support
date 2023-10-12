package com.equal.b2csupport.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private Long id;
    private String content;
    private Long ticketId;
    private String createdBy;
    private LocalDateTime createdAt;
}
