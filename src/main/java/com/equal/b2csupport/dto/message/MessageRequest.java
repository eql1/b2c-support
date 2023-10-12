package com.equal.b2csupport.dto.message;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MessageRequest {
    @NotBlank(message = "Content is required")
    private String content;
    private Long ticketId;
}
