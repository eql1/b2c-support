package com.equal.b2csupport.dto.ticket;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {
    @NotBlank(message = "Name is obligatory")
    private String name;
    @NotBlank(message = "Description is obligatory")
    private String description;
}
