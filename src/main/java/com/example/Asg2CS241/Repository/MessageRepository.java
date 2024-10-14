package com.example.Asg2CS241.Repository;

import com.example.Asg2CS241.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByParent_Parentid(Long parentId);


}
