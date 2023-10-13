package com.equal.b2csupport.service;

import com.equal.b2csupport.auth.AuthenticationService;
import com.equal.b2csupport.model.Ticket;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AccessCheckService {
    private final AuthenticationService authService;

    private boolean isNotPermitted(Ticket ticket) {
        return ticket.getCreatedBy().getId().equals(authService.getCurrentUserId());
    }

    public void checkAccess(Ticket ticket) {
        if(!isNotPermitted(ticket)) {
            throw new AccessDeniedException("Access denied to this ticket");
        }
    }

    public void checkAccess(Ticket ticket, Collection<GrantedAuthority> permittedAuthorities) {
        Collection<? extends GrantedAuthority> userAuthorities = authService.getCurrentUser().getAuthorities();

        boolean hasPermittedAuthority = userAuthorities.stream().anyMatch(permittedAuthorities::contains);

        if (!hasPermittedAuthority && !isNotPermitted(ticket)) {
            throw new AccessDeniedException("Access denied to this ticket");
        }
    }
}
