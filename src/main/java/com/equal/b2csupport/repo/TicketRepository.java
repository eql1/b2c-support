package com.equal.b2csupport.repo;

import com.equal.b2csupport.model.Ticket;
import com.equal.b2csupport.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<List<Ticket>> findByCreatedBy(User user);

//    @Modifying
//    @Query("UPDATE Ticket ticket SET ticket.status = :newStatus WHERE ticket.id = :id")
//    void updateTicketStatus(@Param("id") Long id, @Param("newStatus") TicketStatus newStatus);
}
