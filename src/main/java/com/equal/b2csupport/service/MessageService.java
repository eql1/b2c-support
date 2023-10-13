package com.equal.b2csupport.service;

import com.equal.b2csupport.auth.AuthenticationService;
import com.equal.b2csupport.dto.message.MessageRequest;
import com.equal.b2csupport.dto.message.MessageResponse;
import com.equal.b2csupport.exception.TicketNotFoundException;
import com.equal.b2csupport.exception.UserNotFoundException;
import com.equal.b2csupport.model.Message;
import com.equal.b2csupport.model.Ticket;
import com.equal.b2csupport.model.User;
import com.equal.b2csupport.repo.MessageRepository;
import com.equal.b2csupport.repo.TicketRepository;
import com.equal.b2csupport.util.mappers.MapperToResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final AuthenticationService authService;
    private final TicketService ticketService;
    private final UserService userService;
    private final MapperToResponse mapper;
    private final TicketRepository ticketRepository;
    private final AccessCheckService accessCheckService;

    @Transactional
    public MessageResponse createMessage(MessageRequest messageRequest) throws TicketNotFoundException, UserNotFoundException, AccessDeniedException {
        Ticket relatedTicket = ticketService.getTicketById(messageRequest.getTicketId());
        Collection<GrantedAuthority> authorities = Arrays.asList(
                new SimpleGrantedAuthority("SUPPORT"),
                new SimpleGrantedAuthority("ADMIN")
        );
        accessCheckService.checkAccess(relatedTicket, authorities);
        User relatedUser = userService.getUserByUsername(authService.getCurrentUsername());
        LocalDateTime creationTime = LocalDateTime.now();
        Message message = Message.builder()
                .content(messageRequest.getContent())
                .ticket(relatedTicket)
                .createdBy(relatedUser)
                .createdAt(creationTime)
                .build();
        relatedTicket.setLastEditedBy(relatedUser);
        relatedTicket.setLastEditedTime(creationTime);
        Message savedMessage = messageRepository.save(message);
        ticketRepository.save(relatedTicket);
        return mapper.mapToResponse(savedMessage);
    }
}
