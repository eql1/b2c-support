package com.equal.b2csupport.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50)
    private String username;
    @NotBlank(message = "Password is required")
    @Size(min = 4, max = 100)
    private String password;
}
