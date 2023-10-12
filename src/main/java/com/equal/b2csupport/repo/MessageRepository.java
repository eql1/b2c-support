package com.equal.b2csupport.repo;

import com.equal.b2csupport.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
