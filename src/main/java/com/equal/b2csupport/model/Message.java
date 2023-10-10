package com.equal.b2csupport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_gen")
    @SequenceGenerator(name = "message_gen", sequenceName = "message_seq")
    private Long id;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "ticket_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Ticket ticket;

    @JoinColumn(name = "created_by_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User createdBy;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
