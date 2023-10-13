package com.equal.b2csupport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
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

    @JoinColumn(name = "created_by")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User createdBy;

    private LocalDateTime createdAt;

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }

    //    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//    }
}
