package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.parent.parentid = :parentId ORDER BY m.message_date desc")
    List<Message> findMessagesByParentIdOrderedByDate(@Param("parentId") Long parentId);


}
