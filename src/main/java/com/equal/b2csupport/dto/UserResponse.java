package com.equal.b2csupport.dto;

import com.equal.b2csupport.model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private Role role;
    private List<TicketResponse> tickets;
}
